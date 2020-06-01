package com.tohabit.skip.pojo.po;

import java.io.Serializable;

public class FamilyUserBO implements Serializable {


    /**
     * id : 5
     * userId : 1
     * familyUserId : 1
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202004131526246430433.png
     * nickName : 陈阳☀️
     */

    private int id;
    private int userId;
    private int familyUserId;
    private String image;
    private String nickName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(int familyUserId) {
        this.familyUserId = familyUserId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
