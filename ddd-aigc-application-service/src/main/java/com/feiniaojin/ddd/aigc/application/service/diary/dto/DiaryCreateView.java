package com.feiniaojin.ddd.aigc.application.service.diary.dto;

import lombok.Data;

@Data
public class DiaryCreateView {
    private String diaryId;

    public DiaryCreateView(String diaryId) {
        this.diaryId = diaryId;
    }
}
