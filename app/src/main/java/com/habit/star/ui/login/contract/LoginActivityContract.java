package com.habit.star.ui.login.contract;

import android.content.Context;

import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;

/**
 * @date:  2020-02-16 12:12
 * @ClassName: LoginActivityContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface LoginActivityContract {
    interface View extends BaseView {
        /**
         * 初始化组件失败
         */
        void setInitComponentFailed();
        /**
         * 初始化组件成功
         */
        void setInitComponentSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 初始化组件
         * @param mContext
         */
        void initComponent(Context mContext);
    }

}