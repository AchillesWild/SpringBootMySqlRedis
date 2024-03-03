package com.achilles.server.biz.impl;

import com.achilles.exception.BizException;
import com.achilles.model.response.DataResult;
import com.achilles.model.response.PageResult;
import com.achilles.model.response.code.BaseResultCode;
import com.achilles.server.biz.DiaryBiz;
import com.achilles.server.entity.Diary;
import com.achilles.server.manager.DiaryManager;
import com.achilles.server.model.query.DiaryQuery;
import com.achilles.server.model.response.DiaryVO;
import com.achilles.server.service.DiaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DiaryBizImpl implements DiaryBiz {

    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryManager diaryManager;

    @Override
    public DataResult<List<DiaryVO>> getPageList(DiaryQuery query) {

        List<DiaryVO> diaryVOList = diaryService.getList(query);
        if (diaryVOList.size() == 0) {
            return PageResult.noPageData();
        }

        long total = diaryManager.getCount(query);

        PageResult<DiaryVO> pageResult = PageResult.success(diaryVOList,total);

        return pageResult;
    }


    @Override
    public boolean update(Long id, String title, String content, String remark, String userUuid, Integer type) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(StringUtils.isNotBlank(userUuid),"illegal userUuid !");
        Assert.state(!(StringUtils.isBlank(title) && StringUtils.isBlank(content) && StringUtils.isBlank(remark) && type == null),"illegal params !");

        Diary diary = diaryManager.getById(id, userUuid);
        if (diary == null) {
            throw new BizException(BaseResultCode.DATA_NOT_EXISTS);
        }

        return diaryManager.update(id , title, content, remark, type);
    }

    @Override
    public boolean delete(Long id, String userUuid) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(StringUtils.isNotBlank(userUuid),"illegal userUuid !");

        Diary diary = diaryManager.getById(id, userUuid);
        if (diary == null) {
            throw new BizException(BaseResultCode.DATA_NOT_EXISTS);
        }

        return diaryService.delete(id);
    }

    @Override
    public boolean updateInitial(Long id, String userUuid) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(StringUtils.isNotBlank(userUuid),"illegal userUuid !");

        Diary diary = diaryManager.getById(id, userUuid);
        if (diary == null) {
            throw new BizException(BaseResultCode.DATA_NOT_EXISTS);
        }

        return diaryService.updateInitial(id);
    }

    @Override
    public boolean updateDoing(Long id, String userUuid) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(StringUtils.isNotBlank(userUuid),"illegal userUuid !");

        Diary diary = diaryManager.getById(id, userUuid);
        if (diary == null) {
            throw new BizException(BaseResultCode.DATA_NOT_EXISTS);
        }

        return diaryService.updateDoing(id);
    }

    @Override
    public boolean updateDone(Long id, String userUuid) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(StringUtils.isNotBlank(userUuid),"illegal userUuid !");

        Diary diary = diaryManager.getById(id, userUuid);
        if (diary == null) {
            throw new BizException(BaseResultCode.DATA_NOT_EXISTS);
        }

        return diaryService.updateDone(id);
    }
}
