package com.habit.star.ui.train.bean;


import com.habit.star.model.bean.BaseMode;


/**
 * @date:  2020-02-11 22:42
 * @ClassName: ImprovePlanModel.java
 * @Description:改良方案
 * @author: sundongdong
 * @version V1.0
 */
public class ImprovePlanModel extends BaseMode {
    public String id;
    public int state;
    public String stateName;
    public String content;
    public String trainTime;
    public String trainTimeUnit;
    public String planTime;
    public String planTimeUnit;
    public String energy;
    public String trainIntensity;
}
