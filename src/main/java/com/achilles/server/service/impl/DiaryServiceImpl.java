package com.achilles.server.service.impl;

import com.achilles.server.entity.Diary;
import com.achilles.server.enums.DiaryPaceEnum;
import com.achilles.server.manager.DiaryManager;
import com.achilles.server.model.query.DiaryQuery;
import com.achilles.server.model.response.DiaryVO;
import com.achilles.server.service.DiaryService;
import com.achilles.tool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryServiceImpl implements DiaryService {


    @Autowired
    DiaryManager diaryManager;

    @Override
    public Long add(String title, String content, String remark, Integer type, String userUuid) {

        Assert.state(StringUtils.isNotBlank(title) && StringUtils.isNotBlank(userUuid),"illegal params !");

        return diaryManager.add(title, content, remark, type, userUuid);
    }

    @Override
    public DiaryVO getById(Long id, String userUuid) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(StringUtils.isNotBlank(userUuid),"illegal userUuid !");

        Diary diary = diaryManager.getById(id, userUuid);
        if (diary == null) {
            return null;
        }
        DiaryVO diaryVO = new DiaryVO();
        BeanUtils.copyProperties(diary,diaryVO);
        diaryVO.setCreateTimeStr(DateUtil.getStrDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS, diary.getCreateTime()));
        diaryVO.setUpdateTimeStr(DateUtil.getStrDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS, diary.getUpdateTime()));

        return diaryVO;
    }

    @Override
    public boolean update(Long id, String title, String content, String remark, Integer type) {

        Assert.state(id != null && id > 0,"illegal params !");
        Assert.state(!(StringUtils.isBlank(title) && StringUtils.isBlank(content) && StringUtils.isBlank(remark) && type == null),"illegal params !");

        return diaryManager.update(id, title, content, remark, type);
    }

    @Override
    public boolean delete(Long id) {

        Assert.state(id != null && id > 0,"illegal id !");

        return diaryManager.delete(id);
    }

    @Override
    public List<DiaryVO> getList(DiaryQuery query) {

        List<Diary> diaryList = diaryManager.getList(query);
        if (diaryList.size() == 0) {
            return new ArrayList<>();
        }

        List<DiaryVO> logTimeInfoVOList = diaryList.stream().map(diary -> {
            DiaryVO diaryVO = new DiaryVO();
            BeanUtils.copyProperties(diary,diaryVO);
            diaryVO.setCreateTimeStr(DateUtil.getStrDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS, diary.getCreateTime()));
            diaryVO.setUpdateTimeStr(DateUtil.getStrDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS,diary.getUpdateTime()));
            return diaryVO;
        }).collect(Collectors.toList());

        return logTimeInfoVOList;
    }

    @Override
    public boolean updateInitial(Long id) {

        Assert.state(id != null && id > 0,"illegal params !");

        return diaryManager.updatePace(id, DiaryPaceEnum.INITIAL.toNumericalValue());
    }

    @Override
    public boolean updateDoing(Long id) {

        Assert.state(id != null && id > 0,"illegal params !");

        return diaryManager.updatePace(id, DiaryPaceEnum.DOING.toNumericalValue());
    }

    @Override
    public boolean updateDone(Long id) {

        Assert.state(id != null && id > 0,"illegal params !");

        return diaryManager.updatePace(id, DiaryPaceEnum.DONE.toNumericalValue());
    }
}
