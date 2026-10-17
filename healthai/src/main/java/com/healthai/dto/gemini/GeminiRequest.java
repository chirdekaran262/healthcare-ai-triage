
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiRequest {

    private List<GeminiContent> contents;

    @JsonProperty("system_instruction")
    private GeminiContent systemInstruction;

    @JsonProperty("generation_config")
    private GenerationConfig generationConfig;

    @JsonProperty("safety_settings")
    private List<SafetySetting> safetySettings;

    public GeminiRequest() {
    }

    public GeminiRequest(
            List<GeminiContent> contents,
            GeminiContent systemInstruction,
            GenerationConfig generationConfig,
            List<SafetySetting> safetySettings) {
        this.contents = contents;
        this.systemInstruction = systemInstruction;
        this.generationConfig = generationConfig;
        this.safetySettings = safetySettings;
    }

    public List<GeminiContent> getContents() {
        return contents;
    }

    public void setContents(List<GeminiContent> contents) {
        this.contents = contents;
    }

    public GeminiContent getSystemInstruction() {
        return systemInstruction;
    }

    public void setSystemInstruction(GeminiContent systemInstruction) {
        this.systemInstruction = systemInstruction;
    }

    public GenerationConfig getGenerationConfig() {
        return generationConfig;
    }

    public void setGenerationConfig(GenerationConfig generationConfig) {
        this.generationConfig = generationConfig;
    }

    public List<SafetySetting> getSafetySettings() {
        return safetySettings;
    }

    public void setSafetySettings(List<SafetySetting> safetySettings) {
        this.safetySettings = safetySettings;
    }

    public static GeminiRequestBuilder builder() {
        return new GeminiRequestBuilder();
    }

    public static class GeminiRequestBuilder {
        private List<GeminiContent> contents;
        private GeminiContent systemInstruction;
        private GenerationConfig generationConfig;
        private List<SafetySetting> safetySettings;

        public GeminiRequestBuilder contents(List<GeminiContent> contents) {
            this.contents = contents;
            return this;
        }

        public GeminiRequestBuilder systemInstruction(
                GeminiContent systemInstruction) {
            this.systemInstruction = systemInstruction;
            return this;
        }

        public GeminiRequestBuilder generationConfig(
                GenerationConfig generationConfig) {
            this.generationConfig = generationConfig;
            return this;
        }

        public GeminiRequestBuilder safetySettings(
                List<SafetySetting> safetySettings) {
            this.safetySettings = safetySettings;
            return this;
        }

        public GeminiRequest build() {
            return new GeminiRequest(
                    contents,
                    systemInstruction,
                    generationConfig,
                    safetySettings
            );
        }
    }
}
