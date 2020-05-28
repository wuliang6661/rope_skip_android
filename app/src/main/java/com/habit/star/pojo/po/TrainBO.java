package com.habit.star.pojo.po;

public class TrainBO {


    /**
     * userId : 1
     * trainPlanId : 6
     * skipGrade : D
     * skipTime : 8
     * skipNum : 0
     * breakNum : 0
     * averageVelocity : 0.0
     * accelerateVelocity : 0.0
     * backgroundMusicId : null
     * beat : 0
     * createDate : 2020-05-28T21:52:11
     * updateDate : 2020-05-28T21:52:11
     * remark : null
     * mode : 0
     * maxTime : 0
     * maxNum : 0
     * id : 6
     */

    private int userId;
    private int trainPlanId;
    private String skipGrade;
    private int skipTime;
    private int skipNum;
    private int breakNum;
    private double averageVelocity;
    private double accelerateVelocity;
    private String backgroundMusicId;
    private int beat;
    private String createDate;
    private String updateDate;
    private String remark;
    private int mode;
    private int maxTime;
    private int maxNum;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTrainPlanId() {
        return trainPlanId;
    }

    public void setTrainPlanId(int trainPlanId) {
        this.trainPlanId = trainPlanId;
    }

    public String getSkipGrade() {
        return skipGrade;
    }

    public void setSkipGrade(String skipGrade) {
        this.skipGrade = skipGrade;
    }

    public int getSkipTime() {
        return skipTime;
    }

    public void setSkipTime(int skipTime) {
        this.skipTime = skipTime;
    }

    public int getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(int skipNum) {
        this.skipNum = skipNum;
    }

    public int getBreakNum() {
        return breakNum;
    }

    public void setBreakNum(int breakNum) {
        this.breakNum = breakNum;
    }

    public double getAverageVelocity() {
        return averageVelocity;
    }

    public void setAverageVelocity(double averageVelocity) {
        this.averageVelocity = averageVelocity;
    }

    public double getAccelerateVelocity() {
        return accelerateVelocity;
    }

    public void setAccelerateVelocity(double accelerateVelocity) {
        this.accelerateVelocity = accelerateVelocity;
    }

    public String getBackgroundMusicId() {
        return backgroundMusicId;
    }

    public void setBackgroundMusicId(String backgroundMusicId) {
        this.backgroundMusicId = backgroundMusicId;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
