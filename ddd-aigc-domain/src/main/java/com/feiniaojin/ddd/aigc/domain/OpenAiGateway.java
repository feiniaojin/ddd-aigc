package com.feiniaojin.ddd.aigc.domain;

/**
 * Open AI调用网关
 */
public interface OpenAiGateway {

    String generateContent(String uid, String input);
}
