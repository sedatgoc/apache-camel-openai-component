package io.rsg.camel.openai.capability.imageGeneration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageGenerationResponseData {

    String url;

    @JsonProperty("revised_prompt")
    String revisedPrompt;

    @JsonProperty("b64_json")
    String b64Json;
}
