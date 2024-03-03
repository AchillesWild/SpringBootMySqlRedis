package com.achilles.server.enums;

import com.achilles.common.constans.enums.DescEnum;
import com.achilles.common.constans.enums.NumericalEnum;

public enum DiaryPaceEnum implements DescEnum, NumericalEnum {


    INITIAL(0,"未处理/不需要处理"),

    DOING(1,"处理中"),

    DONE(2,"已处理");

    private Integer value;
    private String desc;

    DiaryPaceEnum(Integer value, String desc) {
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
