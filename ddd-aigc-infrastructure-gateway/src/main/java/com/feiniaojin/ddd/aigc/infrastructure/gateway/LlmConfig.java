package com.feiniaojin.ddd.aigc.infrastructure.gateway;

import io.micrometer.observation.ObservationRegistry;
import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "ddd-aigc")
@Data
public class LlmConfig {

    private String providerName;

    private String defaultSystemMessage;

    // DeepSeek官网
    @Bean
    public ChatClient deepSeekOfficialClient(@Value("${spring.ai.providers.deepseek-official.base-url}") String baseUrl,
                                             @Value("${spring.ai.providers.deepseek-official.api-key}") String apiKey,
                                             @Value("${spring.ai.providers.deepseek-official.model}") String model,
                                             ChatMemory chatMemory) {

        ApiKey apiKey1 = (new SimpleApiKey(apiKey));

        OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiKey1, CollectionUtils.toMultiValueMap(Map.of()),
                "/chat/completions", "/embeddings",
                RestClient.builder(), WebClient.builder(), RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER);

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model(model).build();

        DefaultToolCallingManager callingManager = DefaultToolCallingManager.builder().build();

        OpenAiChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions, callingManager, RetryUtils.DEFAULT_RETRY_TEMPLATE, ObservationRegistry.NOOP);

        return ChatClient.builder(openAiChatModel).defaultOptions(openAiChatOptions)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory)).build();
    }

    //硅基流动
    @Bean
    public ChatClient siliconflowClient(@Value("${spring.ai.providers.siliconflow.base-url}") String baseUrl,
                                        @Value("${spring.ai.providers.siliconflow.api-key}") String apiKey,
                                        @Value("${spring.ai.providers.siliconflow.model}") String model,
                                        ChatMemory chatMemory) {
        ApiKey apiKey1 = (new SimpleApiKey(apiKey));

        OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiKey1, CollectionUtils.toMultiValueMap(Map.of()),
                "/chat/completions", "/embeddings",
                RestClient.builder(), WebClient.builder(), RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER);

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model(model).build();

        DefaultToolCallingManager callingManager = DefaultToolCallingManager.builder().build();

        OpenAiChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions, callingManager, RetryUtils.DEFAULT_RETRY_TEMPLATE, ObservationRegistry.NOOP);

        return ChatClient.builder(openAiChatModel)
                .defaultOptions(openAiChatOptions)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory)).build();
    }


    //OpenAI配置
    @Bean
    public ChatClient openAIClient(@Value("${spring.ai.providers.openai.base-url}") String baseUrl,
                                   @Value("${spring.ai.providers.openai.api-key}") String apiKey,
                                   @Value("${spring.ai.providers.openai.model}") String model,
                                   ChatMemory chatMemory) {
        ApiKey apiKey1 = new SimpleApiKey(apiKey);

        OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiKey1, CollectionUtils.toMultiValueMap(Map.of()),
                "/chat/completions", "/embeddings",
                RestClient.builder(), WebClient.builder(), RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER);

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model(model).build();

        DefaultToolCallingManager callingManager = DefaultToolCallingManager.builder().build();

        OpenAiChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions, callingManager, RetryUtils.DEFAULT_RETRY_TEMPLATE, ObservationRegistry.NOOP);

        return ChatClient.builder(openAiChatModel)
                .defaultOptions(openAiChatOptions)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory)).build();
    }

    // Ollama本地部署配置
    @Bean
    public ChatClient ollamaClient(@Value("${spring.ai.providers.ollama.base-url}") String baseUrl,
                                   @Value("${spring.ai.providers.ollama.model}") String model,
                                   ChatMemory chatMemory) {

        OllamaApi ollamaApi = new OllamaApi(baseUrl);

        OllamaOptions ollamaOptions = OllamaOptions.builder().model(model).build();

        DefaultToolCallingManager callingManager = DefaultToolCallingManager.builder().build();

        OllamaChatModel ollamaChatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(ollamaOptions)
                .toolCallingManager(callingManager)
                .modelManagementOptions(ModelManagementOptions.defaults())
                .build();

        return ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory)).build();
    }

    /**
     * Spring AI 提到不是所有的大模型都支持
     *
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}
