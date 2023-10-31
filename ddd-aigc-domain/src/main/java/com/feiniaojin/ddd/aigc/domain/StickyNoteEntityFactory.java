package com.feiniaojin.ddd.aigc.domain;

import java.util.List;

public interface StickyNoteEntityFactory {


    /**
     * 创建贴纸
     *
     * @param uid          用户ID
     * @param diaryId      日记ID
     * @param content      正文内容
     * @param participants 参与者
     * @return
     */
    StickyNoteEntity newInstance(String uid, String diaryId, String content, List<String> participants, String occurrenceTimeStr);
}
