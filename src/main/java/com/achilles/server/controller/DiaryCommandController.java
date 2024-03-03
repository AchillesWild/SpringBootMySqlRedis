package com.achilles.server.controller;

import com.achilles.common.constans.CommonConstant;
import com.achilles.exception.BizException;
import com.achilles.model.response.BaseResult;
import com.achilles.model.response.DataResult;
import com.achilles.model.response.code.BaseResultCode;
import com.achilles.server.biz.DiaryBiz;
import com.achilles.server.model.request.DiaryAddRequest;
import com.achilles.server.model.request.DiaryRequest;
import com.achilles.server.model.request.DiaryUpdateRequest;
import com.achilles.server.model.response.AddDiaryVO;
import com.achilles.server.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DiaryCommandController {

    @Autowired
    DiaryBiz diaryBiz;

    @Autowired
    DiaryService diaryService;

    @PostMapping(path = "/add")
    public BaseResult add(@RequestBody DiaryAddRequest request){

        if (StringUtils.isBlank(request.getTitle())) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM, "title is necessary ~ ");
        }
        checkParams (request);

        String userUuid = MDC.get(CommonConstant.USER_UUID);
        Long id = diaryService.add(request.getTitle(), request.getContent(), request.getRemark(), request.getType(), userUuid);
        AddDiaryVO diaryVO = new AddDiaryVO();
        diaryVO.setId(id);

        return DataResult.success(diaryVO);
    }

    @PostMapping("/delete/{id}")
    public BaseResult delete(@PathVariable("id") Long id){

        checkId(id);
        String userUuid = MDC.get(CommonConstant.USER_UUID);
        boolean delete = diaryBiz.delete(id, userUuid);
        if (!delete) {
            throw new BizException(BaseResultCode.FAIL);
        }

        return BaseResult.success();
    }

    private void checkId(Long id) {
        if (id == null || id <= 0) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM ,"id");
        }
    }

    @PostMapping(path = "/update/{id}")
    public BaseResult update(@PathVariable("id") Long id, @RequestBody DiaryUpdateRequest request){

        checkParams (request);
        String userUuid = MDC.get(CommonConstant.USER_UUID);
        boolean update = diaryBiz.update(id, request.getTitle(), request.getContent(), request.getRemark(), userUuid, request.getType());
        if (!update) {
            throw new BizException(BaseResultCode.FAIL);
        }
        return BaseResult.success();
    }

    public void checkParams (DiaryRequest request) {
        String title = request.getTitle();
        String content = request.getContent();
        String remark = request.getRemark();
        Integer type = request.getType();
        if (StringUtils.isNotBlank(title) && title.length() > 64) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM, "title length should be less than 64 ~ ");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 65535) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM, "content length should be less than 65535 ~ ");
        }
        if (StringUtils.isNotBlank(remark) && remark.length() > 128) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM, "remark length should be less than 128 ~ ");
        }
        if (type != null && (type < 0 || type > 4)) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM, "type ~ ");
        }
    }

    @PostMapping(path = "/update/pace/initial/{id}")
    public BaseResult updateInitial(@PathVariable("id") Long id){

        checkId(id);
        String userUuid = MDC.get(CommonConstant.USER_UUID);
        boolean update = diaryBiz.updateInitial(id, userUuid);
        if (!update) {
            throw new BizException(BaseResultCode.FAIL);
        }
        return BaseResult.success();
    }

    @PostMapping(path = "/update/pace/doing/{id}")
    public BaseResult updateDoing(@PathVariable("id") Long id){

        checkId(id);
        String userUuid = MDC.get(CommonConstant.USER_UUID);
        boolean update = diaryBiz.updateDoing(id, userUuid);
        if (!update) {
            throw new BizException(BaseResultCode.FAIL);
        }
        return BaseResult.success();
    }

    @PostMapping(path = "/update/pace/done/{id}")
    public BaseResult updateDone(@PathVariable("id") Long id){

        checkId(id);
        String userUuid = MDC.get(CommonConstant.USER_UUID);
        boolean update = diaryBiz.updateDone(id, userUuid);
        if (!update) {
            throw new BizException(BaseResultCode.FAIL);
        }
        return BaseResult.success();
    }
}
