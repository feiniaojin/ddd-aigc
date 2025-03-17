package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class DeepSeekLlmProviderImpl extends AbstractLlmProviderImpl {

    public DeepSeekLlmProviderImpl(ChatClient deepSeekOfficialClient) {
        super(deepSeekOfficialClient);
    }

    @Override
    public String providerName() {
        return "deepseek-official";
    }
}
