package com.achilles.server.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AddDiaryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3087708185351309878L;

    private Long id;

}
