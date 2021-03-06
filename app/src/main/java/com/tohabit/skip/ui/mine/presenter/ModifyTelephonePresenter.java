package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.login.contract.ModifyTelephoneContract;

import javax.inject.Inject;

/**
 * @version V1.0
 * @date: 2020-04-25 21:53
 * @ClassName: ModifyTelephonePresenter.java
 * @Description:
 * @author: sundongdong
 */
public class ModifyTelephonePresenter extends RxPresenter<ModifyTelephoneContract.View> implements ModifyTelephoneContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public ModifyTelephonePresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


    /**
     * 发送验证码
     */
    public void sendCode(String phone, int type) {
        HttpServerImpl.sendCode(phone, type).subscribe(new HttpResultSubscriber<String>() {
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
     * 验证身份
     */
    public void verifyUserInfo(String phone, String code) {
        HttpServerImpl.verifyUserInfo(phone, code).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.YzSouress();
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
     * 修改手机号
     */
    public void updatePhone(String phone, String code) {
        HttpServerImpl.updatePhone(phone, code).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateSourcess();
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