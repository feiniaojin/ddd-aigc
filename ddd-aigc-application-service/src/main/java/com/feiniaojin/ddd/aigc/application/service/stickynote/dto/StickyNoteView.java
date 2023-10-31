package com.feiniaojin.ddd.aigc.application.service.stickynote.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建成功后，需要回传ID
 */
@Data
public class StickyNoteView {
    private String uid;

    private String diaryId;

    private String content;

    private List<String> participants;

    private String occurrenceTimeStr;
}
