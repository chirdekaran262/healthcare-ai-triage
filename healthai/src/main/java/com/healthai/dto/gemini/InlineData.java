
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InlineData {

    @JsonProperty("mime_type")
    private String mimeType;

    @JsonProperty("data")
    private String data; // Base64-encoded string

    // No-argument constructor
    public InlineData() {
    }

    // All-argument constructor
    public InlineData(String mimeType, String data) {
        this.mimeType = mimeType;
        this.data = data;
    }

    // Getters and setters
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String mimeType;
        private String data;

        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public InlineData build() {
            return new InlineData(mimeType, data);
        }
    }
}
