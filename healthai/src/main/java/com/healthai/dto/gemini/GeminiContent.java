
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiContent {

    private String role;
    private List<GeminiPart> parts;

    public GeminiContent() {
    }

    public GeminiContent(String role, List<GeminiPart> parts) {
        this.role = role;
        this.parts = parts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<GeminiPart> getParts() {
        return parts;
    }

    public void setParts(List<GeminiPart> parts) {
        this.parts = parts;
    }

    public static GeminiContentBuilder builder() {
        return new GeminiContentBuilder();
    }

    public static class GeminiContentBuilder {
        private String role;
        private List<GeminiPart> parts;

        public GeminiContentBuilder role(String role) {
            this.role = role;
            return this;
        }

        public GeminiContentBuilder parts(List<GeminiPart> parts) {
            this.parts = parts;
            return this;
        }

        public GeminiContent build() {
            return new GeminiContent(role, parts);
        }
    }
}
