
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerationConfig {

    @JsonProperty("response_mime_type")
    private String responseMimeType;

    @JsonProperty("response_schema")
    private ResponseSchema responseSchema;

    private Double temperature;
    private Integer maxOutputTokens;

    public GenerationConfig() {
    }

    public GenerationConfig(
            String responseMimeType,
            ResponseSchema responseSchema,
            Double temperature,
            Integer maxOutputTokens) {
        this.responseMimeType = responseMimeType;
        this.responseSchema = responseSchema;
        this.temperature = temperature;
        this.maxOutputTokens = maxOutputTokens;
    }

    public String getResponseMimeType() {
        return responseMimeType;
    }

    public void setResponseMimeType(String responseMimeType) {
        this.responseMimeType = responseMimeType;
    }

    public ResponseSchema getResponseSchema() {
        return responseSchema;
    }

    public void setResponseSchema(ResponseSchema responseSchema) {
        this.responseSchema = responseSchema;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxOutputTokens() {
        return maxOutputTokens;
    }

    public void setMaxOutputTokens(Integer maxOutputTokens) {
        this.maxOutputTokens = maxOutputTokens;
    }

    public static GenerationConfigBuilder builder() {
        return new GenerationConfigBuilder();
    }

    public static class GenerationConfigBuilder {
        private String responseMimeType;
        private ResponseSchema responseSchema;
        private Double temperature;
        private Integer maxOutputTokens;

        public GenerationConfigBuilder responseMimeType(String responseMimeType) {
            this.responseMimeType = responseMimeType;
            return this;
        }

        public GenerationConfigBuilder responseSchema(
                ResponseSchema responseSchema) {
            this.responseSchema = responseSchema;
            return this;
        }

        public GenerationConfigBuilder temperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public GenerationConfigBuilder maxOutputTokens(Integer maxOutputTokens) {
            this.maxOutputTokens = maxOutputTokens;
            return this;
        }

        public GenerationConfig build() {
            return new GenerationConfig(
                    responseMimeType,
                    responseSchema,
                    temperature,
                    maxOutputTokens
            );
        }
    }
}
