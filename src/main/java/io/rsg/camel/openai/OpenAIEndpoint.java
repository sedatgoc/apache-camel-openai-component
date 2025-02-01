package io.rsg.camel.openai;


import io.rsg.camel.openai.util.EndpointURIHelper;
import org.apache.camel.Category;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

import java.net.http.HttpClient;

@UriEndpoint(firstVersion = "0.0.1", scheme = "openai", title = "openai", syntax="openai:capability:model",
             category = {Category.JAVA}, producerOnly = true)
public class OpenAIEndpoint extends DefaultEndpoint {
    @UriPath(name = "capability", description = "Open AI Capability")
    @Metadata(required = true)
    private String capability;

    @UriPath(name = "model", description = "Open AI model version")
    @Metadata(required = true)
    private String model;

    @UriParam(name = "apiKey", description = "Open AI Api Key.")
    private String apiKey;

    @UriParam(name = "rawPayload", description = "Raw Payload instance of Pojo")
    private boolean rawPayload;

    public boolean isRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(boolean rawPayload) {
        this.rawPayload = rawPayload;
    }

    private HttpClient httpClient;


    public OpenAIEndpoint() {
    }

    public OpenAIEndpoint(String uri, OpenAIComponent component, String uriPath) throws OpenAIException {
        super(uri, component);
        EndpointURIHelper endpointURIHelper  = new EndpointURIHelper();
        endpointURIHelper.checkEndpointURI(this, uriPath);
    }

    public Producer createProducer() throws Exception {
        return new OpenAIProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
      return null;
    }

    @Override
    protected void doInit() throws Exception {

        httpClient = HttpClient.newHttpClient();

    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
