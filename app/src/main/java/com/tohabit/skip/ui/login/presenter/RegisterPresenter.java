package com.tohabit.skip.ui.login.presenter;

import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.login.contract.RegisterContract;

import javax.inject.Inject;

/**
 * 创建日期：2018/6/14 9:28
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： LoginPresenter.java
 * 类说明：
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public RegisterPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    /**
     * 注册账号
     */
    public void register(String userName, String password, String yzm, int isBuy) {
        HttpServerImpl.regiest(userName, password, yzm, isBuy + "").subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if(mView != null){
                    mView.registerSuccess();
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
     * 发送验证码
     */
    public void sendCode(String phone) {
        HttpServerImpl.sendCode(phone, 0).subscribe(new HttpResultSubscriber<String>() {
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

}