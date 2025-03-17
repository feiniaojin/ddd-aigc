package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class OpenAiLlmProviderImpl extends AbstractLlmProviderImpl {

    public OpenAiLlmProviderImpl(ChatClient openAIClient) {
        super(openAIClient);
    }

    @Override
    public String providerName() {
        return "openai";
    }
}
