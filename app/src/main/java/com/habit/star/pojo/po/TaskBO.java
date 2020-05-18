package com.habit.star.pojo.po;

public class TaskBO {


    /**
     * id : 1
     * type : 0
     * typeName : 即时PK
     * name : 即时PK挑战更高战力
     * describes : *奖励10枚能量晶体
     */

    private int id;
    private int type;
    private String typeName;
    private String name;
    private String describes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
