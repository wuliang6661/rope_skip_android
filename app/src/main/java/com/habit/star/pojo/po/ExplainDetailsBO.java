package com.habit.star.pojo.po;

import java.io.Serializable;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/289:20
 * desc   :
 * version: 1.0
 */
public class ExplainDetailsBO implements Serializable {


    /**
     * questionAnalysisId : 4
     * videoTitle : 动作矫正——大臂姿态矫正训练（大臂外张）
     * videoUrl : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/common/202005221700168659659.mp4
     * videoCode : 555
     * trainLength : 1
     * trainContent : <p>规范的股份后台后台</p><p><br></p>
     * delStatus : 0
     * createDate : 2020-05-22T17:00:42
     * updateDate : 2020-05-27T19:41:26
     * remark : null
     * id : 3
     */

    private int questionAnalysisId;
    private String videoTitle;
    private String videoUrl;
    private String videoCode;
    private int trainLength;
    private String trainContent;
    private int delStatus;
    private String createDate;
    private String updateDate;
    private String remark;
    private int id;

    public int getQuestionAnalysisId() {
        return questionAnalysisId;
    }

    public void setQuestionAnalysisId(int questionAnalysisId) {
        this.questionAnalysisId = questionAnalysisId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public int getTrainLength() {
        return trainLength;
    }

    public void setTrainLength(int trainLength) {
        this.trainLength = trainLength;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
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
