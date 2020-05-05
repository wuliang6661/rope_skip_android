package com.habit.star.ui.train.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.train.contract.RopeSkipSettingContract;

import javax.inject.Inject;


/**
 * @version V1.0
 * @date: 2020-02-12 15:43
 * @ClassName: RoseSkipSettingPresenter.java
 * @Description:
 * @author: sundongdong
 */
public class RoseSkipSettingPresenter extends RxPresenter<RopeSkipSettingContract.View> implements RopeSkipSettingContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public RoseSkipSettingPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

//    @Override
//    public void getRecodList() {
//        mView.showProgress();
//        addSubscrebe(mRetrofitHelper.getRecodList().subscribe(new Action1<UserInfoMode>() {
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