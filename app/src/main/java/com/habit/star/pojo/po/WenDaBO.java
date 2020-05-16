package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1413:35
 * desc   :
 * version: 1.0
 */
public class WenDaBO {


    /**
     * question : 这款智能跳绳的质量怎么样？
     * answer : 本公司产品都是经国家相关机构检测过的，可以放心购买。
     * createDate : 2020-04-18 09:46:14
     * updateDate : 2020-04-18 09:47:27
     * remark : null
     * id : 3
     */

    private String question;
    private String answer;
    private String createDate;
    private String updateDate;
    private Object remark;
    private int id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
