package com.achilles.server.biz;

import com.achilles.model.response.DataResult;
import com.achilles.server.model.query.DiaryQuery;
import com.achilles.server.model.response.DiaryVO;

import java.util.List;

public interface DiaryBiz {

    DataResult<List<DiaryVO>> getPageList(DiaryQuery query);

    boolean update(Long id ,String title,String content,String remark, String userUuid, Integer type);

    boolean delete(Long id, String userUuid);

    boolean updateInitial(Long id, String userUuid);

    boolean updateDoing(Long id, String userUuid);

    boolean updateDone(Long id, String userUuid);
}
