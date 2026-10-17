
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {

    private List<Candidate> candidates;

    public GeminiResponse() {
    }

    public GeminiResponse(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {

        private GeminiContent content;
        private String finishReason;

        public Candidate() {
        }

        public Candidate(
                GeminiContent content,
                String finishReason) {
            this.content = content;
            this.finishReason = finishReason;
        }

        public GeminiContent getContent() {
            return content;
        }

        public void setContent(GeminiContent content) {
            this.content = content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }
}
