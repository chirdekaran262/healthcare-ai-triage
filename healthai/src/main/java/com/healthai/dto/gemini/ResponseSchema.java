
package com.healthai.dto.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSchema {

    private String type;
    private Map<String, SchemaProperty> properties;
    private List<String> required;

    public ResponseSchema() {
    }

    public ResponseSchema(
            String type,
            Map<String, SchemaProperty> properties,
            List<String> required) {
        this.type = type;
        this.properties = properties;
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public static ResponseSchemaBuilder builder() {
        return new ResponseSchemaBuilder();
    }

    public static class ResponseSchemaBuilder {
        private String type;
        private Map<String, SchemaProperty> properties;
        private List<String> required;

        public ResponseSchemaBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ResponseSchemaBuilder properties(
                Map<String, SchemaProperty> properties) {
            this.properties = properties;
            return this;
        }

        public ResponseSchemaBuilder required(List<String> required) {
            this.required = required;
            return this;
        }

        public ResponseSchema build() {
            return new ResponseSchema(type, properties, required);
        }
    }
}
