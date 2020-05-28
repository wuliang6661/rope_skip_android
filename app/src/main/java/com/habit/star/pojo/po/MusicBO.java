package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2817:25
 * desc   :
 * version: 1.0
 */
public class MusicBO {


    /**
     * name : fafas
     * url : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/common/202005211624033631393.mp3
     * orderNum : 0
     * delStatus : 0
     * createDate : 2020-05-21T16:24:06
     * updateDate : 2020-05-21T16:24:06
     * remark : null
     * id : 13
     */

    private String name;
    private String url;
    private int orderNum;
    private int delStatus;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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
