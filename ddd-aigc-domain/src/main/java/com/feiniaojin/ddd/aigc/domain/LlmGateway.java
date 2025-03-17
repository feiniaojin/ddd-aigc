package com.feiniaojin.ddd.aigc.domain;

import java.util.List;

/**
 * 大模型调用网关
 */
public interface LlmGateway {

    String generateContent(List<StickyNoteEntity> stickyNoteEntities, String userInput);
}
