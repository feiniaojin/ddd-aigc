package com.feiniaojin.ddd.aigc.infrastructure.persistence.data;

import jakarta.annotation.Generated;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名称：t_sticky_note
 * 表注释：日记贴纸表
 * NOTICE:本文件由代码生成器code-generator生成，不要在本文件手工追加任何内容，因为随时可能重新生成替换
 * github：https://github.com/feiniaojin/code-generator
 */
@Data
@Table("t_sticky_note")
@Generated("generator")
public class StickyNote implements Serializable {
    /**
     * 自增主键
     */
    @Id
    private Long id;
    /**
     * 贴纸id
     */
    private String stickyNoteId;
    /**
     * 贴纸正文
     */
    private String content;
    /**
     * 日记ID
     */
    private String diaryId;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 参与者，字符串数组的json,['aaa','bbb']
     */
    private String participants;
    /**
     * 创建时间
     */
    private Date occurrenceTime;
    /**
     * 逻辑删除标记[0-正常；1-已删除]
     */
    private Integer deleted;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
     @CreatedDate
    private Date createdDate;
    /**
     * 更新人
     */
    private String modifiedBy;
    /**
     * 更新时间
     */
    @LastModifiedDate
    private Date lastModifiedDate;
    /**
     * 乐观锁
     */
    @Version
    private Long version;
}
