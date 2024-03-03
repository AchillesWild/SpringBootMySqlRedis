package com.achilles.server.model.request;

import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class DiaryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5814788142627786943L;

    private String title;

    private String content;

    private String remark;

    private Integer type;
}
