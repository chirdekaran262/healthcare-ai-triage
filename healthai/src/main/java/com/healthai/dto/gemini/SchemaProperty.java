
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchemaProperty {

    private String type;
    private String description;

    @JsonProperty("enum")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> enumValues;

    private SchemaProperty items;
    private Map<String, SchemaProperty> properties;
    private List<String> required;

    public SchemaProperty() {
    }

    public SchemaProperty(
            String type,
            String description,
            List<String> enumValues,
            SchemaProperty items,
            Map<String, SchemaProperty> properties,
            List<String> required) {
        this.type = type;
        this.description = description;
        this.enumValues = enumValues;
        this.items = items;
        this.properties = properties;
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("enum")
    public List<String> getEnumValues() {
        return enumValues;
    }

    @JsonProperty("enum")
    public void setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
    }

    public SchemaProperty getItems() {
        return items;
    }

    public void setItems(SchemaProperty items) {
        this.items = items;
    }

    public Map<String, SchemaProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, SchemaProperty> properties) {
        this.properties = properties;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public static SchemaPropertyBuilder builder() {
        return new SchemaPropertyBuilder();
    }

    public static class SchemaPropertyBuilder {

        private String type;
        private String description;
        private List<String> enumValues;
        private SchemaProperty items;
        private Map<String, SchemaProperty> properties;
        private List<String> required;

        public SchemaPropertyBuilder type(String type) {
            this.type = type;
            return this;
        }

        public SchemaPropertyBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SchemaPropertyBuilder enumValues(List<String> enumValues) {
            this.enumValues = enumValues;
            return this;
        }

        public SchemaPropertyBuilder items(SchemaProperty items) {
            this.items = items;
            return this;
        }

        public SchemaPropertyBuilder properties(
                Map<String, SchemaProperty> properties) {
            this.properties = properties;
            return this;
        }

        public SchemaPropertyBuilder required(List<String> required) {
            this.required = required;
            return this;
        }

        public SchemaProperty build() {
            return new SchemaProperty(
                    type,
                    description,
                    enumValues,
                    items,
                    properties,
                    required
            );
        }
    }
}
