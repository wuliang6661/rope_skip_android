package com.tohabit.skip.ui.train.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.TestBO;
import com.tohabit.skip.pojo.po.TestDataBO;

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
        void setRecordList(List<TestBO> data);

        void getDeviceQcAndType(String dianliang, String type);

        void getDeviceCishu(String cichu);

        /**
         * 获取测试总数据
         */
        void getTestData(TestDataBO dataBO);

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