package com.tohabit.skip.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1411:07
 * desc   :
 * version: 1.0
 */
public class MessageBO {


    /**
     * id : 244
     * title : 能量值变动通知
     * content : #每日登录# 奖励10枚能量晶体,请及时领取,超时未领取则自动消失
     * status : 0
     * createDate : 52分钟前
     */

    private int id;
    private String title;
    private String content;
    private int status;
    private String createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
