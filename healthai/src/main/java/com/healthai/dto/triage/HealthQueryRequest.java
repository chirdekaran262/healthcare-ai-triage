
package com.healthai.dto.triage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HealthQueryRequest {

    @NotBlank(message = "Symptoms description must not be blank")
    @Size(
        min = 5,
        max = 2000,
        message = "Symptoms must be between 5 and 2000 characters"
    )
    private String symptoms;

    private Integer age;
    private String biologicalSex;
    private String knownConditions;

    public HealthQueryRequest() {
    }

    public HealthQueryRequest(
            String symptoms,
            Integer age,
            String biologicalSex,
            String knownConditions) {
        this.symptoms = symptoms;
        this.age = age;
        this.biologicalSex = biologicalSex;
        this.knownConditions = knownConditions;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBiologicalSex() {
        return biologicalSex;
    }

    public void setBiologicalSex(String biologicalSex) {
        this.biologicalSex = biologicalSex;
    }

    public String getKnownConditions() {
        return knownConditions;
    }

    public void setKnownConditions(String knownConditions) {
        this.knownConditions = knownConditions;
    }

    public static HealthQueryRequestBuilder builder() {
        return new HealthQueryRequestBuilder();
    }

    public static class HealthQueryRequestBuilder {
        private String symptoms;
        private Integer age;
        private String biologicalSex;
        private String knownConditions;

        public HealthQueryRequestBuilder symptoms(String symptoms) {
            this.symptoms = symptoms;
            return this;
        }

        public HealthQueryRequestBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public HealthQueryRequestBuilder biologicalSex(
                String biologicalSex) {
            this.biologicalSex = biologicalSex;
            return this;
        }

        public HealthQueryRequestBuilder knownConditions(
                String knownConditions) {
            this.knownConditions = knownConditions;
            return this;
        }

        public HealthQueryRequest build() {
            return new HealthQueryRequest(
                    symptoms,
                    age,
                    biologicalSex,
                    knownConditions
            );
        }
    }
}
