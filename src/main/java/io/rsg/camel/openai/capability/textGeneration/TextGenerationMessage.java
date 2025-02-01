package io.rsg.camel.openai.capability.textGeneration;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextGenerationMessage {
    private String role;
    private String content;
    private String name;

    public TextGenerationMessage(String role, String content, String name){
        this.role = role;
        this.content = content;
        this.name = name;
    }

    public TextGenerationMessage(String role, String content){
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
