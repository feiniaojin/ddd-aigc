package com.feiniaojin.ddd.aigc.application.service.diary.dto;

import lombok.Data;

@Data
public class DiaryView {

    private String diaryId;

    private String content;

    /**
     * 日记对应的时间，字符串,yyyyMMDD
     */
    private String diaryDateStr;

    /**
     * 用户ID
     */
    private String uid;
}
