package io.rsg.camel.openai.util;

import io.rsg.camel.openai.OpenAIEndpoint;
import io.rsg.camel.openai.OpenAIException;

import java.util.List;
import java.util.Map;

public class EndpointURIHelper {


    Map<String, List<String>> capabilityMap =
            Map.of("text-generation",List.of("gpt-4", "gpt-4 turbo", "gpt-3.5-turbo"),
                    "text-to-speech",List.of("tts-1", "tts-1-hd"),
                    "image-generation",List.of("dall-e-2", "dall-e-3"));

    public void checkEndpointURI(OpenAIEndpoint endpoint, String uri) throws OpenAIException {
        String[] uriPathArr = uri.split(":");
        if(uriPathArr.length != 2){
            throw new OpenAIException("Syntax is incorrect. The Syntax must be openai:capability:model");

        }else{
            String capability = uriPathArr[0];
            String model = uriPathArr[1];
            if(capabilityMap.containsKey(capability)){
               if(capabilityMap.get(capability).contains(model)){
                   endpoint.setCapability(capability);
                   endpoint.setModel(model);
               }else{
                   throw new OpenAIException(String.format("Capability '%s' found but model '%s' not found. Please check your endpoint.", capability,model));
               }
            }else{
                throw new OpenAIException(String.format("Capability '%s' not found. Please check your endpoint.", capability));

            }

        }
    }



}
