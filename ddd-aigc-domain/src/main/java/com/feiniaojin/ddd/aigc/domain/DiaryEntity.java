package com.feiniaojin.ddd.aigc.domain;

import com.feiniaojin.ddd.AbstractDomainMask;
import lombok.Data;

import java.util.Date;

/**
 * 一篇日记
 */
@Data
public class DiaryEntity extends AbstractDomainMask {

    /**
     * 日记id
     */
    private DiaryEntityId diaryEntityId;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 日记记录的那一天
     */
    private Date diaryDate;

    /**
     * 日记记录的那一天
     */
    private String diaryDateStr;

    /**
     * 根据白板生成的日记内容
     */
    private String content;

    public void create() {

    }

    public void modifyContent(String content) {
        this.content = content;
    }
}
