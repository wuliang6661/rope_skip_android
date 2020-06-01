package com.tohabit.skip.ui.login.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;

/**
 * 创建日期：2018/6/14 9:28
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： LoginContract.java
 * 类说明：
 */
public interface RegisterContract {
    interface View extends BaseView {

        /**
         * 注册成功
         *
         * @param token
         */
        void registerSuccess();

        /**
         * 获取验证码成功
         *
         * @param str
         */
        void getYZMSuccess();

    }

    interface Presenter extends BasePresenter<View> {
//        /**
//         * 注册用户
//         * @param phone
//         * @param password
//         * @param yzm
//         */
//        void register(String phone, String password,String yzm);
//
//        /**
//         * 获取验证码
//         * @param phone
//         */
//        void getYZM(String phone);
    }

}