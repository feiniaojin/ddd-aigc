package com.feiniaojin.ddd.aigc.application.service.diary;

import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryQuery;
import com.feiniaojin.ddd.aigc.application.service.diary.dto.DiaryView;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.data.Diary;
import com.feiniaojin.ddd.aigc.infrastructure.persistence.mapper.DiaryMapper;
import com.feiniaojin.gracefulresponse.data.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiaryQueryApplicationService {

    @Resource
    private DiaryMapper diaryMapper;

    public PageBean<DiaryView> pageList(DiaryQuery query) {

        PageBean<DiaryView> pageBean = new PageBean<>();
        pageBean.setPage(query.getPage());
        pageBean.setPageSize(query.getPageSize());

        Map<String, Object> paramMap = new HashMap<>();

        int count = diaryMapper.countForPageList(paramMap);
        pageBean.setTotal(count);
        if (count == 0) {
            return pageBean;
        }
        paramMap.put("limitStart", (query.getPage() - 1) * query.getPageSize());
        paramMap.put("limitEnd", query.getPageSize());

        List<Diary> liveList = diaryMapper.pageList(paramMap);
        List<DiaryView> views = this.dataToView(liveList);

        pageBean.setList(views);
        pageBean.setTotal(count);
        return pageBean;
    }

    private List<DiaryView> dataToView(List<Diary> list) {

        List<DiaryView> views = new ArrayList<>(list.size());

        for (Diary d : list) {
            DiaryView view = new DiaryView();
            view.setUid(d.getUid());
            view.setContent(d.getContent());
            view.setDiaryId(d.getDiaryId());
            view.setDiaryDateStr(d.getDiaryDateStr());
            views.add(view);
        }

        return views;
    }
}
