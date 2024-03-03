package com.achilles.server.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class DiaryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7160845795996379143L;

    private Long id;

//    private String uuid;

    private String title;

    private String content;

    private Integer type;

    private String remark;

    private Integer pace;

    private String createTimeStr;

    private String updateTimeStr;

//    private String picUrl;

//    private Long createTime;
//
//    private Long updateTime;
}
