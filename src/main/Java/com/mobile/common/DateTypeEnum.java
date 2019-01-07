package com.mobile.common;
/**
 * 日期类型的枚举(运行的指标)
 * */
public enum DateTypeEnum {
    YEAR("year"),
    SEASON("season"),
    MONTH("month"),
    WEEK("week"),
    DAY("day"),
    HOUR("hour")
    ;

    public String type;

    DateTypeEnum(String type) {
        this.type = type;
    }

    /**
     * 根据传进来的type来获取对应的枚举类型
     * */
    public static DateTypeEnum valueOfType(String type){
        for(DateTypeEnum date:values()){
            if(date.type.equals(type)){
                return date;
            }
        }
        return null;
    }
}
