
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiPart {

    private String text;

    public GeminiPart() {
    }

    public GeminiPart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static GeminiPartBuilder builder() {
        return new GeminiPartBuilder();
    }

    public static class GeminiPartBuilder {
        private String text;

        public GeminiPartBuilder text(String text) {
            this.text = text;
            return this;
        }

        public GeminiPart build() {
            return new GeminiPart(text);
        }
    }
}
