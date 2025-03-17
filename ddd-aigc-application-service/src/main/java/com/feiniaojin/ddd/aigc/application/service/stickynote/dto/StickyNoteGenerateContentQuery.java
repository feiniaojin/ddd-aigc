package com.feiniaojin.ddd.aigc.application.service.stickynote.dto;

import lombok.Data;

@Data
public class StickyNoteGenerateContentQuery {

    /**
     * 日志ID
     */
    private String diaryId;

    private String userInput;

}
