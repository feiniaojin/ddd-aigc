package com.feiniaojin.ddd.aigc.application.service.stickynote;

import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.*;
import com.feiniaojin.ddd.aigc.domain.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class StickyNoteCommandApplicationService {

    @Resource
    private StickyNoteEntityFactory factory;

    @Resource
    private StickyNoteEntityRepository repository;


    public StickyNoteCreateView createStickyNote(StickyNoteCreateCommand command) {

        StickyNoteEntity entity = factory.newInstance(command.getUid(),
                command.getDiaryId(),
                command.getContent(),
                command.getParticipants(),
                command.getOccurrenceTimeStr());

        entity.completeCreate();

        repository.save(entity);

        return new StickyNoteCreateView(entity.getStickyNoteEntityId().getValue());
    }

    public void modifyStickyNote(StickyNoteModifyCommand command) {

        StickyNoteEntity entity = repository.load(new StickyNoteEntityId(command.getStickyNoteId()));

        entity.modify(command.getContent(), command.getParticipants());

        repository.save(entity);
    }


}
