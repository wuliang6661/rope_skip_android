package com.tohabit.skip.ui.login.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.UserBO;

/**
 * 创建日期：2018/6/14 9:28
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称： LoginContract.java
 * 类说明：
 */
public interface LoginContract {
    interface View extends BaseView {

        void loginSuccess(UserBO loginBean);
//
//
//        void getUserInfo(UserInfoMode userInfoMode);

    }

    interface Presenter extends BasePresenter<View> {
//        /**
//         * 登录
//         * @param userName
//         * @param password
//         */
//        void login(String userName, String password);
//
//        /**
//         * 获取用户信息
//         */
//        void getUserInfo();
    }

}