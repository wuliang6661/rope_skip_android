package com.habit.star.pojo.po;

import java.io.Serializable;

public class AddressBO implements Serializable {


    /**
     * userId : 1
     * phone : 15856381576
     * name : 孙中赛
     * area : 浙江省杭州市西湖区
     * address : 西港新界8幢8E单元8楼杭州百倍云科技有限公司
     * isDefault : 1
     * delStatus : 0
     * createDate : 2020-04-09T22:28:50
     * updateDate : 2020-05-07T23:51:42
     * remark : null
     * id : 3
     */

    private int userId;
    private String phone;
    private String name;
    private String area;
    private String address;
    private int isDefault;
    private int delStatus;
    private String createDate;
    private String updateDate;
    private Object remark;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
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

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
