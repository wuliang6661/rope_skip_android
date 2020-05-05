package com.habit.star.ui.train.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.ui.train.bean.RopeSkipResultModel;

/**
 * 创建日期：2018/7/2 16:49
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： CommonContract.java
 * 类说明：
 */
public interface RopeSkipResultContract {
    interface View extends BaseView {
        void getData(RopeSkipResultModel data);

    }

    interface Presenter extends BasePresenter<View> {

        void getData();
        void tryAgainRope();
    }

}