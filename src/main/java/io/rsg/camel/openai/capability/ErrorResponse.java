package io.rsg.camel.openai.capability;

import lombok.Data;

@Data
public class ErrorResponse {
    String code;
    String message;
    String param;
    String type;
}
