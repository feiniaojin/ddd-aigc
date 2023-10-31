package com.feiniaojin.ddd.aigc.domain;

import com.feiniaojin.ddd.AbstractDomainMask;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 便利贴
 */
@Data
public class StickyNoteEntity extends AbstractDomainMask {

    /**
     * 便利贴ID
     */
    private StickyNoteEntityId stickyNoteEntityId;


    /**
     * 日记ID
     */
    private DiaryEntityId diaryEntityId;

    /**
     * 贴纸正文
     */
    private String content;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 参与者
     */
    private List<String> participants;

    /**
     * 贴纸对应的发生时间
     * 2023-01-01 08:00:00
     */
    private Date occurrenceTime;

    /**
     * 修改贴纸信息
     *
     * @param content
     * @param participants
     */
    public void modify(String content, List<String> participants) {
        this.content = content;
        this.participants = participants;
    }

    /**
     * 创建贴纸
     */
    public void completeCreate() {

    }
}
