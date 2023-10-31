package com.feiniaojin.ddd.aigc.domain;

/**
 * 根据贴纸生成文本的领域服务
 * 要根据日记下的多个StickyNote聚合生成文本，因此是一个领域服务
 */
public interface StickyNoteGenerateContentDomainService {

    String generateContent(DiaryEntityId diaryEntityId);
}
