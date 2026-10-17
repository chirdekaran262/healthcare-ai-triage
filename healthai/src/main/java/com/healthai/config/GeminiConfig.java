package com.healthai.config;

import com.healthai.dto.gemini.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class GeminiConfig {

    public static final String SYSTEM_INSTRUCTION_TEXT = """
        You are an advanced Emergency Medical Triage AI Assistant.
        Your mission is to evaluate reported symptoms and classify them into one of three strict triage levels:
        
        1. RED (Emergency):
           - Life-threatening symptoms: severe chest pain, stroke symptoms (FAST), respiratory arrest, profuse bleeding, anaphylaxis, loss of consciousness.
           - Action: Immediate 911/EMS callout, immediate life-support/CPR/hemostasis instructions.
        
        2. YELLOW (Urgent):
           - Urgent but non-immediately fatal: high unremitting fever, deep lacerations, potential fractures, severe persistent abdominal pain.
           - Action: Same-day clinic or emergency room visit, active monitoring signs.
           
        3. GREEN (Minor):
           - Self-limiting or non-acute: common cold, mild scrapes, mild tension headache, minor insect bites.
           - Action: At-home self-care, comfort measures, over-the-counter considerations, non-pharmacological relief.
        
        Mandatory Guidelines:
        - Output MUST adhere strictly to the JSON schema provided.
        - Never prescribe specific prescription medication or declare definitive diagnoses.
        - Always provide a clear, non-negotiable liability disclaimer stating you are an AI assistant and not a human physician.
        """;

    @Bean
    public ResponseSchema triageJsonSchema() {
        Map<String, SchemaProperty> properties = new LinkedHashMap<>();

        properties.put("triageLevel", SchemaProperty.builder()
                .type("STRING")
                .description("Triage urgency tier: RED (immediate emergency), YELLOW (urgent clinical care), GREEN (minor self-care)")
                .enumValues(List.of("RED", "YELLOW", "GREEN"))
                .build());

        properties.put("disclaimer", SchemaProperty.builder()
                .type("STRING")
                .description("Clinical and legal disclaimer emphasizing this is AI guidance, not a medical diagnosis")
                .build());

        properties.put("immediateActions", SchemaProperty.builder()
                .type("ARRAY")
                .description("Chronological, imperative steps to perform immediately (e.g., Call 911, apply direct pressure)")
                .items(SchemaProperty.builder().type("STRING").build())
                .build());

        properties.put("careAdvice", SchemaProperty.builder()
                .type("STRING")
                .description("Detailed first aid, comfort measures, or symptoms monitoring advice")
                .build());

        properties.put("followUpRecommendation", SchemaProperty.builder()
                .type("STRING")
                .description("Explicit guidance on when and where to seek professional human medical attention")
                .build());

        return ResponseSchema.builder()
                .type("OBJECT")
                .properties(properties)
                .required(List.of("triageLevel", "disclaimer", "immediateActions", "careAdvice", "followUpRecommendation"))
                .build();
    }

    @Bean
    public List<SafetySetting> geminiSafetySettings() {
        return List.of(
                SafetySetting.builder()
                        .category("HARM_CATEGORY_DANGEROUS_CONTENT")
                        .threshold("BLOCK_LOW_AND_ABOVE")
                        .build(),
                SafetySetting.builder()
                        .category("HARM_CATEGORY_HATE_SPEECH")
                        .threshold("BLOCK_MEDIUM_AND_ABOVE")
                        .build(),
                SafetySetting.builder()
                        .category("HARM_CATEGORY_HARASSMENT")
                        .threshold("BLOCK_MEDIUM_AND_ABOVE")
                        .build(),
                SafetySetting.builder()
                        .category("HARM_CATEGORY_SEXUALLY_EXPLICIT")
                        .threshold("BLOCK_MEDIUM_AND_ABOVE")
                        .build()
        );
    }
}