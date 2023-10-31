package com.feiniaojin.ddd.aigc.application.service.stickynote.dto;

import lombok.Data;

import java.util.List;

@Data
public class StickyNoteCreateView {

    public StickyNoteCreateView(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }

    private String stickyNoteId;

}
