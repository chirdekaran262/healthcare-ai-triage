
package com.healthai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthai.config.ReportAnalysisConfig;
import com.healthai.dto.gemini.*;
import com.healthai.dto.report.ReportAnalysisResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ReportAnalysisService {

	private static final Logger log = LoggerFactory.getLogger(ReportAnalysisService.class);

	private final WebClient geminiWebClient;
	private final ResponseSchema reportAnalysisJsonSchema;
	private final List<SafetySetting> geminiSafetySettings;
	private final ObjectMapper objectMapper;

	@Value("${gemini.api.key}")
	private String apiKey;

	@Value("${gemini.api.model}")
	private String model;

	@Value("${gemini.api.timeout-seconds:35}")
	private int timeoutSeconds;

	// Explicit constructor replaces @RequiredArgsConstructor
	public ReportAnalysisService(WebClient geminiWebClient, ResponseSchema reportAnalysisJsonSchema,
			List<SafetySetting> geminiSafetySettings, ObjectMapper objectMapper) {
		this.geminiWebClient = geminiWebClient;
		this.reportAnalysisJsonSchema = reportAnalysisJsonSchema;
		this.geminiSafetySettings = geminiSafetySettings;
		this.objectMapper = objectMapper;
	}

	public ReportAnalysisResponse analyzeReport(MultipartFile file, String optionalPatientContext) {

		validateFile(file);

		List<GeminiPart> parts = new ArrayList<>();

		// Convert the uploaded file to Base64 for Gemini
		try {
			String base64Data = Base64.getEncoder().encodeToString(file.getBytes());

			parts.add(GeminiPart.builder()
					.inlineData(InlineData.builder().mimeType(file.getContentType()).data(base64Data).build()).build());

		} catch (IOException e) {
			log.error("Failed to read report binary stream", e);

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file payload");
		}

		// Build the user prompt
		String userPrompt = "Analyze this attached lab/medical document. " + "Translate all terms into plain English "
				+ "for a non-medical patient.";

		if (optionalPatientContext != null && !optionalPatientContext.isBlank()) {
			userPrompt += " Patient background context: " + optionalPatientContext;
		}

		parts.add(GeminiPart.builder().text(userPrompt).build());

		// Build the Gemini request
		GeminiRequest payload = GeminiRequest.builder()
				.contents(List.of(GeminiContent.builder().role("user").parts(parts).build()))
				.systemInstruction(GeminiContent.builder()
						.parts(List.of(GeminiPart.builder()
								.text(ReportAnalysisConfig.REPORT_ANALYSIS_SYSTEM_INSTRUCTION).build()))
						.build())
				.generationConfig(GenerationConfig.builder().responseMimeType("application/json")
						.responseSchema(reportAnalysisJsonSchema).temperature(0.2).maxOutputTokens(2048).build())
				.safetySettings(geminiSafetySettings).build();

		String uri = String.format("/models/%s:generateContent?key=%s", model, apiKey);

		// Call Gemini API
		GeminiResponse geminiResponse = geminiWebClient.post().uri(uri).bodyValue(payload).retrieve()
				.onStatus(HttpStatusCode::isError,
						clientResponse -> clientResponse.bodyToMono(String.class).flatMap(err -> {
							log.error("Gemini Report Analysis Error: {} - {}", clientResponse.statusCode(), err);

							return Mono.error(new ResponseStatusException(clientResponse.statusCode(),
									"Report analysis failed upstream"));
						}))
				.bodyToMono(GeminiResponse.class).timeout(Duration.ofSeconds(timeoutSeconds)).block();

		return parseReportResponse(geminiResponse);
	}

	private void validateFile(MultipartFile file) {

		if (file == null || file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medical report file cannot be empty");
		}

		String contentType = file.getContentType();

		if (contentType == null
				|| !(contentType.equalsIgnoreCase("application/pdf") || contentType.equalsIgnoreCase("image/jpeg")
						|| contentType.equalsIgnoreCase("image/png") || contentType.equalsIgnoreCase("image/webp"))) {

			throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
					"Supported formats: PDF, JPEG, PNG, WEBP");
		}
	}

	private ReportAnalysisResponse parseReportResponse(GeminiResponse response) {
		System.out.println("Get reponse from gemini: "+response.getCandidates());
		if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {

			log.warn("Gemini returned no candidates.");

			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
					"The AI service returned no analysis. Please try again.");
		}

		// Safely retrieve the first candidate
		var candidate = response.getCandidates().get(0);

		if (candidate == null || candidate.getContent() == null) {
			log.warn("Gemini candidate or content is missing.");

			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
					"The AI service returned an incomplete response. Please try again.");
		}

		// Check the parts list before accessing it
		var responseParts = candidate.getContent().getParts();

		if (responseParts == null || responseParts.isEmpty()) {
			log.warn("Gemini returned no content parts. Finish reason: {}", candidate.getFinishReason());

			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
					"The AI service returned no usable analysis. Please try again.");
		}

		// Find the first non-null text part
		String rawJson = responseParts.stream().filter(part -> part != null && part.getText() != null)
				.map(GeminiPart::getText).filter(text -> !text.isBlank()).findFirst().orElse(null);

		if (rawJson == null) {
			log.warn("Gemini response contains no text content.");

			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
					"The AI service returned empty content. Please try again.");
		}

		try {
			return objectMapper.readValue(rawJson, ReportAnalysisResponse.class);

		} catch (Exception e) {
			// Medical report output may contain sensitive patient information.
			// Do not log the raw JSON.
			log.error("Failed to parse Gemini report analysis JSON.", e);

			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
					"The AI service returned an invalid analysis. Please try again.");
		}
	}

}
