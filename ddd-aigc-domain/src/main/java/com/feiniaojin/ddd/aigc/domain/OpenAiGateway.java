package com.feiniaojin.ddd.aigc.domain;

public interface OpenAiGateway {

    String generateContent(String uid, String input);
}
