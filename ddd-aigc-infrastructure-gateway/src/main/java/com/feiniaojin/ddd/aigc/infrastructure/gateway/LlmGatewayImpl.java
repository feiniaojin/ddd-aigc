package com.feiniaojin.ddd.aigc.infrastructure.gateway;

import com.feiniaojin.ddd.aigc.domain.LlmGateway;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntity;
import com.feiniaojin.ddd.aigc.infrastructure.gateway.llm.LlmProvider;
import com.feiniaojin.ddd.aigc.infrastructure.gateway.llm.LlmProviderRegister;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * ai调用网关
 */
@Component
public class LlmGatewayImpl implements LlmGateway {

    @Resource
    private LlmProviderRegister llmProviderRegister;

    @Resource
    private LlmConfig llmConfig;

    @Resource
    private ChatMemory chatMemory;

    @Override
    public String generateContent(List<StickyNoteEntity> stickyNoteEntities, String userInput) {
        StickyNoteEntity note = stickyNoteEntities.get(0);
        String uid = note.getUid();
        String diaryId = note.getDiaryEntityId().getValue();

        String conversationId = uid + diaryId;
        List<Message> lastMessage = chatMemory.get(conversationId, 1);
        List<Message> messages = new ArrayList<>();

        //首次增加系统提示词
        if (CollectionUtils.isEmpty(lastMessage)) {
            SystemMessage systemMessage = new SystemMessage(llmConfig.getDefaultSystemMessage());
            messages.add(systemMessage);
        }

        //简单处理，避免贴纸有新增
        String content = noteContent(stickyNoteEntities);
        UserMessage userMessage = new UserMessage(content + userInput);
        messages.add(userMessage);

        String providerName = llmConfig.getProviderName();
        LlmProvider llmProvider = llmProviderRegister.getLlmProvider(providerName);
        Prompt prompt = new Prompt(messages);
        return llmProvider.generateContent(prompt, conversationId);
    }

    /**
     * 根据StickyNoteEntity生成内容
     *
     * @param stickyNoteEntities
     * @return
     */
    private String noteContent(List<StickyNoteEntity> stickyNoteEntities) {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        StringBuilder stringBuilder = new StringBuilder();

        for (StickyNoteEntity note : stickyNoteEntities) {
            //08:00:00 和aaa、bbb一起，正文
            if (note.getOccurrenceTime() != null) {
                stringBuilder.append("时间：");
                stringBuilder.append(dateFormat.format(note.getOccurrenceTime()));
                stringBuilder.append("，");
            }

            List<String> participants = note.getParticipants();
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
