package com.tohabit.skip.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2817:25
 * desc   :
 * version: 1.0
 */
public class BeatsBO {


    /**
     * beat : 30
     * url : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/common/202005232046139148515.MP4
     * createDate : 2020-05-23T20:46:19
     * updateDate : 2020-05-23T21:06:23
     * remark : null
     * id : 1
     */

    private int beat;
    private String url;
    private String createDate;
    private String updateDate;
    private String remark;
    private int id;

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
