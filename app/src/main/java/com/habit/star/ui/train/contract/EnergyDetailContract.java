package com.habit.star.ui.train.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.ui.train.bean.EnergyRewardModel;
import com.habit.star.ui.train.bean.ImprovePlanModel;

import java.util.List;


/**
 * @date:  2020-02-11 23:34
 * @ClassName: TestResultContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface EnergyDetailContract {
    interface View extends BaseView {

        /**
         * 设置数据
         * @param data
         */
        void setList(List<EnergyRewardModel> data);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取数据
         */
        void getList();
    }

}