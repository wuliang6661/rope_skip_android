package com.habit.star.ui.mine.presenter;


import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.login.contract.ModifyTelephoneContract;
import com.habit.star.ui.login.contract.RetrievePasswordContract;

import javax.inject.Inject;

/**
 * @date:  2020-04-25 21:53
 * @ClassName: ModifyTelephonePresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
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
}