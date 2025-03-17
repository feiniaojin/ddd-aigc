package com.feiniaojin.ddd.aigc.application.service.diary;

import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryCreateCommand;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryCreateView;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiarySaveContentCommand;
import com.feiniaojin.ddd.aigc.domain.DiaryEntity;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityFactory;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class DiaryCommandApplicationService {

    @Resource
    private DiaryEntityFactory factory;

    @Resource
    private DiaryEntityRepository repository;

    public DiaryCreateView createDiary(DiaryCreateCommand command) {

        DiaryEntity diary = factory.newInstance(command.getUid(), command.getDiaryDate());
        //执行业务上的创建逻辑
        diary.create();

        repository.save(diary);

        return new DiaryCreateView(diary.getDiaryEntityId().getValue());
    }

    public void saveContent(DiarySaveContentCommand command) {

        DiaryEntity diaryEntity = repository.load(new DiaryEntityId(command.getDiaryId()));
        diaryEntity.modifyContent(command.getContent());
        repository.save(diaryEntity);
    }
}
