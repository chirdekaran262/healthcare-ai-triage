
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiPart {

    private String text;
    private InlineData inlineData;

    public GeminiPart() {
    }

    public GeminiPart(String text) {
        this.text = text;
    }

    public GeminiPart(String text, InlineData inlineData) {
        this.text = text;
        this.inlineData = inlineData;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public InlineData getInlineData() {
        return inlineData;
    }

    public void setInlineData(InlineData inlineData) {
        this.inlineData = inlineData;
    }

    public static GeminiPartBuilder builder() {
        return new GeminiPartBuilder();
    }

    public static class GeminiPartBuilder {

        private String text;
        private InlineData inlineData;

        public GeminiPartBuilder text(String text) {
            this.text = text;
            return this;
        }

        public GeminiPartBuilder inlineData(InlineData inlineData) {
            this.inlineData = inlineData;
            return this;
        }

        public GeminiPart build() {
            return new GeminiPart(text, inlineData);
        }
    }
}
