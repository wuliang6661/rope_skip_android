package com.tohabit.skip.pojo.po;

public class TestDataBO {


    /**
     * userId : 1
     * totalMinute : 10
     * totalDay : 2
     * skipTotalNum : 102
     * testTotalNum : 100
     * createDate : 2020-05-07T10:57:00
     * updateDate : 2020-05-07T10:57:00
     * remark : null
     * id : 1
     */

    private int userId;
    private String totalMinute;
    private int totalDay;
    private int skipTotalNum;
    private int testTotalNum;
    private String createDate;
    private String updateDate;
    private String remark;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTotalMinute() {
        return totalMinute;
    }

    public void setTotalMinute(String totalMinute) {
        this.totalMinute = totalMinute;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public int getSkipTotalNum() {
        return skipTotalNum;
    }

    public void setSkipTotalNum(int skipTotalNum) {
        this.skipTotalNum = skipTotalNum;
    }

    public int getTestTotalNum() {
        return testTotalNum;
    }

    public void setTestTotalNum(int testTotalNum) {
        this.testTotalNum = testTotalNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
