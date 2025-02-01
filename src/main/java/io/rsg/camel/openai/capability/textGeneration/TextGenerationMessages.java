package io.rsg.camel.openai.capability.textGeneration;

import lombok.Data;

@Data
public class TextGenerationMessages {
    private String content;
    private String role;
    private String refusal;
}
