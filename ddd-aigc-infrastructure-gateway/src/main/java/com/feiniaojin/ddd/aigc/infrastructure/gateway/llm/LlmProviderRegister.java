package com.feiniaojin.ddd.aigc.infrastructure.gateway.llm;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 大语言模型提供者的抽象
 */
@Component
public class LlmProviderRegister implements ApplicationContextAware {

    private final ConcurrentHashMap<String, LlmProvider> providerMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(LlmProvider.class).forEach((key, value) -> {
            providerMap.putIfAbsent(value.providerName(), value);
        });
    }

    public LlmProvider getLlmProvider(String providerName) {
        return providerMap.get(providerName);
    }
}
