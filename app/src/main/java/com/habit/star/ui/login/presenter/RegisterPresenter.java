package com.habit.star.ui.login.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.login.contract.RegisterContract;

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
        mRetrofitHelper= retrofitHelper;
    }

//    @Override
//    public void register(String userName, String password, String yzm) {
//        addSubscrebe(mRetrofitHelper.register(userName,password,yzm).subscribe(new Action1<String>() {
//            @Override
//            public void call(String data) {
//                mView.registerSuccess("");
//            }
//        }, new ActionError(App.getStringResource(R.string.register_failed)) {
//            @Override
//            public void onError(String msg, int code) {
//                mView.showError(msg);
//            }
//        }));
//    }
//
//    @Override
//    public void getYZM(String phone) {
//        addSubscrebe(mRetrofitHelper.getYZM(phone).subscribe(new Action1<String>() {
//            @Override
//            public void call(String data) {
//                mView.getYZMSuccess("操作成功");
//            }
//        }, new ActionError(App.getStringResource(R.string.register_failed)) {
//            @Override
//            public void onError(String msg, int code) {
//                mView.showError(msg);
//            }
//        }));
//    }
}