package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OllamaLlmProviderImpl extends AbstractLlmProviderImpl {

    /**
     * 运行时修改模型
     */
    @Value("${spring.ai.providers.ollama.model}")
    private String model;

    public OllamaLlmProviderImpl(ChatClient ollamaClient) {
        super(ollamaClient);
    }

    @Override
    protected Optional<ChatOptions> runtimeOptions() {
        return Optional.of(ChatOptions.builder().model(model).build());
    }

    @Override
    public String providerName() {
        return "ollama";
    }
}
