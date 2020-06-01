package com.tohabit.skip.ui.login.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.login.contract.RetrievePasswordContract;

import javax.inject.Inject;

/**
 * @version V1.0
 * @date: 2020-02-16 12:06
 * @ClassName: RetrievePasswordPresenter.java
 * @Description:
 * @author: sundongdong
 */
public class RetrievePasswordPresenter extends RxPresenter<RetrievePasswordContract.View> implements RetrievePasswordContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public RetrievePasswordPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    /**
     * 发送验证码
     */
    public void sendCode(String phone) {
        HttpServerImpl.sendCode(phone, 1).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.getYZMSuccess();
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


    /**
     * 验证手机号
     */
    public void verifyPhone(String phone, String msgCode) {
        HttpServerImpl.verifyPhone(phone, msgCode).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.verifyPhoneSuress();
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


    /**
     * 修改密码
     */
    public void forgetPassword(String phone, String password) {
        HttpServerImpl.forgetPassword(phone, password).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.forwordPasswordSuress();
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