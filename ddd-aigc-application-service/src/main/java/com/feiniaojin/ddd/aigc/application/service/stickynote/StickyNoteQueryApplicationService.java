package com.feiniaojin.ddd.aigc.application.service.stickynote;

import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteGenerateContentCommand;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteGenerateContentView;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteQuery;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteView;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.StickyNoteGenerateContentDomainService;
import com.feiniaojin.gracefulresponse.data.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StickyNoteQueryApplicationService {

    @Resource
    private StickyNoteGenerateContentDomainService generateContentDomainService;

    private PageBean<StickyNoteView> pageList(StickyNoteQuery query) {

        return null;
    }

    public StickyNoteGenerateContentView generateDiaryContent(StickyNoteGenerateContentCommand command) {

        String content = generateContentDomainService.generateContent(new DiaryEntityId(command.getDiaryId()));

        StickyNoteGenerateContentView view = new StickyNoteGenerateContentView();
        view.setGenerateContent(content);

        return view;
    }
}
