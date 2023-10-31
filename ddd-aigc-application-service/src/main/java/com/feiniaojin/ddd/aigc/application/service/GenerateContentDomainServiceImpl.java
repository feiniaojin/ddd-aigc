package com.feiniaojin.ddd.aigc.application.service;

import com.feiniaojin.ddd.aigc.domain.DiaryEntityId;
import com.feiniaojin.ddd.aigc.domain.OpenAiGateway;
import com.feiniaojin.ddd.aigc.domain.StickyNoteGenerateContentDomainService;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.StickyNote;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.jdbc.StickyNoteJdbcRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateContentDomainServiceImpl implements StickyNoteGenerateContentDomainService {

    @Resource
    private OpenAiGateway openAiGateway;

    @Resource
    private StickyNoteJdbcRepository stickyNoteJdbcRepository;

    Gson gson = new Gson();

    @Override
    public String generateContent(DiaryEntityId diaryEntityId) {

        //1.加载日记下的所有贴纸，由于不修改状态，我们不需要加载领域模型
        List<StickyNote> noteList = stickyNoteJdbcRepository.queryListByDiaryId(diaryEntityId.getValue());
        //2.拼写报文
        if (CollectionUtils.isEmpty(noteList)) {
            return "";
        }

        String uid = noteList.get(0).getUid();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        StringBuilder sb = new StringBuilder();

        for (StickyNote note : noteList) {
            //08:00:00 和aaa、bbb一起，正文
            if (note.getOccurrenceTime() != null) {
                sb.append("时间：");
                sb.append(dateFormat.format(note.getOccurrenceTime()));
                sb.append("，");
            }

            String participants = note.getParticipants();
            if (StringUtils.isNoneBlank(participants)) {
                sb.append("参与者：");
                List<String> pList = gson.fromJson(participants, new TypeToken<ArrayList<String>>() {
                }.getType());
                for (String p : pList) {
                    sb.append(p);
                    sb.append("、");
                }
                sb.deleteCharAt(sb.lastIndexOf("、"));
                sb.append("，");
            }

            //活动
            sb.append("活动：");
            sb.append(note.getContent());
            sb.append("；");
        }
        String noteContents = sb.toString();
        //2.根据贴纸聚合的集合，生成文本
        String generateContent = openAiGateway.generateContent(uid, noteContents);
        return generateContent;
    }
}
