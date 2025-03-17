package com.feiniaojin.ddd.aigc.application.service.stickynote;

import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteGenerateContentQuery;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteGenerateContentView;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteQuery;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.StickyNoteView;
import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.StickyNoteGenerateContentDomainService;
import com.feiniaojin.ddd.aigc.infrastructure.gateway.LlmConfig;
import com.feiniaojin.ddd.aigc.infrastructure.gateway.llm.LlmProvider;
import com.feiniaojin.ddd.aigc.infrastructure.gateway.llm.LlmProviderRegister;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.StickyNote;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc.StickyNoteJdbcRepository;
import com.feiniaojin.gracefulresponse.data.PageBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class StickyNoteQueryApplicationService {

    @Resource
    private StickyNoteGenerateContentDomainService generateContentDomainService;

    @Resource
    private StickyNoteJdbcRepository stickyNoteJdbcRepository;

    Gson gson = new Gson();

    @Resource
    private LlmProviderRegister llmProviderRegister;

    @Resource
    private LlmConfig llmConfig;


    private PageBean<StickyNoteView> pageList(StickyNoteQuery query) {

        return null;
    }

    public StickyNoteGenerateContentView generateDiaryContent(StickyNoteGenerateContentQuery query) {

        String content = generateContentDomainService.generateContent(new DiaryEntityId(query.getDiaryId()), query.getUserInput());

        StickyNoteGenerateContentView view = new StickyNoteGenerateContentView();
        view.setGenerateContent(content);

        return view;
    }

    public Flux<String> generateDiaryContentStream(StickyNoteGenerateContentQuery query) {
        String diaryId = query.getDiaryId();
        //直接查询
        List<StickyNote> stickyNotes = stickyNoteJdbcRepository.queryListByDiaryId(diaryId);
        String content = noteContent(stickyNotes);
        String providerName = llmConfig.getProviderName();
        LlmProvider llmProvider = llmProviderRegister.getLlmProvider(providerName);
        SystemMessage systemMessage = new SystemMessage(llmConfig.getDefaultSystemMessage() + content);
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        String userInput = query.getUserInput();
        if (StringUtils.isBlank(userInput)) {
            UserMessage userMessage = new UserMessage("风格、字数等无限制");
            messages.add(userMessage);
        } else {
            UserMessage userMessage = new UserMessage(userInput);
            messages.add(userMessage);
        }
        Prompt prompt = new Prompt(messages);
        StickyNote stickyNote = stickyNotes.get(0);
        String conversationId = stickyNote.getUid() + stickyNote.getDiaryId();
        return llmProvider.generateContentStream(prompt, conversationId);
    }

    /**
     * 根据StickyNoteEntity生成内容
     *
     * @param stickyNoteEntities
     * @return
     */
    private String noteContent(List<StickyNote> stickyNoteEntities) {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        StringBuilder stringBuilder = new StringBuilder();

        for (StickyNote note : stickyNoteEntities) {
            //08:00:00 和aaa、bbb一起，正文
            if (note.getOccurrenceTime() != null) {
                stringBuilder.append("时间：");
                stringBuilder.append(dateFormat.format(note.getOccurrenceTime()));
                stringBuilder.append("，");
            }
            String string = note.getParticipants();
            List<String> participants = gson.fromJson(string, new TypeToken<List<String>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(participants)) {
                stringBuilder.append("参与者：");
                for (String p : participants) {
                    stringBuilder.append(p);
                    stringBuilder.append("、");
                }
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("、"));
                stringBuilder.append("，");
            }

            //活动
            stringBuilder.append("活动：");
            stringBuilder.append(note.getContent());
            stringBuilder.append("；");
        }
        return stringBuilder.toString();
    }
}
