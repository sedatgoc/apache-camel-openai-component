package io.rsg.camel.openai;

import io.rsg.camel.openai.capability.imageGeneration.ImageGeneration;
import io.rsg.camel.openai.capability.textGeneration.TextGeneration;
import io.rsg.camel.openai.capability.textGeneration.TextGenerationMessage;
import io.rsg.camel.openai.capability.textGeneration.TextGenerationResponse;
import io.rsg.camel.openai.capability.textToSpeech.TextToSpeechGeneration;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.*;

public class OpenAIComponentTest extends CamelTestSupport {


    @Test
    public void textGenerationTest() throws Exception {
        List<TextGenerationMessage> textGenerationMessageList = List.of(new TextGenerationMessage("user","Can you explain apache camel?"));
        TextGeneration textGeneration = TextGeneration.builder().messages(textGenerationMessageList).build();

        template.sendBody("direct:text-generation", textGeneration);

    }

    @Test
    public void textGenerationRawTest() throws Exception {
        var rawBody = "{\"messages\":[{\"role\":\"user\",\"content\":\"Can you write a story about Java?\"}],\"model\":\"gpt-3.5-turbo\"}";
        template.sendBody("direct:text-generation-raw", rawBody);

    }

    @Test
    public void imageGenerationTest() {
        ImageGeneration imageGeneration = ImageGeneration
                .builder()
                .prompt("Beautiful Istanbul view with bagel and tea.")
                .size("1000x500")
                .build();

        template.sendBody("direct:image-generation", imageGeneration);
    }

    @Test
    public void textToSpeechGenerationTest(){
        TextToSpeechGeneration textToSpeechGeneration = TextToSpeechGeneration.builder()
                .input("Testing for myself.. I love coding!")
                .voice("alloy")
                .build();

        template.sendBody("direct:text-to-speech-generation", textToSpeechGeneration);
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {

                from("direct:text-generation")
                        .to("openai:text-generation:gpt-3.5-turbo?apiKey="+System.getenv("OPENAI_APIKEY"))
                        .process(exchange -> {
                            TextGenerationResponse response = exchange.getIn().getBody(TextGenerationResponse.class);
                            System.out.println(response.getChoices().get(0).getMessage().getContent());
                        });

                from("direct:text-generation-raw")
                        .to("openai:text-generation:gpt-3.5-turbo?apiKey="+System.getenv("OPENAI_APIKEY")+"&rawPayload=true")
                        .process(exchange -> {
                            System.out.println(exchange.getIn().getBody(String.class));
                        });

                from("direct:image-generation")
                        .to("openai:image-generation:dall-e-3?apiKey="+System.getenv("OPENAI_APIKEY"));

                from("direct:text-to-speech-generation")
                        .to("openai:text-to-speech:tts-1?apiKey="+System.getenv("OPENAI_APIKEY"));
            }
        };
    }

}
