# Apache Camel OpenAI Component

## Overview
This Apache Camel component provides seamless integration with OpenAI's APIs for text generation, image generation, and text-to-speech functionalities. It supports various OpenAI models and allows easy interaction within Camel routes.

## Features
- **Text Generation**: Supports `gpt-4`, `gpt-4-turbo`, and `gpt-3.5-turbo` models.
- **Image Generation**: Supports `dall-e-2` and `dall-e-3` models.
- **Text-to-Speech**: Supports `tts-1` and `tts-1-hd` models.

## Installation

After build the project run this mvn command.

```java
mvn install:install-file -Dfile=apache-camel-openai-0.0.1.jar -DgroupId=io.rsg.camel -DartifactId=apache-camel-openai -Dversion=0.0.1 -Dpackaging=jar
```

To use this component in your Apache Camel project, add the dependency:

```xml
<dependency>
  <groupId>io.rsg.camel</groupId>
  <artifactId>apache-camel-openai</artifactId>
  <version>0.0.1</version>
</dependency>
```

## Configuration

The component requires an OpenAI API key to function. Configure it in application.properties or set it dynamically:

openai.apikey=your_openai_api_key

Add "apiKey" parameter to openai component. 

Example;

```java
  .to("openai:text-generation:gpt-3.5-turbo?apiKey={{openai.apikey}})
```

## Usage
### Text Generation
```java

List<TextGenerationMessage> textGenerationMessageList = List.of(new TextGenerationMessage("user","Can you explain apache camel?"));
TextGeneration textGeneration = TextGeneration.builder().messages(textGenerationMessageList).build();

template.sendBody("direct:text-generation", textGeneration);

from("direct:text-generation")
.to("openai:text-generation:gpt-3.5-turbo?apiKey={{openai.apikey}})
.process(exchange -> {
  TextGenerationResponse response = exchange.getIn().getBody(TextGenerationResponse.class);
  System.out.println(response.getChoices().get(0).getMessage().getContent());
});
```

### Image Generation

```java
ImageGeneration imageGeneration = ImageGeneration
  .builder()
  .prompt("Beautiful Istanbul view with bagel and tea.")
  .size("1024x1024")
  .build();

template.sendBody("direct:image-generation", imageGeneration);

from("direct:image-generation")
  .to("openai:image-generation:dall-e-3?apiKey={{openai.apikey}})
  .process(exchange -> {
    ImageGenerationResponse imageGenerationResponse = exchange.getIn().getBody(ImageGenerationResponse.class);
    //  Print Image
    //System.out.println(imageGenerationResponse.getData().get(0).getUrl());
  }).end();
```

### Text-to-Speech
```java

TextToSpeechGeneration textToSpeechGeneration = TextToSpeechGeneration
  .builder()
  .input("Testing for myself.. I love coding!")
  .voice("alloy")
  .build();

from("direct:text-to-speech-generation")
  .to("openai:text-to-speech:tts-1?apiKey={{openai.apikey}})
  .process(exchange -> {
    //Text to speech response is writing directly to exchange body as byte array.
    FileOutputStream fileOutputStream = new FileOutputStream("textToSpeech.mp3");
    fileOutputStream.write(exchange.getIn().getBody(byte[].class));
  });
```

### With Raw Payload

Supports raw payload instead of objects. 

```java
var rawBody = "{\"messages\":[{\"role\":\"user\",\"content\":\"Can you write a story about Java?\"}],\"model\":\"gpt-3.5-turbo\"}";
template.sendBody("direct:text-generation-raw", rawBody);

from("direct:text-generation-raw")
.to("openai:text-generation:gpt-3.5-turbo?apiKey={{openai.apikey}}&rawPayload=true")
.process(exchange -> {
  System.out.println(exchange.getIn().getBody(String.class));
});
```

## Contributing
Contributions are welcome! Please open an issue or submit a pull request.

## Contact
For any inquiries, please reach out via GitHub issues.

---
Enjoy using OpenAI with Apache Camel! 🚀

