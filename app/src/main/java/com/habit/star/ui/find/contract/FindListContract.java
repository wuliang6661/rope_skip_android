package com.habit.star.ui.find.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.ui.find.bean.FindModel;
import com.habit.star.ui.train.bean.ImprovePlanModel;

import java.util.List;


/**
 * @date:  2020-02-26 23:17
 * @ClassName: FindListContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface FindListContract {
    interface View extends BaseView {

        /**
         * 设置数据
         * @param data
         */
        void setList(List<FindModel> data);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取数据
         */
        void getList(String type);
    }

}