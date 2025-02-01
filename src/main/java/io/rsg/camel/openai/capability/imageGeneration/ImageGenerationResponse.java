package io.rsg.camel.openai.capability.imageGeneration;

import io.rsg.camel.openai.capability.ErrorResponse;
import lombok.Data;

import java.util.List;

@Data
public class ImageGenerationResponse {
    Long created;
    List<ImageGenerationResponseData> data;

    ErrorResponse error;
}
