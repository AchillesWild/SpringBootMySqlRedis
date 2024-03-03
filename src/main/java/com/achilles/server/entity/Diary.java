package com.achilles.server.entity;

import com.achilles.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diary extends BaseEntity {

    private String title;

    private String content;

    private Integer type;

    private String userUuid;

    private String remark;

    private Integer pace;

    private String picUrl;

}