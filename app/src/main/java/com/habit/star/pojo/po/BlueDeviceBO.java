package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1510:22
 * desc   : 搜索到的蓝牙设备
 * version: 1.0
 */
public class BlueDeviceBO {

    private String deviceName;


    private String deviceMac;

    /**
     * 是否已连接
     */
    private boolean isConnect;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }
}
