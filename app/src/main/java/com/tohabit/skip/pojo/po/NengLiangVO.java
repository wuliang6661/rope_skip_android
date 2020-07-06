package com.tohabit.skip.pojo.po;

import android.widget.ScrollView;

public class NengLiangVO {


    /**
     * id : 153
     * energyValue : 10
     * code : 06
     * title : 每日登录
     * content : 获取能量值
     * createDate : null
     */

    private int id;
    private String energyValue;
    private String code;
    private String title;
    private String content;
    private String createDate;
    private String color;
    private String icon;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(String energyValue) {
        this.energyValue = energyValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
