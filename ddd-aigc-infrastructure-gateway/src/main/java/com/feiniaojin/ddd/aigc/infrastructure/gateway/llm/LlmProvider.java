package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

/**
 * 大语言模型提供者的抽象
 */
public interface LlmProvider {

    String providerName();

    String generateContent(Prompt prompt, String conversationId);

    default Flux<String> generateContentStream(Prompt prompt, String conversationId) {
        throw new UnsupportedOperationException("待实现");
    }

}
