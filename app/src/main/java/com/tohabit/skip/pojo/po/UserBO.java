package com.tohabit.skip.pojo.po;

import java.io.Serializable;

public class UserBO implements Serializable {


    /**
     * id : 1
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoiV2VkIE1heSAwNiAyMDozNTozMCBDU1QgMjAyMCIsInVzZXJuYW1lIjoiMTU4NTYzODU3NjYifQ.TygduBov-lWREQ7ZTUeqxj7Ge9EQAymOPamH7TInxnw
     * phone : 15856385766
     * userCode : 2003260033
     * nickName : 陈阳☀️
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202004131526246430433.png
     * isBuy : 0
     * isDayPush : 0
     * youngGeneralCount : 1
     */

    private int id;
    private String token;
    private String phone;
    private String userCode;
    private String nickName;
    private String image;
    private int isBuy;
    private int isDayPush;
    private int youngGeneralCount;
    /**
     * birthDate :
     * downloadUrl :
     * height : 0
     * sex : 0
     * weight : 0
     */

    private String birthDate;
    private String downloadUrl;
    private String height;
    private int sex;
    private String weight;
    /**
     * weight : 50.0
     * height : 180.0
     * maxTime : 30
     * maxNum : 60
     * percent : 1
     */

    private int maxTime;
    private int maxNum;
    private int percent;
    private String url;
    private int type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }

    public int getIsDayPush() {
        return isDayPush;
    }

    public void setIsDayPush(int isDayPush) {
        this.isDayPush = isDayPush;
    }

    public int getYoungGeneralCount() {
        return youngGeneralCount;
    }

    public void setYoungGeneralCount(int youngGeneralCount) {
        this.youngGeneralCount = youngGeneralCount;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
