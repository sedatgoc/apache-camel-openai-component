package io.rsg.camel.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsg.camel.openai.model.imageGeneration.ImageGeneration;
import io.rsg.camel.openai.model.imageGeneration.ImageGenerationResponse;
import io.rsg.camel.openai.model.textGeneration.TextGeneration;
import io.rsg.camel.openai.model.textGeneration.TextGenerationResponse;
import io.rsg.camel.openai.model.textToSpeech.TextToSpeechGeneration;
import io.rsg.camel.openai.client.OpenAPIClient;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAIProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(OpenAIProducer.class);
    private OpenAIEndpoint endpoint;

    public OpenAIProducer(OpenAIEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        OpenAPIClient apiClient = null;
        ObjectMapper mapper = new ObjectMapper();
        Object exchangeBody = exchange.getIn().getBody();

        if(endpoint.isRawPayload()){
            if(exchangeBody instanceof String){
                apiClient = new OpenAPIClient(endpoint, (String) exchangeBody, null, endpoint.isRawPayload());
                String responseBody = (String) apiClient.sendAndGetResponse();
                exchange.getIn().setBody(responseBody);
            }else{
                LOG.error("isRawPayload true but received String body.");
            }
        }else{
            if(exchangeBody instanceof TextGeneration){
                TextGeneration textGeneration = (TextGeneration) exchangeBody;
                textGeneration.setModel(endpoint.getModel());
                apiClient = new OpenAPIClient(endpoint, mapper.writeValueAsString(textGeneration), null, endpoint.isRawPayload());
                TextGenerationResponse textGenerationResponse = (TextGenerationResponse) apiClient.sendAndGetResponse();
                if(textGenerationResponse.getError() != null){
                    LOG.error(textGenerationResponse.getError().getMessage());
                }else{
                    exchange.getIn().setBody(textGenerationResponse);
                }
            }

            if(exchangeBody instanceof ImageGeneration){
                ImageGeneration imageGeneration = (ImageGeneration) exchangeBody;
                imageGeneration.setModel(endpoint.getModel());
                apiClient = new OpenAPIClient(endpoint, mapper.writeValueAsString(imageGeneration), null, endpoint.isRawPayload());
                ImageGenerationResponse imageGenerationResponse = (ImageGenerationResponse) apiClient.sendAndGetResponse();
                if(imageGenerationResponse.getError() != null){
                    LOG.error("ImageGeneration error : "+imageGenerationResponse.getError().getMessage());
                }else{
                    exchange.getIn().setBody(imageGenerationResponse);
                }
            }

            if(exchangeBody instanceof TextToSpeechGeneration){
                TextToSpeechGeneration textToSpeechGeneration = (TextToSpeechGeneration) exchangeBody;
                textToSpeechGeneration.setModel(endpoint.getModel());
                apiClient = new OpenAPIClient(endpoint, mapper.writeValueAsString(textToSpeechGeneration), null, endpoint.isRawPayload());
                try{
                    exchange.getIn().setBody(apiClient.sendAndGetResponse());
                }
                catch (Exception e){
                    LOG.error("TextToSpeech error : "+e.getMessage());
                }
            }
        }
    }

}
