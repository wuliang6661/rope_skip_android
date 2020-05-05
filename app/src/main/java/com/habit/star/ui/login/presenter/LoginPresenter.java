package com.habit.star.ui.login.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
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
        mRetrofitHelper= retrofitHelper;
    }

//    @Override
//    public void login(String userName, String password) {
//        mView.showProgress();
//        addSubscrebe(mRetrofitHelper.login(userName,password).subscribe(new Action1<LoginBean>() {
//            @Override
//            public void call(LoginBean data) {
//                mView.hideProgress();
//                mView.loginSuccess(data);
//            }
//        }, new ActionError(App.getStringResource(R.string.login_failed)) {
//            @Override
//            public void onError(String msg, int code) {
//                mView.hideProgress();
//                mView.showError(msg);
//            }
//        }));
//    }
//
//    @Override
//    public void getUserInfo() {
//        if (App.getInstance().loginBean ==null){
//            return;
//        }
//        mView.showProgress();
//        addSubscrebe(mRetrofitHelper.getNowUserInfoOne().subscribe(new Action1<UserInfoMode>() {
//            @Override
//            public void call(UserInfoMode userInfoMode) {
//                mView.hideProgress();
//                mView.getUserInfo(userInfoMode);
//            }
//        }, new ActionError(App.getStringResource(R.string.getUserInfoError)) {
//            @Override
//            public void onError(String msg, int code) {
//                mView.hideProgress();
//                mView.showError(msg);
//            }
//        }));
//    }
}