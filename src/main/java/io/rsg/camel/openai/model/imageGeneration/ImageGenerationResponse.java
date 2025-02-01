package io.rsg.camel.openai.model.imageGeneration;

import io.rsg.camel.openai.model.ErrorResponse;
import lombok.Data;

import java.util.List;

@Data
public class ImageGenerationResponse {
    Long created;
    List<ImageGenerationResponseData> data;

    ErrorResponse error;
}
