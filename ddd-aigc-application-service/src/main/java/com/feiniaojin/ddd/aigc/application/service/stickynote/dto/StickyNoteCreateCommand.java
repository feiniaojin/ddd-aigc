package com.feiniaojin.ddd.aigc.application.service.stickynote.dto;

import lombok.Data;

import java.util.List;

@Data
public class StickyNoteCreateCommand {

    private String uid;

    private String diaryId;

    private String content;

    private String participants;

    private String occurrenceTimeStr;
}
