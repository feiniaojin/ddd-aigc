package com.feiniaojin.ddd.aigc.application.service.diary.dto;

import lombok.Data;

@Data
public class DiaryQuery {
    private Integer pageSize = 10;

    private Integer page = 1;
}
