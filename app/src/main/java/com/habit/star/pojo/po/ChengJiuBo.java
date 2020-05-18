package com.habit.star.pojo.po;

import java.util.List;

public class ChengJiuBo {


    private List<AcquireMedalListBean> acquireMedalList;
    private List<AcquireMedalListBean> noAcquireMedalList;

    public List<AcquireMedalListBean> getAcquireMedalList() {
        return acquireMedalList;
    }

    public void setAcquireMedalList(List<AcquireMedalListBean> acquireMedalList) {
        this.acquireMedalList = acquireMedalList;
    }

    public List<AcquireMedalListBean> getNoAcquireMedalList() {
        return noAcquireMedalList;
    }

    public void setNoAcquireMedalList(List<AcquireMedalListBean> noAcquireMedalList) {
        this.noAcquireMedalList = noAcquireMedalList;
    }

    public static class AcquireMedalListBean {
        /**
         * id : 1
         * name : 银一星
         * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004291050320632728.png
         * skipTotalNum : 1000
         * completeTotalNum : 1
         * orderNum : 1
         */

        private int id;
        private String name;
        private String image;
        private int skipTotalNum;
        private int completeTotalNum;
        private int orderNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getSkipTotalNum() {
            return skipTotalNum;
        }

        public void setSkipTotalNum(int skipTotalNum) {
            this.skipTotalNum = skipTotalNum;
        }

        public int getCompleteTotalNum() {
            return completeTotalNum;
        }

        public void setCompleteTotalNum(int completeTotalNum) {
            this.completeTotalNum = completeTotalNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }
    }

}
