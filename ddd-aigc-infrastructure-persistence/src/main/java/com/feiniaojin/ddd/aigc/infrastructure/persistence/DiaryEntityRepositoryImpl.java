package com.feiniaojin.ddd.aigc.infrastructure.persistence;

import com.feiniaojin.ddd.aigc.domain.DiaryEntity;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityRepository;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.Diary;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc.DiaryJdbcRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DiaryEntityRepositoryImpl implements DiaryEntityRepository {

    @Resource
    private DiaryJdbcRepository diaryJdbcRepository;

    @Override
    public DiaryEntity load(DiaryEntityId entityId) {
        return null;
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
        diary.setCreatedTime(entity.getCreatedTime());
        diary.setModifiedTime(entity.getModifiedTime());

        diaryJdbcRepository.save(diary);
    }
}
