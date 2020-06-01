package com.tohabit.skip.ui.train.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.ui.train.bean.ImprovePlanModel;

import java.util.List;


/**
 * @date:  2020-02-11 23:34
 * @ClassName: TestResultContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface TrainPlanListContract {
    interface View extends BaseView {

        /**
         * 设置数据
         * @param data
         */
        void setList(List<ImprovePlanModel> data);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取数据
         */
        void getList(String type);
    }

}