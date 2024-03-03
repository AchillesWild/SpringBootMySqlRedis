package com.achilles.server.model.request;

import com.achilles.model.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryQryRequest extends PageRequest {

    private Long id;

    private String title;

    private String content;

    private Integer type;

    private String remark;

    private Integer pace;

    private String createTimeStart;

    private String createTimeEnd;
}
