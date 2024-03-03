package com.achilles.server.controller;

import com.achilles.common.constans.CommonConstant;
import com.achilles.exception.BizException;
import com.achilles.model.response.DataResult;
import com.achilles.model.response.PageResult;
import com.achilles.model.response.code.BaseResultCode;
import com.achilles.server.biz.DiaryBiz;
import com.achilles.server.model.query.DiaryQuery;
import com.achilles.server.model.request.DiaryQryRequest;
import com.achilles.server.model.response.DiaryVO;
import com.achilles.server.service.DiaryService;
import com.achilles.tool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class DiaryQryController {

    @Autowired
    DiaryBiz diaryBiz;

    @Autowired
    DiaryService diaryService;


    @GetMapping("/get/{id}")
    public DataResult get(@PathVariable("id") Long id){

        if (id == null || id <= 0) {
            throw new BizException(BaseResultCode.ILLEGAL_PARAM);
        }

        String userUuid = MDC.get(CommonConstant.USER_UUID);
        DiaryVO diaryVO = diaryService.getById(id, userUuid);
        if (diaryVO == null) {
            throw new BizException(BaseResultCode.DATA_NOT_EXISTS);
        }

        return DataResult.success(diaryVO);
    }

    @GetMapping(path = "/list")
    public DataResult<List<DiaryVO>> getPageList(DiaryQryRequest request){

        if (request.getId() != null) {
            if (request.getId() <= 0) {
                throw new BizException(BaseResultCode.ILLEGAL_PARAM, "id");
            }
            DiaryVO diaryVO = diaryService.getById(request.getId(), MDC.get(CommonConstant.USER_UUID));
            if (diaryVO == null) {
                return PageResult.noPageData();
            }
            PageResult<DiaryVO> pageResult = PageResult.success(Arrays.asList(diaryVO), 1);
            return pageResult;
        }

        DiaryQuery diaryQuery = getLogTimeInfoQuery(request);
        DataResult<List<DiaryVO>> pageResult = diaryBiz.getPageList(diaryQuery);

        return pageResult;
    }

    private DiaryQuery getLogTimeInfoQuery(DiaryQryRequest request) {
        DiaryQuery diaryQuery = new DiaryQuery();
        if (request == null) {
            return diaryQuery;
        }
        BeanUtils.copyProperties(request,diaryQuery);
        String userUuid = MDC.get(CommonConstant.USER_UUID);
        diaryQuery.setUserUuid(userUuid);
        if (StringUtils.isNotEmpty(request.getCreateTimeStart())) {
            diaryQuery.setCreateTimeStart(DateUtil.getCurrentMillis(DateUtil.FORMAT_YYYY_MM_DD_T_HHMMSS,request.getCreateTimeStart()));
        }
        if (StringUtils.isNotEmpty(request.getCreateTimeEnd())) {
            diaryQuery.setCreateTimeEnd(DateUtil.getCurrentMillis(DateUtil.FORMAT_YYYY_MM_DD_T_HHMMSS,request.getCreateTimeEnd()));
        }
        return diaryQuery;
    }
}
