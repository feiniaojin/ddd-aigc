package com.feiniaojin.ddd.aigc.application.service;

import com.feiniaojin.ddd.aigc.domain.*;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc.StickyNoteJdbcRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateContentDomainServiceImpl implements StickyNoteGenerateContentDomainService {

    @Resource
    private LlmGateway llmGateway;

    @Resource
    private StickyNoteJdbcRepository stickyNoteJdbcRepository;

    @Resource
    private StickyNoteEntityRepository noteEntityRepository;

    /**
     * 经典领域驱动对领域服务的定义
     *
     * @param diaryEntityId
     * @return
     */
    @Override
    public String generateContent(DiaryEntityId diaryEntityId, String userInput) {

        //1.加载日记下的所有贴纸
        List<String> noteIdList = stickyNoteJdbcRepository.queryStickyNoteIdByDiaryId(diaryEntityId.getValue());
        if (CollectionUtils.isEmpty(noteIdList)) {
            return "";
        }

        List<StickyNoteEntity> stickyNoteEntities = new ArrayList<>();
        //2.加载领域模型
        for (String noteId : noteIdList) {
            StickyNoteEntity stickyNoteEntity = noteEntityRepository.load(new StickyNoteEntityId(noteId));
            stickyNoteEntities.add(stickyNoteEntity);
        }

        //2.根据贴纸聚合的集合，生成文本
        return llmGateway.generateContent(stickyNoteEntities, userInput);
    }
}
