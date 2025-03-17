package com.feiniaojin.ddd.aigc.application.service.stickynote;

import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntity;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntityFactory;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntityId;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class StickyNoteEntityFactoryImpl implements StickyNoteEntityFactory {

    @Override
    public StickyNoteEntity newInstance(String uid, String diaryId, String content, List<String> participants, String occurrenceTimeStr) {

        StickyNoteEntity entity = new StickyNoteEntity();

        String stickyNoteId = RandomStringUtils.randomAlphanumeric(9);
        entity.setStickyNoteEntityId(new StickyNoteEntityId(stickyNoteId));
        entity.setDiaryEntityId(new DiaryEntityId(diaryId));
        entity.setContent(content);
        entity.setParticipants(participants);
        entity.setUid(uid);
        if (StringUtils.isBlank(occurrenceTimeStr)) {
            throw new IllegalArgumentException();
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = dateFormat.parse(occurrenceTimeStr);
            entity.setOccurrenceTime(parse);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        entity.setDeleted(0);

        Date date = new Date();
        entity.setCreatedDate(date);
        entity.setLastModifiedDate(date);

        return entity;
    }
}
