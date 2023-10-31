package com.feiniaojin.ddd.aigc.application.service.stickynote.dto;

import lombok.Data;

import java.util.List;

@Data
public class StickyNoteModifyCommand {

    private String stickyNoteId;

    private String content;

    private List<String> participants;
}
