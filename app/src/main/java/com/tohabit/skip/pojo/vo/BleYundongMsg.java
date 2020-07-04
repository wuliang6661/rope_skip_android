package com.tohabit.skip.pojo.vo;

public class BleYundongMsg {


    /**
     * 运动时间
     */
    private int timeCount;

    /**
     * 跳绳次数
     */
    private int skipNum;

    /**
     * 断绳次数
     */
    private int breakNum;


    /**
     * 跳绳开始时间
     */
    private long time;

    public BleYundongMsg(int timeCount, int skipNum, int breakNum, long time) {
        this.timeCount = timeCount;
        this.skipNum = skipNum;
        this.breakNum = breakNum;
        this.time = time;
    }


    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
