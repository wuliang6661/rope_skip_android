package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1413:52
 * desc   :
 * version: 1.0
 */
public class FeedBackBO {


    /**
     * name : 建议
     * createDate : 2020-04-09T11:56:00
     * updateDate : 2020-04-09T11:56:23
     * remark : null
     * id : 1
     */

    private String name;
    private String createDate;
    private String updateDate;
    private Object remark;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
