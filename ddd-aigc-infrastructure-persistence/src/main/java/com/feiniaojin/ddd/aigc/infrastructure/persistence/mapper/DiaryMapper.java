package com.feiniaojin.ddd.aigc.infrastructure.persistence.mapper;

import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.Diary;
import jakarta.annotation.Generated;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 表名称：t_diary自动生成的Mapper
 * 表注释：日记表
 * NOTICE:本文件由代码生成器code-generator生成
 * github：https://github.com/feiniaojin/code-generator
 */
@Generated("generator")
@Repository
public interface DiaryMapper {
    int insert(Diary record);
    Diary findOneById(@Param("id")Long id);

    int countForPageList(Map<String, Object> paramMap);

    List<Diary> pageList(Map<String, Object> paramMap);
}
