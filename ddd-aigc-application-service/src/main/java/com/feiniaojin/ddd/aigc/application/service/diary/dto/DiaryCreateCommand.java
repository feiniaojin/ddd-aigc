package com.feiniaojin.ddd.aigc.application.service.diary.dto;

import lombok.Data;

@Data
public class DiaryCreateCommand {

    /**
     * 写日记的当天
     * 按照格式传过来yyyyMMdd
     */
    private String diaryDate;

    /**
     * 用户id
     */
    private String uid;
}
