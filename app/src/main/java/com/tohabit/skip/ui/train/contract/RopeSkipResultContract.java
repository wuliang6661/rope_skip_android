package com.tohabit.skip.ui.train.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.TrainBO;

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

        void getData(TrainBO data);

    }

    interface Presenter extends BasePresenter<View> {

        void getData();
        void tryAgainRope();
    }

}