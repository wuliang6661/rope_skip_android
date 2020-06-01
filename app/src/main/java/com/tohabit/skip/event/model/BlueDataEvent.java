package com.tohabit.skip.event.model;

public class BlueDataEvent {


    /**
     * 0是电量
     * <p>
     * 1 是跳绳次数
     */
    private int state;


    private byte[] data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
