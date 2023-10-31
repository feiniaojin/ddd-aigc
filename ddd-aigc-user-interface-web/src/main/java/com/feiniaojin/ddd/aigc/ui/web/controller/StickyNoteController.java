package com.feiniaojin.ddd.aigc.ui.web.controller;

import com.feiniaojin.ddd.aigc.application.service.stickynote.StickyNoteCommandApplicationService;
import com.feiniaojin.ddd.aigc.application.service.stickynote.StickyNoteQueryApplicationService;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/stickyNote")
public class StickyNoteController {

    @Resource
    private StickyNoteCommandApplicationService commandApplicationService;

    @Resource
    private StickyNoteQueryApplicationService queryApplicationService;

    @RequestMapping("/generateDiaryContent")
    public StickyNoteGenerateContentView generateDiaryContent(StickyNoteGenerateContentCommand command) {
        return queryApplicationService.generateDiaryContent(command);
    }

    @RequestMapping("/create")
    public StickyNoteCreateView create(@RequestBody StickyNoteCreateCommand command) {
        return commandApplicationService.createStickyNote(command);
    }

    @RequestMapping("/modify")
    public void modify(@RequestBody StickyNoteModifyCommand command) {
        commandApplicationService.modifyStickyNote(command);
    }
}
