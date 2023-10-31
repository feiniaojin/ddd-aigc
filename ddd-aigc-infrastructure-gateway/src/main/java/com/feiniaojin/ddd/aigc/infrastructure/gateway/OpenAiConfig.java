package com.feiniaojin.ddd.aigc.infrastructure.gateway;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "ddd-aigc.open-ai")
@Data
public class OpenAiConfig {

    private String token;

    @PostConstruct
    public void init() {
        String envToken = System.getenv("ddd-aigc.open-ai.token");
        if (StringUtils.isNoneBlank(envToken)) {
            this.token = envToken;
        }
    }
}
