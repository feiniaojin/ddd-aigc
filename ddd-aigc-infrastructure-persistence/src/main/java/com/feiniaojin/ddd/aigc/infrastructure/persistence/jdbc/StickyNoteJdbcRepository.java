package com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc;

import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.StickyNote;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表名称：t_sticky_note自动生成的Repository
 * 表注释：日记贴纸表
 * NOTICE:本文件由代码生成器code-generator生成，不要在本文件手工追加任何内容，因为随时可能重新生成替换
 * github：https://github.com/feiniaojin/code-generator
 */
@Repository
public interface StickyNoteJdbcRepository extends CrudRepository<StickyNote, Long> {
    @Query("select * from t_sticky_note where sticky_note_id=:stickyNoteId and deleted=0 limit 1")
    StickyNote queryOneByBizId(String stickyNoteId);

    @Query("select sticky_note_id from t_sticky_note where diary_id=:diaryId and deleted=0")
    List<String> queryStickyNoteIdByDiaryId(String diaryId);

    @Query("select * from t_sticky_note where diary_id=:diaryId and deleted=0")
    List<StickyNote> queryListByDiaryId(String diaryId);
}
