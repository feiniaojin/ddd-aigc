package com.feiniaojin.ddd.aigc.application.service.diary.dto;

import lombok.Data;

@Data
public class DiarySaveContentCommand {

    /**
     * 日记ID
     */
    private String diaryId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * AI生成的内容
     */
    private String content;
}
