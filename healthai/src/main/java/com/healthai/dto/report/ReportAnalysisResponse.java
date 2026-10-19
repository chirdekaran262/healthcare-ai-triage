
package com.healthai.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ReportAnalysisResponse {

    @JsonProperty(required = true)
    private String executiveSummary;

    @JsonProperty(required = true)
    private String reportType;

    @JsonProperty(required = true)
    private List<LabMarkerAnalysis> keyFindings;

    @JsonProperty(required = true)
    private List<String> lifestyleAndCareNotes;

    @JsonProperty(required = true)
    private List<String> questionsForYourDoctor;

    @JsonProperty(required = true)
    private String nextSteps;

    @JsonProperty(required = true)
    private String clinicalDisclaimer;

    // No-argument constructor
    public ReportAnalysisResponse() {
    }

    // All-argument constructor
    public ReportAnalysisResponse(
            String executiveSummary,
            String reportType,
            List<LabMarkerAnalysis> keyFindings,
            List<String> lifestyleAndCareNotes,
            List<String> questionsForYourDoctor,
            String nextSteps,
            String clinicalDisclaimer) {
        this.executiveSummary = executiveSummary;
        this.reportType = reportType;
        this.keyFindings = keyFindings;
        this.lifestyleAndCareNotes = lifestyleAndCareNotes;
        this.questionsForYourDoctor = questionsForYourDoctor;
        this.nextSteps = nextSteps;
        this.clinicalDisclaimer = clinicalDisclaimer;
    }

    // Getters and setters
    public String getExecutiveSummary() {
        return executiveSummary;
    }

    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<LabMarkerAnalysis> getKeyFindings() {
        return keyFindings;
    }

    public void setKeyFindings(List<LabMarkerAnalysis> keyFindings) {
        this.keyFindings = keyFindings;
    }

    public List<String> getLifestyleAndCareNotes() {
        return lifestyleAndCareNotes;
    }

    public void setLifestyleAndCareNotes(List<String> lifestyleAndCareNotes) {
        this.lifestyleAndCareNotes = lifestyleAndCareNotes;
    }

    public List<String> getQuestionsForYourDoctor() {
        return questionsForYourDoctor;
    }

    public void setQuestionsForYourDoctor(List<String> questionsForYourDoctor) {
        this.questionsForYourDoctor = questionsForYourDoctor;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }

    public String getClinicalDisclaimer() {
        return clinicalDisclaimer;
    }

    public void setClinicalDisclaimer(String clinicalDisclaimer) {
        this.clinicalDisclaimer = clinicalDisclaimer;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String executiveSummary;
        private String reportType;
        private List<LabMarkerAnalysis> keyFindings;
        private List<String> lifestyleAndCareNotes;
        private List<String> questionsForYourDoctor;
        private String nextSteps;
        private String clinicalDisclaimer;

        public Builder executiveSummary(String executiveSummary) {
            this.executiveSummary = executiveSummary;
            return this;
        }

        public Builder reportType(String reportType) {
            this.reportType = reportType;
            return this;
        }

        public Builder keyFindings(
                List<LabMarkerAnalysis> keyFindings) {
            this.keyFindings = keyFindings;
            return this;
        }

        public Builder lifestyleAndCareNotes(
                List<String> lifestyleAndCareNotes) {
            this.lifestyleAndCareNotes = lifestyleAndCareNotes;
            return this;
        }

        public Builder questionsForYourDoctor(
                List<String> questionsForYourDoctor) {
            this.questionsForYourDoctor = questionsForYourDoctor;
            return this;
        }

        public Builder nextSteps(String nextSteps) {
            this.nextSteps = nextSteps;
            return this;
        }

        public Builder clinicalDisclaimer(String clinicalDisclaimer) {
            this.clinicalDisclaimer = clinicalDisclaimer;
            return this;
        }

        public ReportAnalysisResponse build() {
            return new ReportAnalysisResponse(
                    executiveSummary,
                    reportType,
                    keyFindings,
                    lifestyleAndCareNotes,
                    questionsForYourDoctor,
                    nextSteps,
                    clinicalDisclaimer);
        }
        
    }
}
