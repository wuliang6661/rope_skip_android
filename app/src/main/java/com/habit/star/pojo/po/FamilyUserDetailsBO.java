package com.habit.star.pojo.po;

import java.util.List;

public class FamilyUserDetailsBO {


    /**
     * id : 5
     * userId : 1
     * familyUserId : 1
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202004131526246430433.png
     * nickName : 陈阳☀️
     * sex : 0
     * height : 160.0
     * weight : 89.0
     * age : 15
     * skipDataList : [{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月07日"},{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月08日"},{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月09日"},{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月10日"},{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月11日"},{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月12日"},{"skipNum":0,"skipTime":0,"breakNum":0,"testDate":"5月13日"}]
     */

    private int id;
    private int userId;
    private int familyUserId;
    private String image;
    private String nickName;
    private int sex;
    private double height;
    private double weight;
    private int age;
    private List<SkipDataListBean> skipDataList;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<SkipDataListBean> getSkipDataList() {
        return skipDataList;
    }

    public void setSkipDataList(List<SkipDataListBean> skipDataList) {
        this.skipDataList = skipDataList;
    }

    public static class SkipDataListBean {
        /**
         * skipNum : 0
         * skipTime : 0
         * breakNum : 0
         * testDate : 5月07日
         */

        private int skipNum;
        private int skipTime;
        private int breakNum;
        private String testDate;

        public int getSkipNum() {
            return skipNum;
        }

        public void setSkipNum(int skipNum) {
            this.skipNum = skipNum;
        }

        public int getSkipTime() {
            return skipTime;
        }

        public void setSkipTime(int skipTime) {
            this.skipTime = skipTime;
        }

        public int getBreakNum() {
            return breakNum;
        }

        public void setBreakNum(int breakNum) {
            this.breakNum = breakNum;
        }

        public String getTestDate() {
            return testDate;
        }

        public void setTestDate(String testDate) {
            this.testDate = testDate;
        }
    }
}
