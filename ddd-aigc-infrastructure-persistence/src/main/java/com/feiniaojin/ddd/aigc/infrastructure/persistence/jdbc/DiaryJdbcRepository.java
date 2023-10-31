package com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc;

import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.Diary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 表名称：t_diary自动生成的Repository
 * 表注释：日记表
 * NOTICE:本文件由代码生成器code-generator生成，不要在本文件手工追加任何内容，因为随时可能重新生成替换
 * github：https://github.com/feiniaojin/code-generator
 */
@Repository
public interface DiaryJdbcRepository extends CrudRepository<Diary, Long> {
}
