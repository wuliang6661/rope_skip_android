package com.tohabit.skip.pojo.po;

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
    private String height;
    private String weight;
    private int sex;
    private int challengeSuccessNum;
    private String energyValue;
    private String pkName;
    private String pkValue;
    private String image;
    private String icon;
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
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
