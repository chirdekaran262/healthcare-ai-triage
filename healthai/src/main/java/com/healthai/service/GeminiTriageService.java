
package com.healthai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthai.config.GeminiConfig;
import com.healthai.dto.gemini.*;
import com.healthai.dto.triage.HealthQueryRequest;
import com.healthai.dto.triage.TriageLevel;
import com.healthai.dto.triage.TriageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class GeminiTriageService {

    private static final Logger log =
            LoggerFactory.getLogger(GeminiTriageService.class);

    private final WebClient geminiWebClient;
    private final ResponseSchema triageJsonSchema;
    private final List<SafetySetting> geminiSafetySettings;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.model}")
    private String model;

    @Value("${gemini.api.timeout-seconds:25}")
    private int timeoutSeconds;

    // Replacement for Lombok @RequiredArgsConstructor
    public GeminiTriageService(
            WebClient geminiWebClient,
            ResponseSchema triageJsonSchema,
            List<SafetySetting> geminiSafetySettings,
            ObjectMapper objectMapper) {

        this.geminiWebClient = geminiWebClient;
        this.triageJsonSchema = triageJsonSchema;
        this.geminiSafetySettings = geminiSafetySettings;
        this.objectMapper = objectMapper;
    }

    public TriageResponse evaluateSymptoms(HealthQueryRequest request) {
    	System.out.println("In Evalute Sysmptoms");
        String prompt = buildPrompt(request);

        GeminiRequest payload = GeminiRequest.builder()
                .contents(List.of(
                        GeminiContent.builder()
                                .role("user")
                                .parts(List.of(
                                        GeminiPart.builder()
                                                .text(prompt)
                                                .build()
                                ))
                                .build()
                ))
                .systemInstruction(
                        GeminiContent.builder()
                                .parts(List.of(
                                        GeminiPart.builder()
                                                .text(GeminiConfig.SYSTEM_INSTRUCTION_TEXT)
                                                .build()
                                ))
                                .build()
                )
                .generationConfig(
                        GenerationConfig.builder()
                                .responseMimeType("application/json")
                                .responseSchema(triageJsonSchema)
                                .temperature(0.1)
                                .maxOutputTokens(1024)
                                .build()
                )
                .safetySettings(geminiSafetySettings)
                .build();
        System.out.println("Payload : "+payload.getSystemInstruction().getRole());
        String uri = String.format(
                "/models/%s:generateContent?key=%s",
                model,
                apiKey
        );

        GeminiResponse geminiResponse = geminiWebClient.post()
                .uri(uri)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse ->
                                clientResponse.bodyToMono(String.class)
                                        .flatMap(errorBody -> {
                                            log.error(
                                                    "Gemini API Error: {} - {}",
                                                    clientResponse.statusCode(),
                                                    errorBody
                                            );

                                            return Mono.error(
                                                    new ResponseStatusException(
                                                            clientResponse.statusCode(),
                                                            "Gemini service upstream error"
                                                    )
                                            );
                                        })
                )
                .bodyToMono(GeminiResponse.class)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .onErrorResume(ex -> {
                    log.error(
                            "Failed to fetch response from Gemini",
                            ex
                    );

                    return Mono.error(
                            new ResponseStatusException(
                                    HttpStatus.SERVICE_UNAVAILABLE,
                                    "Unable to process triage at this time. "
                                    + "If this is an emergency, contact "
                                    + "your local emergency services."
                            )
                    );
                })
                .block();

        return parseGeminiResponse(geminiResponse);
    }

    private String buildPrompt(HealthQueryRequest req) {

        StringBuilder sb = new StringBuilder();

        sb.append("Evaluate patient presentation for clinical triage:\n");
        sb.append("- Reported Symptoms: ")
                .append(req.getSymptoms())
                .append("\n");

        if (req.getAge() != null) {
            sb.append("- Age: ")
                    .append(req.getAge())
                    .append("\n");
        }

        if (req.getBiologicalSex() != null
                && !req.getBiologicalSex().isBlank()) {
            sb.append("- Biological Sex: ")
                    .append(req.getBiologicalSex())
                    .append("\n");
        }

        if (req.getKnownConditions() != null
                && !req.getKnownConditions().isBlank()) {
            sb.append("- Existing Health Conditions: ")
                    .append(req.getKnownConditions())
                    .append("\n");
        }

        sb.append(
                "\nReturn strictly formatted JSON matching the specified schema."
        );

        return sb.toString();
    }

    private TriageResponse parseGeminiResponse(
            GeminiResponse response) {

        if (response == null
                || response.getCandidates() == null
                || response.getCandidates().isEmpty()) {

            log.warn("Gemini returned empty candidate list");
            return buildFallbackResponse();
        }

        GeminiResponse.Candidate candidate =
                response.getCandidates().get(0);

        if (candidate.getContent() == null
                || candidate.getContent().getParts() == null
                || candidate.getContent().getParts().isEmpty()) {

            log.warn("Gemini candidate content is missing parts");
            return buildFallbackResponse();
        }

        String rawJson =
                candidate.getContent().getParts().get(0).getText();

        if (rawJson == null || rawJson.isBlank()) {
            log.warn("Gemini returned empty response text");
            return buildFallbackResponse();
        }

        try {
            return objectMapper.readValue(
                    rawJson,
                    TriageResponse.class
            );
        } catch (JsonProcessingException e) {
            log.error(
                    "Could not parse JSON response from Gemini",
                    e
            );

            return buildFallbackResponse();
        }
    }

    private TriageResponse buildFallbackResponse() {

        return TriageResponse.builder()
                .triageLevel(TriageLevel.YELLOW)
                .disclaimer(
                        "EMERGENCY NOTICE: The system could not "
                        + "complete the assessment reliably. "
                        + "Seek urgent medical assistance if symptoms "
                        + "are severe or worsening."
                )
                .immediateActions(List.of(
                        "Contact local emergency services immediately "
                        + "for severe chest pain, difficulty breathing, "
                        + "or stroke symptoms.",
                        "Seek an in-person medical assessment when "
                        + "symptoms are urgent or worsening."
                ))
                .careAdvice(
                        "Rest in a safe, comfortable position. "
                        + "Do not delay emergency care while waiting "
                        + "for an AI assessment."
                )
                .followUpRecommendation(
                        "Consult a qualified healthcare professional "
                        + "for an appropriate assessment."
                )
                .build();
    }
}
