package com.achilles.server.enums;

import com.achilles.common.constans.enums.DescEnum;
import com.achilles.common.constans.enums.NumericalEnum;

public enum DiaryTypeEnum implements DescEnum, NumericalEnum {


    OTHER(0,"其他"),

    DAILY(1,"日常"),

    LEARN(2,"学习"),

    TODO(3,"待办"),

    TOUR(4,"旅行");

    private Integer value;
    private String desc;

    DiaryTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public int toNumericalValue() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }
}
