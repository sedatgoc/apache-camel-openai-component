package io.rsg.camel.openai.capability.textGeneration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextGenerationUsage {
    @JsonProperty("completion_tokens")
    private Integer completionTokens;

    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    @JsonProperty("total_tokens")
    private Integer totalTokens;

}
