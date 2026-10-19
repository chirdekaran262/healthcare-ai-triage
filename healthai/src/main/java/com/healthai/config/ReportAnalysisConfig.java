package com.healthai.config;

import com.healthai.dto.gemini.ResponseSchema;
import com.healthai.dto.gemini.SchemaProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ReportAnalysisConfig {

    public static final String REPORT_ANALYSIS_SYSTEM_INSTRUCTION = """
        You are a specialized Clinical Medical Report Interpreter designed to translate complex medical diagnostic reports, blood panels, and imaging summaries into clear, reassuring, plain language for patients and non-medical users.

        Your Core Mandate:
        1. Break down medical jargon into simple, everyday explanations (target a 6th to 8th-grade reading level).
        2. Extract key biological markers, test values, standard reference ranges, and categorize their status (NORMAL, ATTENTION_NEEDED, or CRITICAL).
        3. Never provide a final diagnosis, declare absolute illness, or prescribe medications.
        4. Focus on 'What this means for you' and equip the user with an actionable checklist of questions to ask their doctor at their next visit.
        5. Formulate a structured, empathetic, non-alarmist executive summary.
        6. Always append an explicit liability disclaimer stating this interpretation is an informational aid, not an official diagnostic interpretation.
        """;

    @Bean
    public ResponseSchema reportAnalysisJsonSchema() {
        Map<String, SchemaProperty> itemProps = new LinkedHashMap<>();
        itemProps.put("markerName", SchemaProperty.builder().type("STRING").description("e.g. Hemoglobin, Platelets, TSH").build());
        itemProps.put("reportedValue", SchemaProperty.builder().type("STRING").description("e.g. 10.2 g/dL").build());
        itemProps.put("referenceRange", SchemaProperty.builder().type("STRING").description("e.g. 13.5 - 17.5 g/dL").build());
        itemProps.put("status", SchemaProperty.builder().type("STRING").enumValues(List.of("NORMAL", "ATTENTION_NEEDED", "CRITICAL")).build());
        itemProps.put("plainLanguageExplanation", SchemaProperty.builder().type("STRING").description("Simple explanation of what this test measures and what this value signifies").build());

        SchemaProperty keyFindingItem = SchemaProperty.builder()
                .type("OBJECT")
                .properties(itemProps)
                .required(List.of("markerName", "reportedValue", "referenceRange", "status", "plainLanguageExplanation"))
                .build();

        Map<String, SchemaProperty> rootProps = new LinkedHashMap<>();
        rootProps.put("executiveSummary", SchemaProperty.builder().type("STRING").description("Plain-language overview of the overall report").build());
        rootProps.put("reportType", SchemaProperty.builder().type("STRING").description("e.g. Complete Blood Count (CBC), Metabolic Panel, Lipid Panel").build());
        rootProps.put("keyFindings", SchemaProperty.builder().type("ARRAY").items(keyFindingItem).build());
        rootProps.put("lifestyleAndCareNotes", SchemaProperty.builder().type("ARRAY").items(SchemaProperty.builder().type("STRING").build()).build());
        rootProps.put("questionsForYourDoctor", SchemaProperty.builder().type("ARRAY").items(SchemaProperty.builder().type("STRING").build()).build());
        rootProps.put("nextSteps", SchemaProperty.builder().type("STRING").description("Clear guidance on what the patient should do next (e.g. routine checkup vs urgent booking)").build());
        rootProps.put("clinicalDisclaimer", SchemaProperty.builder().type("STRING").description("Non-diagnostic medical legal disclaimer").build());

        return ResponseSchema.builder()
                .type("OBJECT")
                .properties(rootProps)
                .required(List.of("executiveSummary", "reportType", "keyFindings", "lifestyleAndCareNotes", "questionsForYourDoctor", "nextSteps", "clinicalDisclaimer"))
                .build();
    }
}