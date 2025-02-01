package io.rsg.camel.openai;

import java.util.Map;

import org.apache.camel.Endpoint;

import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;

@Component("openai")
public class OpenAIComponent extends DefaultComponent {
    
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new OpenAIEndpoint(uri, this,remaining);
        setProperties(endpoint, parameters);
        return endpoint;
    }


}
