package io.rsg.camel.openai.model.textGeneration;

import lombok.Data;

@Data
public class TextGenerationMessages {
    private String content;
    private String role;
    private String refusal;
}
