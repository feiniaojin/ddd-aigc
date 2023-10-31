package com.feiniaojin.ddd.aigc.application.service.diary;

import com.feiniaojin.ddd.aigc.domain.DiaryEntity;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityFactory;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DiaryEntityFactoryImpl implements DiaryEntityFactory {
    @Override
    public DiaryEntity newInstance(String uid, String diaryDateStr) {

        DiaryEntity diaryEntity = new DiaryEntity();

        diaryEntity.setUid(uid);

        if (StringUtils.isBlank(diaryDateStr)) {
            throw new IllegalArgumentException("diaryDate为空");
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date parse = dateFormat.parse(diaryDateStr);
            diaryEntity.setDiaryDate(parse);
            diaryEntity.setDiaryDateStr(diaryDateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String did = RandomStringUtils.randomAlphabetic(9);
        diaryEntity.setDiaryEntityId(new DiaryEntityId(did));
        //维护层超类型
        diaryEntity.setDeleted(0);
        Date date = new Date();
        diaryEntity.setCreatedTime(date);
        diaryEntity.setModifiedTime(date);
        return diaryEntity;
    }
}
