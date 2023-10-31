package com.feiniaojin.ddd.aigc.ui.web.controller;

import com.feiniaojin.ddd.aigc.application.service.diary.DiaryCommandApplicationService;
import com.feiniaojin.ddd.aigc.application.service.diary.DiaryQueryApplicationService;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryCreateCommand;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryCreateView;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryQuery;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryView;
import com.feiniaojin.gracefulresponse.data.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
