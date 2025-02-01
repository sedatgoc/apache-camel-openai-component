package io.rsg.camel.openai.model;

import lombok.Data;

@Data
public class ErrorResponse {
    String code;
    String message;
    String param;
    String type;
}
