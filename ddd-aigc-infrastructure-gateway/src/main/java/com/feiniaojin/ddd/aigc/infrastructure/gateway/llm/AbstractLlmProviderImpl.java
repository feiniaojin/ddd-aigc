package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.Optional;

/**
 * LlmProvider的抽象实现类
 * - 把ChatClient引入进来
 * - 使用模板方法设计模式，把生成内容的过程统一起来
 */
public abstract class AbstractLlmProviderImpl implements LlmProvider {

    private final ChatClient chatClient;

    public AbstractLlmProviderImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String generateContent(Prompt prompt, String conversationId) {
        Optional<ChatOptions> runtimeOptions = runtimeOptions();
        ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(prompt);
        runtimeOptions.ifPresent(requestSpec::options);
        requestSpec.advisors(advisor ->
                advisor.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)
        );
        return requestSpec.call().content();
    }

    /**
     * 子类调整运行时ChatOptions
     *
     * @return Optional<ChatOptions>
     */
    protected Optional<ChatOptions> runtimeOptions() {
        return Optional.empty();
    }

    @Override
    public Flux<String> generateContentStream(Prompt prompt, String conversationId) {
        Optional<ChatOptions> runtimeOptions = runtimeOptions();
        ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(prompt);
        runtimeOptions.ifPresent(requestSpec::options);
        requestSpec.advisors(advisor ->
                advisor.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)
        );
        return requestSpec.stream().content();
    }
}
