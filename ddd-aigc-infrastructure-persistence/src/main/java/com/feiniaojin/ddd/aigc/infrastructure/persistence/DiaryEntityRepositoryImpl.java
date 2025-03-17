package com.feiniaojin.ddd.aigc.infrastructure.persistence;

import com.feiniaojin.ddd.aigc.domain.DiaryEntity;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityRepository;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.Diary;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc.DiaryJdbcRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


@Component
public class DiaryEntityRepositoryImpl implements DiaryEntityRepository {

    @Resource
    private DiaryJdbcRepository diaryJdbcRepository;

    @Override
    public DiaryEntity load(DiaryEntityId entityId) {

        Diary diary = diaryJdbcRepository.findByDiaryId(entityId.getValue());

        DiaryEntity entity = new DiaryEntity();

        entity.setDiaryEntityId(entityId);
        entity.setContent(diary.getContent());
        entity.setUid(diary.getUid());
        entity.setDiaryDateStr(diary.getDiaryDateStr());
        entity.setDiaryDate(diary.getDiaryDate());

        entity.setId(diary.getId());
        entity.setCreatedBy(diary.getCreatedBy());
        entity.setCreatedDate(diary.getCreatedDate());
        entity.setLastModifiedBy(diary.getModifiedBy());
        entity.setLastModifiedDate(diary.getLastModifiedDate());
        entity.setVersion(diary.getVersion());
        return entity;
    }

    @Override
    public void save(DiaryEntity entity) {

        Diary diary = new Diary();

        diary.setContent(entity.getContent());
        diary.setDiaryDate(entity.getDiaryDate());
        diary.setDiaryDateStr(entity.getDiaryDateStr());
        diary.setUid(entity.getUid());
        diary.setDiaryId(entity.getDiaryEntityId().getValue());

        diary.setVersion(entity.getVersion());
        diary.setId(entity.getId());
        diary.setDeleted(entity.getDeleted());
        diary.setCreatedDate(entity.getCreatedDate());
        diary.setLastModifiedDate(entity.getCreatedDate());

        diaryJdbcRepository.save(diary);
    }
}
