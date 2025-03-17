package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class SiliconflowLlmProviderImpl extends AbstractLlmProviderImpl {

    public SiliconflowLlmProviderImpl(ChatClient siliconflowClient) {
        super(siliconflowClient);
    }

    @Override
    public String providerName() {
        return "siliconflow";
    }
}
