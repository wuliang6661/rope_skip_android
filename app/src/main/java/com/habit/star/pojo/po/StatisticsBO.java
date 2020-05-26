package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2616:52
 * desc   : 统计
 * version: 1.0
 */
public class StatisticsBO {


    /**
     * skipNum : 0
     * skipTime : 0
     * breakNum : 0
     * averageVelocity : 0.0
     * accelerateVelocity : 0.0
     * createDate : 5/20
     */

    private int skipNum;
    private int skipTime;
    private int breakNum;
    private double averageVelocity;
    private double accelerateVelocity;
    private String createDate;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
