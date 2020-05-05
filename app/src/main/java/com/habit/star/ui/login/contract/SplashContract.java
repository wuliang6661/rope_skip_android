package com.habit.star.ui.login.contract;

import android.content.Context;

import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.ui.mine.bean.UserInfoMode;

public interface SplashContract {
    interface View extends BaseView {

        void getUserInfo(UserInfoMode userInfoMode);
        void getUserInfoError();
        /**
         * 初始化组件成功
         */
        void setInitComponentSuccess();
        /**
         * 初始化组件失败
         */
        void setInitComponentFailed();
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取用户信息
         */
        void getUserInfo();

        /**
         * 初始化组件
         * @param mContext
         */
        void initComponent(Context mContext);
    }

}