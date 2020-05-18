package com.habit.star.pojo.po;

import java.io.Serializable;

public class XIaoJiangBO implements Serializable {


    /**
     * id : 1
     * nickName : 逗你玩
     * height : 160.0
     * weight : 89.0
     * sex : 0
     * challengeSuccessNum : 0
     * energyValue : 6260
     * pkName : 黄金斗神
     * pkValue : 5690
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004281632492535443.png
     * icon : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004281649323160250.png
     */

    private int id;
    private String nickName;
    private double height;
    private double weight;
    private int sex;
    private int challengeSuccessNum;
    private String energyValue;
    private String pkName;
    private String pkValue;
    private String image;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getChallengeSuccessNum() {
        return challengeSuccessNum;
    }

    public void setChallengeSuccessNum(int challengeSuccessNum) {
        this.challengeSuccessNum = challengeSuccessNum;
    }

    public String getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(String energyValue) {
        this.energyValue = energyValue;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
