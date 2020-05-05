package com.habit.star.ui.mine.contract;

import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;

/**
 * 创建日期：2018/6/1 9:20
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： MineContract.java
 * 类说明：
 */
public interface MineContract {
    interface View extends BaseView {
//        void getUserInfo(UserInfoMode userInfoMode);
    }

    interface Presenter extends BasePresenter<View> {

//        void getUserInfo();
    }

}