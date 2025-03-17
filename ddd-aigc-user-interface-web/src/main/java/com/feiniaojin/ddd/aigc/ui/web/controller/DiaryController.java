package com.feiniaojin.ddd.aigc.ui.web.controller;

import com.feiniaojin.ddd.aigc.application.service.diary.DiaryCommandApplicationService;
import com.feiniaojin.ddd.aigc.application.service.diary.DiaryQueryApplicationService;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.*;
import com.feiniaojin.gracefulresponse.data.PageBean;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Resource
    private DiaryCommandApplicationService commandApplicationService;

    @Resource
    private DiaryQueryApplicationService queryApplicationService;

    @RequestMapping("/create")
    public DiaryCreateView createDiary(@RequestBody DiaryCreateCommand command) {
        return commandApplicationService.createDiary(command);
    }

    @RequestMapping("/pageList")
    public PageBean<DiaryView> pageList(DiaryQuery query) {
        return queryApplicationService.pageList(query);
    }


    @RequestMapping("/saveContent")
    public void saveContent(@RequestBody DiarySaveContentCommand command) {
        commandApplicationService.saveContent(command);
    }
}
