package com.habit.star.ui.train.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.ui.train.bean.TranRecordModel;

import java.util.List;


/**
 * @version V1.0
 * @date: 2020-02-11 11:12
 * @ClassName: TranHomeContract.java
 * @Description:
 * @author: sundongdong
 */

public interface TranHomeContract {
    interface View extends BaseView {

        /**
         * 设置记录数据
         *
         * @param data
         */
        void setRecordList(List<TranRecordModel> data);

        void getDeviceQcAndType(String dianliang, String type);


        void getDeviceCishu(String cichu);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取记录数据
         */
        void getRecodList();

        /**
         * 获取电量
         */
        void getDeviceQC();
    }

}