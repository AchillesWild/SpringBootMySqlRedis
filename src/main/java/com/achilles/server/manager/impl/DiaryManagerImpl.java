package com.achilles.server.manager.impl;

import com.achilles.common.constans.enums.StatusEnum;
import com.achilles.server.dao.DiaryDao;
import com.achilles.server.entity.Diary;
import com.achilles.server.enums.DiaryPaceEnum;
import com.achilles.server.enums.DiaryTypeEnum;
import com.achilles.server.manager.DiaryManager;
import com.achilles.server.model.query.DiaryQuery;
import com.achilles.tool.date.DateUtil;
import com.achilles.tool.generate.unique.GenerateUniqueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DiaryManagerImpl implements DiaryManager {

    @Autowired
    DiaryDao diaryDao;

    @Override
    public Long add(String title,String content,String remark, Integer type, String userUuid) {

        Assert.state(StringUtils.isNotBlank(title) && StringUtils.isNotBlank(userUuid),"illegal params !");

        Diary diary = new Diary();
        diary.setTitle(title);
        diary.setContent(content);
        diary.setRemark(remark);
        diary.setType(DiaryTypeEnum.OTHER.toNumericalValue());
        if (type != null) {
            diary.setType(type);
        }
        diary.setUserUuid(userUuid);
        diary.setPace(DiaryPaceEnum.INITIAL.toNumericalValue());
        diary.setUuid(GenerateUniqueUtil.getUuId());
        diary.setPicUrl(null); //todo
        diary.setStatus(StatusEnum.NORMAL.toNumericalValue());
        diary.setCreateDate(DateUtil.getCurrentDate());
        diary.setUpdateDate(DateUtil.getCurrentDate());
        diary.setCreateTime(DateUtil.getCurrentMillis());
        diary.setUpdateTime(DateUtil.getCurrentMillis());

        int insert = diaryDao.insert(diary);
        if (insert == 0) {
            throw new RuntimeException("insert fail !");
        }
        return diary.getId();
    }

    @Override
    public Diary getById(Long id, String userUuid) {

        Assert.state(id != null && id > 0,"illegal id !");

        return diaryDao.selectByPrimaryKey(id, userUuid);
    }

    @Override
    public boolean update(Long id ,String title,String content, String remark, Integer type) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(!(StringUtils.isBlank(title) && StringUtils.isBlank(content) && StringUtils.isBlank(remark) && type == null),"illegal params !");

        Diary diary = new Diary();
        diary.setId(id);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setRemark(remark);
        diary.setType(type);
        diary.setUpdateDate(DateUtil.getCurrentDate());
        diary.setUpdateTime(DateUtil.getCurrentMillis());
        int update = diaryDao.updateByPrimaryKeySelective(diary);
        if (update == 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean updatePace(Long id, Integer pace) {

        Assert.state(id != null && id > 0,"illegal id !");
        Assert.state(pace != null && pace >= 0,"illegal pace !");

        Diary diary = new Diary();
        diary.setId(id);
        diary.setPace(pace);
        diary.setUpdateDate(DateUtil.getCurrentDate());
        diary.setUpdateTime(DateUtil.getCurrentMillis());
        int update = diaryDao.updateByPrimaryKeySelective(diary);
        if (update == 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Long id) {

        Assert.state(id != null && id > 0,"illegal id !");

        Diary diary = new Diary();
        diary.setId(id);
        diary.setStatus(StatusEnum.DELETED.toNumericalValue());
        diary.setUpdateDate(DateUtil.getCurrentDate());
        diary.setUpdateTime(DateUtil.getCurrentMillis());
        int update = diaryDao.updateByPrimaryKeySelective(diary);
        if (update == 0) {
            return false;
        }

        return true;
    }

    @Override
    public List<Diary> getList(DiaryQuery query) {
        return diaryDao.query(query);
    }

    @Override
    public long getCount(DiaryQuery query) {
        return diaryDao.count(query);
    }
}
