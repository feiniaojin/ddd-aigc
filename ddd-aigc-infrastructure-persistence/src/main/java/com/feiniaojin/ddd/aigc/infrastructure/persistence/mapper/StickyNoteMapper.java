package com.feiniaojin.ddd.aigc.infrastructure.persistence.mapper;

import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.StickyNote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Generated;

/**
 * 表名称：t_sticky_note自动生成的Mapper
 * 表注释：日记贴纸表
 * NOTICE:本文件由代码生成器code-generator生成
 * github：https://github.com/feiniaojin/code-generator
 */
@Generated("generator")
@Repository
public interface StickyNoteMapper {
    int insert(StickyNote record);
    StickyNote findOneById(@Param("id")Long id);
}