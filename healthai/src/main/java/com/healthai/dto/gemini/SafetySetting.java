
package com.healthai.dto.gemini;

public class SafetySetting {

    private String category;
    private String threshold;

    public SafetySetting() {
    }

    public SafetySetting(String category, String threshold) {
        this.category = category;
        this.threshold = threshold;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public static SafetySettingBuilder builder() {
        return new SafetySettingBuilder();
    }

    public static class SafetySettingBuilder {
        private String category;
        private String threshold;

        public SafetySettingBuilder category(String category) {
            this.category = category;
            return this;
        }

        public SafetySettingBuilder threshold(String threshold) {
            this.threshold = threshold;
            return this;
        }

        public SafetySetting build() {
            return new SafetySetting(category, threshold);
        }
    }
}
