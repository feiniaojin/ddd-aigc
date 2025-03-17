package com.feiniaojin.ddd.aigc.infrastructure.persistence;

import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntity;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntityId;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntityRepository;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.StickyNote;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc.StickyNoteJdbcRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StickyNoteEntityRepositoryImpl implements StickyNoteEntityRepository {

    Gson gson = new Gson();

    @Resource
    private StickyNoteJdbcRepository jdbcRepository;

    @Override
    public StickyNoteEntity load(StickyNoteEntityId entityId) {

        String entityIdValue = entityId.getValue();

        StickyNote stickyNote = jdbcRepository.queryOneByBizId(entityIdValue);

        StickyNoteEntity entity = new StickyNoteEntity();
        entity.setStickyNoteEntityId(entityId);
        entity.setContent(stickyNote.getContent());
        entity.setUid(stickyNote.getUid());
        entity.setDiaryEntityId(new DiaryEntityId(stickyNote.getDiaryId()));
        String participants = stickyNote.getParticipants();
        List<String> pList = gson.fromJson(participants, new TypeToken<ArrayList<String>>() {
        }.getType());
        entity.setParticipants(pList);
        entity.setOccurrenceTime(stickyNote.getOccurrenceTime());

        //维护层超类型
        entity.setId(stickyNote.getId());
        entity.setDeleted(stickyNote.getDeleted());
        entity.setVersion(stickyNote.getVersion());
        entity.setCreatedDate(stickyNote.getCreatedDate());
        entity.setLastModifiedDate(stickyNote.getLastModifiedDate());

        return entity;
    }

    @Override
    public void save(StickyNoteEntity entity) {

        StickyNote stickyNote = new StickyNote();

        stickyNote.setStickyNoteId(entity.getStickyNoteEntityId().getValue());
        stickyNote.setDiaryId(entity.getDiaryEntityId().getValue());
        stickyNote.setUid(entity.getUid());
        stickyNote.setContent(entity.getContent());
        stickyNote.setParticipants(gson.toJson(entity.getParticipants()));
        stickyNote.setOccurrenceTime(entity.getOccurrenceTime());

        stickyNote.setId(entity.getId());
        stickyNote.setVersion(entity.getVersion());
        stickyNote.setDeleted(entity.getDeleted());
        stickyNote.setCreatedDate(entity.getCreatedDate());
        stickyNote.setLastModifiedDate(entity.getLastModifiedDate());

        jdbcRepository.save(stickyNote);
    }

}
