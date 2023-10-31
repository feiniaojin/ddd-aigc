package com.feiniaojin.ddd.aigc.domain;


public interface DiaryEntityFactory {

    /**
     * 创建新日记
     *
     * @param uid       用户id
     * @param diaryDate 日记当天
     * @return
     */
    DiaryEntity newInstance(String uid, String diaryDate);
}
