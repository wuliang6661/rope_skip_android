package com.habit.star.ui.login.presenter;

import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.ui.login.contract.LoginContract;

import javax.inject.Inject;

/**
 * 创建日期：2018/6/14 9:28
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： LoginPresenter.java
 * 类说明：
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public LoginPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


    public void login(String phone, String password) {
        HttpServerImpl.login(phone, password).subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO s) {
                if (mView != null) {
                    mView.loginSuccess(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.showError(message);
                }
            }
        });
    }


}