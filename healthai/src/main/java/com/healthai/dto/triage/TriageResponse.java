
package com.healthai.dto.triage;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TriageResponse {

    @JsonProperty(required = true)
    private TriageLevel triageLevel;

    @JsonProperty(required = true)
    private String disclaimer;

    @JsonProperty(required = true)
    private List<String> immediateActions;

    @JsonProperty(required = true)
    private String careAdvice;

    @JsonProperty(required = true)
    private String followUpRecommendation;

    public TriageResponse() {
    }

    public TriageResponse(
            TriageLevel triageLevel,
            String disclaimer,
            List<String> immediateActions,
            String careAdvice,
            String followUpRecommendation) {
        this.triageLevel = triageLevel;
        this.disclaimer = disclaimer;
        this.immediateActions = immediateActions;
        this.careAdvice = careAdvice;
        this.followUpRecommendation = followUpRecommendation;
    }

    public TriageLevel getTriageLevel() {
        return triageLevel;
    }

    public void setTriageLevel(TriageLevel triageLevel) {
        this.triageLevel = triageLevel;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public List<String> getImmediateActions() {
        return immediateActions;
    }

    public void setImmediateActions(List<String> immediateActions) {
        this.immediateActions = immediateActions;
    }

    public String getCareAdvice() {
        return careAdvice;
    }

    public void setCareAdvice(String careAdvice) {
        this.careAdvice = careAdvice;
    }

    public String getFollowUpRecommendation() {
        return followUpRecommendation;
    }

    public void setFollowUpRecommendation(
            String followUpRecommendation) {
        this.followUpRecommendation = followUpRecommendation;
    }

    public static TriageResponseBuilder builder() {
        return new TriageResponseBuilder();
    }

    public static class TriageResponseBuilder {
        private TriageLevel triageLevel;
        private String disclaimer;
        private List<String> immediateActions;
        private String careAdvice;
        private String followUpRecommendation;

        public TriageResponseBuilder triageLevel(
                TriageLevel triageLevel) {
            this.triageLevel = triageLevel;
            return this;
        }

        public TriageResponseBuilder disclaimer(String disclaimer) {
            this.disclaimer = disclaimer;
            return this;
        }

        public TriageResponseBuilder immediateActions(
                List<String> immediateActions) {
            this.immediateActions = immediateActions;
            return this;
        }

        public TriageResponseBuilder careAdvice(String careAdvice) {
            this.careAdvice = careAdvice;
            return this;
        }

        public TriageResponseBuilder followUpRecommendation(
                String followUpRecommendation) {
            this.followUpRecommendation = followUpRecommendation;
            return this;
        }

        public TriageResponse build() {
            return new TriageResponse(
                    triageLevel,
                    disclaimer,
                    immediateActions,
                    careAdvice,
                    followUpRecommendation
            );
        }
    }
}
