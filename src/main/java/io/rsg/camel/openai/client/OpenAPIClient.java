package io.rsg.camel.openai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.rsg.camel.openai.OpenAIEndpoint;
import io.rsg.camel.openai.capability.imageGeneration.ImageGenerationResponse;
import io.rsg.camel.openai.capability.textGeneration.TextGenerationResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class OpenAPIClient {

    private String apiKey;

    private String body;

    private  Properties headers;

    private OpenAIEndpoint endpoint;

    private boolean isRawPayload;

    public OpenAPIClient(OpenAIEndpoint endpoint, String body, Properties headers, boolean isRawPayload){
        this.endpoint = endpoint;
        this.body = body;
        this.headers = headers;
        this.isRawPayload = isRawPayload;
    }

    public Object sendAndGetResponse() throws IOException, InterruptedException {
        switch (endpoint.getCapability()){
            case "text-generation" : return textGenerationHTTPRequest();
            case "image-generation" :return imageGenerationHTTPRequest();
            case "text-to-speech":  return textToSpeechGenerationHTTPRequest();
            default: return "Capability not found. Possible values : text-generation, image-generation and text-to-speech";
        }
    }

    private Object textGenerationHTTPRequest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type","application/json")
                .headers("Authorization","Bearer "+endpoint.getApiKey())
                .build();
        HttpResponse<String> response = endpoint.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.INDENT_OUTPUT);
        return isRawPayload ? response.body() : objectMapper.readValue(response.body(), TextGenerationResponse.class);
    }

    private Object imageGenerationHTTPRequest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/images/generations"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type","application/json")
                .headers("Authorization","Bearer "+endpoint.getApiKey())
                .build();

        HttpResponse<String> response = endpoint.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.INDENT_OUTPUT);

        return isRawPayload ? response.body() : objectMapper.readValue(response.body(), ImageGenerationResponse.class);
    }

    private Object textToSpeechGenerationHTTPRequest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/audio/speech"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type","application/json")
                .headers("Authorization","Bearer "+endpoint.getApiKey())
                .build();
        HttpResponse<InputStream> response = endpoint.getHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());


        return response.body().readAllBytes();



    }
}
