package io.rsg.camel.openai.capability.textGeneration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TextGenerationChoices {

    @JsonProperty("finish_reason")
    private String finishReason;

    private Integer index;

    private TextGenerationMessages message;

    private boolean logprobs;

}
