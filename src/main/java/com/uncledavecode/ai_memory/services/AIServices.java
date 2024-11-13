package com.uncledavecode.ai_memory.services;

import com.uncledavecode.ai_memory.model.dtos.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class AIServices {

    @Value("classpath:/prompts/pt-joke-maker.st")
    private Resource ptResource;

    private final ChatClient chatClient;

    public AIServices(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new PromptChatMemoryAdvisor(new InMemoryChatMemory())
                )
                .build();
    }

    public ChatResponse getJoke(String message) {
        try {
            var resource = ptResource.getContentAsString(Charset.defaultCharset());

            var response = chatClient
                    .prompt()
                    .user(userSpec -> {
                        userSpec.text(resource)
                                .param("message", message);
                    })
                    .call()
                    .entity(String.class);

            return new ChatResponse(response);
        } catch (IOException e) {
            log.error("Error reading resource: {}", e.getMessage());
            return new ChatResponse("Error getting a joke");
        }

    }
}
