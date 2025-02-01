package io.rsg.camel.openai.capability.textToSpeech;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextToSpeechGeneration {


    private String model;

    @NonNull
    private String input;

    @NonNull
    private String voice;

    @JsonProperty("response_format")
    private String responseFormat;

    @Builder.Default
    private double speed = 1.0;

}

