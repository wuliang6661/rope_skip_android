package com.habit.star.pojo.po;

import java.util.List;

public class RongYuBO {


    private List<AcquireHonorListBean> acquireHonorList;
    private List<AcquireHonorListBean> noAcquireHonorList;

    public List<AcquireHonorListBean> getAcquireHonorList() {
        return acquireHonorList;
    }

    public void setAcquireHonorList(List<AcquireHonorListBean> acquireHonorList) {
        this.acquireHonorList = acquireHonorList;
    }

    public List<AcquireHonorListBean> getNoAcquireHonorList() {
        return noAcquireHonorList;
    }

    public void setNoAcquireHonorList(List<AcquireHonorListBean> noAcquireHonorList) {
        this.noAcquireHonorList = noAcquireHonorList;
    }

    public static class AcquireHonorListBean {
        /**
         * id : 1
         * name : 荣誉证书一
         * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004291059055590367.png
         * skipSTotalNum : 1
         * pkVTotalNum : 1
         * orderNum : 1
         */

        private int id;
        private String name;
        private String image;
        private int skipSTotalNum;
        private int pkVTotalNum;
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

        public int getSkipSTotalNum() {
            return skipSTotalNum;
        }

        public void setSkipSTotalNum(int skipSTotalNum) {
            this.skipSTotalNum = skipSTotalNum;
        }

        public int getPkVTotalNum() {
            return pkVTotalNum;
        }

        public void setPkVTotalNum(int pkVTotalNum) {
            this.pkVTotalNum = pkVTotalNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }
    }


}
