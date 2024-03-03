package com.achilles.server.dao;


import com.achilles.server.entity.Diary;
import com.achilles.server.model.query.DiaryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryDao {

    int insert(Diary diary);

    Diary selectByPrimaryKey(Long id, String userUuid);

    int updateByPrimaryKeySelective(Diary diary);

    List<Diary> query(DiaryQuery query);

    long count(DiaryQuery query);
}