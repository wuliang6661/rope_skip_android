package com.tohabit.skip.ui.login.presenter;

import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.login.contract.LoginContract;

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