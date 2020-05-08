package com.habit.star.pojo.po;

import java.io.Serializable;

public class DeviceBO implements Serializable {


    /**
     * id : 10
     * code : 7907
     * name : 设备5号
     * macAddress : 123456
     * linkStatus : 1
     * workStatus : 0
     * electricQuantity : 0
     */

    private int id;
    private String code;
    private String name;
    private String macAddress;
    private int linkStatus;
    private int workStatus;
    private int electricQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(int linkStatus) {
        this.linkStatus = linkStatus;
    }

    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }

    public int getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(int electricQuantity) {
        this.electricQuantity = electricQuantity;
    }
}
