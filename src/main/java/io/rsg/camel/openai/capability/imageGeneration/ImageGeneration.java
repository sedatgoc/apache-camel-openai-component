package io.rsg.camel.openai.capability.imageGeneration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageGeneration {

    @NonNull
    private String prompt;

    private String model;

    private Integer n;

    private String quality;

    private String response_format;

    private String size;

    private String style;

    private String user;

}
