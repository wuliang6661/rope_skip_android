package com.tohabit.skip.pojo.po;

import java.util.List;

public class TestDetailsBO {


    /**
     * id : 22
     * userId : 1
     * deviceId : 1
     * skipTime : 60
     * skipNum : 180
     * breakNum : 4
     * averageVelocity : 180.0
     * accelerateVelocity : -20.0
     * actionScore : 5
     * enduranceScore : 2
     * stableScore : 4
     * rhythmScore : 1
     * coordinateScore : 3
     * analysisList : [{"scoreRubric":0,"flag":4,"score":5,"questionAnalysis":"摇绳无法连贯，摇一圈之后出现明显停顿，需要重新起绳","questionCode":"00001","createDate":"2020-05-20T13:53:38","updateDate":"2020-05-25T17:00:34","remark":null,"id":1},{"scoreRubric":1,"flag":1,"score":1,"questionAnalysis":"两侧大臂、小臂向外张开，接近平举，而且使用大臂转动发力摇绳","questionCode":"00002","createDate":"2020-05-20T13:56:19","updateDate":"2020-05-20T14:01:20","remark":null,"id":2},{"scoreRubric":4,"flag":0,"score":2,"questionAnalysis":"两侧大臂、小臂向外张开，接近平举，而且使用大臂转动发力摇绳","questionCode":"00002","createDate":"2020-05-20T13:56:19","updateDate":"2020-05-20T13:56:19","remark":null,"id":3},{"scoreRubric":2,"flag":2,"score":3,"questionAnalysis":"摇绳无法连贯，摇一圈之后出现明显停顿，需要重新起绳","questionCode":"010102","createDate":"2020-05-21T20:07:01","updateDate":"2020-05-25T17:00:24","remark":null,"id":4},{"scoreRubric":3,"flag":3,"score":4,"questionAnalysis":"摇绳无法连贯，摇一圈之后出现明显停顿，需要重新起绳","questionCode":"010101","createDate":"2020-05-21T19:30:17","updateDate":"2020-05-25T17:00:30","remark":null,"id":5}]
     * planList : [{"videoTitle":"基本单摇跳-标准跳绳动作","trainLength":1,"status":1,"id":1},{"videoTitle":"动作矫正\u2014\u2014小臂、手腕矫正训练","trainLength":12,"status":1,"id":4},{"videoTitle":"徒步摇绳","trainLength":111,"status":1,"id":6},{"videoTitle":"徒步摇绳","trainLength":12,"status":2,"id":2},{"videoTitle":"动作矫正\u2014\u2014大臂姿态矫正训练（大臂外张）","trainLength":1,"status":2,"id":3}]
     */

    private int id;
    private int userId;
    private int deviceId;
    private int skipTime;
    private int skipNum;
    private int breakNum;
    private String averageVelocity;
    private String accelerateVelocity;
    private int actionScore;
    private int enduranceScore;
    private int stableScore;
    private int rhythmScore;
    private int coordinateScore;
    private List<AnalysisListBean> analysisList;
    private List<PlanListBean> planList;
    private int averageVelocityArrow;
    private int accelerateVelocityArrow;

    public int getAccelerateVelocityArrow() {
        return accelerateVelocityArrow;
    }

    public void setAccelerateVelocityArrow(int accelerateVelocityArrow) {
        this.accelerateVelocityArrow = accelerateVelocityArrow;
    }

    public int getAverageVelocityArrow() {
        return averageVelocityArrow;
    }

    public void setAverageVelocityArrow(int averageVelocityArrow) {
        this.averageVelocityArrow = averageVelocityArrow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getSkipTime() {
        return skipTime;
    }

    public void setSkipTime(int skipTime) {
        this.skipTime = skipTime;
    }

    public int getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(int skipNum) {
        this.skipNum = skipNum;
    }

    public int getBreakNum() {
        return breakNum;
    }

    public void setBreakNum(int breakNum) {
        this.breakNum = breakNum;
    }

    public String getAverageVelocity() {
        return averageVelocity;
    }

    public void setAverageVelocity(String averageVelocity) {
        this.averageVelocity = averageVelocity;
    }

    public String getAccelerateVelocity() {
        return accelerateVelocity;
    }

    public void setAccelerateVelocity(String accelerateVelocity) {
        this.accelerateVelocity = accelerateVelocity;
    }

    public int getActionScore() {
        return actionScore;
    }

    public void setActionScore(int actionScore) {
        this.actionScore = actionScore;
    }

    public int getEnduranceScore() {
        return enduranceScore;
    }

    public void setEnduranceScore(int enduranceScore) {
        this.enduranceScore = enduranceScore;
    }

    public int getStableScore() {
        return stableScore;
    }

    public void setStableScore(int stableScore) {
        this.stableScore = stableScore;
    }

    public int getRhythmScore() {
        return rhythmScore;
    }

    public void setRhythmScore(int rhythmScore) {
        this.rhythmScore = rhythmScore;
    }

    public int getCoordinateScore() {
        return coordinateScore;
    }

    public void setCoordinateScore(int coordinateScore) {
        this.coordinateScore = coordinateScore;
    }

    public List<AnalysisListBean> getAnalysisList() {
        return analysisList;
    }

    public void setAnalysisList(List<AnalysisListBean> analysisList) {
        this.analysisList = analysisList;
    }

    public List<PlanListBean> getPlanList() {
        return planList;
    }

    public void setPlanList(List<PlanListBean> planList) {
        this.planList = planList;
    }

    public static class AnalysisListBean {
        /**
         * scoreRubric : 0
         * flag : 4
         * score : 5
         * questionAnalysis : 摇绳无法连贯，摇一圈之后出现明显停顿，需要重新起绳
         * questionCode : 00001
         * createDate : 2020-05-20T13:53:38
         * updateDate : 2020-05-25T17:00:34
         * remark : null
         * id : 1
         */

        private int scoreRubric;
        private int flag;
        private int score;
        private String questionAnalysis;
        private String questionCode;
        private String createDate;
        private String updateDate;
        private Object remark;
        private int id;

        public int getScoreRubric() {
            return scoreRubric;
        }

        public void setScoreRubric(int scoreRubric) {
            this.scoreRubric = scoreRubric;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getQuestionAnalysis() {
            return questionAnalysis;
        }

        public void setQuestionAnalysis(String questionAnalysis) {
            this.questionAnalysis = questionAnalysis;
        }

        public String getQuestionCode() {
            return questionCode;
        }

        public void setQuestionCode(String questionCode) {
            this.questionCode = questionCode;
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

    public static class PlanListBean {
        /**
         * videoTitle : 基本单摇跳-标准跳绳动作
         * trainLength : 1
         * status : 1
         * id : 1
         */

        private String videoTitle;
        private int trainLength;
        private int status;
        private int id;

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public int getTrainLength() {
            return trainLength;
        }

        public void setTrainLength(int trainLength) {
            this.trainLength = trainLength;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
