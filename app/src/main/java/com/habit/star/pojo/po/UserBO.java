package com.habit.star.pojo.po;

public class UserBO {


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
}
