package com.achilles.server.service;

import com.achilles.server.model.query.DiaryQuery;
import com.achilles.server.model.response.DiaryVO;

import java.util.List;

public interface DiaryService {

    Long add(String title,String content,String remark, Integer type, String userUuid);

    boolean update(Long id ,String title,String content,String remark, Integer type);

    boolean updateInitial(Long id);

    boolean updateDoing(Long id);

    boolean updateDone(Long id);

    boolean delete(Long id);

    DiaryVO getById(Long id, String userUuid);

    List<DiaryVO> getList(DiaryQuery query);
}
