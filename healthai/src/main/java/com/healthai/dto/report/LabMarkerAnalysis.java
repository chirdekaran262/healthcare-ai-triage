
package com.healthai.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabMarkerAnalysis {

    @JsonProperty(required = true)
    private String markerName;

    @JsonProperty(required = true)
    private String reportedValue;

    @JsonProperty(required = true)
    private String referenceRange;

    @JsonProperty(required = true)
    private MarkerStatus status;

    @JsonProperty(required = true)
    private String plainLanguageExplanation;

    // No-argument constructor
    public LabMarkerAnalysis() {
    }

    // All-argument constructor
    public LabMarkerAnalysis(
            String markerName,
            String reportedValue,
            String referenceRange,
            MarkerStatus status,
            String plainLanguageExplanation) {
        this.markerName = markerName;
        this.reportedValue = reportedValue;
        this.referenceRange = referenceRange;
        this.status = status;
        this.plainLanguageExplanation = plainLanguageExplanation;
    }

    // Getters and setters
    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getReportedValue() {
        return reportedValue;
    }

    public void setReportedValue(String reportedValue) {
        this.reportedValue = reportedValue;
    }

    public String getReferenceRange() {
        return referenceRange;
    }

    public void setReferenceRange(String referenceRange) {
        this.referenceRange = referenceRange;
    }

    public MarkerStatus getStatus() {
        return status;
    }

    public void setStatus(MarkerStatus status) {
        this.status = status;
    }

    public String getPlainLanguageExplanation() {
        return plainLanguageExplanation;
    }

    public void setPlainLanguageExplanation(String plainLanguageExplanation) {
        this.plainLanguageExplanation = plainLanguageExplanation;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String markerName;
        private String reportedValue;
        private String referenceRange;
        private MarkerStatus status;
        private String plainLanguageExplanation;

        public Builder markerName(String markerName) {
            this.markerName = markerName;
            return this;
        }

        public Builder reportedValue(String reportedValue) {
            this.reportedValue = reportedValue;
            return this;
        }

        public Builder referenceRange(String referenceRange) {
            this.referenceRange = referenceRange;
            return this;
        }

        public Builder status(MarkerStatus status) {
            this.status = status;
            return this;
        }

        public Builder plainLanguageExplanation(
                String plainLanguageExplanation) {
            this.plainLanguageExplanation = plainLanguageExplanation;
            return this;
        }

        public LabMarkerAnalysis build() {
            return new LabMarkerAnalysis(
                    markerName,
                    reportedValue,
                    referenceRange,
                    status,
                    plainLanguageExplanation);
        }
    }
}
