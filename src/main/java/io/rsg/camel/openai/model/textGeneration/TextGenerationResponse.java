package io.rsg.camel.openai.model.textGeneration;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.rsg.camel.openai.model.ErrorResponse;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextGenerationResponse {
    private String id;
    private Integer created;
    private String model;

    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    private String object;

    private TextGenerationUsage usage;

    private List<TextGenerationChoices> choices;

    ErrorResponse error;

}
