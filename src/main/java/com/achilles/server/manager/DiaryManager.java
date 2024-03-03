package com.achilles.server.manager;

import com.achilles.server.entity.Diary;
import com.achilles.server.model.query.DiaryQuery;

import java.util.List;

public interface DiaryManager {

    Long add(String title, String content, String remark, Integer type, String userUuid);

    Diary getById(Long id, String userUuid);

    boolean update(Long id, String title, String content, String remark, Integer type);

    boolean updatePace(Long id, Integer pace);

    boolean delete(Long id);

    List<Diary> getList(DiaryQuery query);

    long getCount(DiaryQuery query);
}
