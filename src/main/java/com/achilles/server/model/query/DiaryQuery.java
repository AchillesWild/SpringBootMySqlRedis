package com.achilles.server.model.query;

import com.achilles.model.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryQuery extends PageQuery {

    private String title;

    private String content;

    private Integer type;

    private String userUuid;

    private String remark;

    private Integer pace;

    private Integer status;

    private Long createTimeStart;

    private Long createTimeEnd;
}
