package com.tohabit.skip.pojo.po;

import java.util.List;

public class BaoGaoDetailsBO {


    /**
     * newPkValue : 0
     * completeTaskNum : 11
     * skipNum : 0
     * pkChallengeNum : 2
     * taskList : [{"taskType":1,"completeNum":1},{"taskType":2,"completeNum":0},{"taskType":3,"completeNum":0},{"taskType":4,"completeNum":0},{"taskType":5,"completeNum":0}]
     * skipList : [{"skipDate":"5/16","skipNum":0},{"skipDate":"5/17","skipNum":0},{"skipDate":"5/18","skipNum":0},{"skipDate":"5/19","skipNum":0},{"skipDate":"5/20","skipNum":0},{"skipDate":"5/21","skipNum":0},{"skipDate":"5/22","skipNum":0}]
     */

    private int newPkValue;
    private int completeTaskNum;
    private int skipNum;
    private int pkChallengeNum;
    private List<TaskListBean> taskList;
    private List<SkipListBean> skipList;

    public int getNewPkValue() {
        return newPkValue;
    }

    public void setNewPkValue(int newPkValue) {
        this.newPkValue = newPkValue;
    }

    public int getCompleteTaskNum() {
        return completeTaskNum;
    }

    public void setCompleteTaskNum(int completeTaskNum) {
        this.completeTaskNum = completeTaskNum;
    }

    public int getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(int skipNum) {
        this.skipNum = skipNum;
    }

    public int getPkChallengeNum() {
        return pkChallengeNum;
    }

    public void setPkChallengeNum(int pkChallengeNum) {
        this.pkChallengeNum = pkChallengeNum;
    }

    public List<TaskListBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskListBean> taskList) {
        this.taskList = taskList;
    }

    public List<SkipListBean> getSkipList() {
        return skipList;
    }

    public void setSkipList(List<SkipListBean> skipList) {
        this.skipList = skipList;
    }

    public static class TaskListBean {
        /**
         * taskType : 1
         * completeNum : 1
         */

        private int taskType;
        private int completeNum;
        private String taskName;

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getTaskType() {
            return taskType;
        }

        public void setTaskType(int taskType) {
            this.taskType = taskType;
        }

        public int getCompleteNum() {
            return completeNum;
        }

        public void setCompleteNum(int completeNum) {
            this.completeNum = completeNum;
        }
    }

    public static class SkipListBean {
        /**
         * skipDate : 5/16
         * skipNum : 0
         */

        private String skipDate;
        private int skipNum;

        public String getSkipDate() {
            return skipDate;
        }

        public void setSkipDate(String skipDate) {
            this.skipDate = skipDate;
        }

        public int getSkipNum() {
            return skipNum;
        }

        public void setSkipNum(int skipNum) {
            this.skipNum = skipNum;
        }
    }
}
