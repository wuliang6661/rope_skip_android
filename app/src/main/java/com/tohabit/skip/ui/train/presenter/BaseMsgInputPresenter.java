package com.tohabit.skip.ui.train.presenter;

import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.train.contract.BaseMsgInputContract;

import javax.inject.Inject;


/**
 * @date:  2020-02-11 11:18
 * @ClassName: BaseMsgInputPresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class BaseMsgInputPresenter extends RxPresenter<BaseMsgInputContract.View> implements BaseMsgInputContract.Presenter {

    RetrofitHelper mRetrofitHelper;
    @Inject
    public BaseMsgInputPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper= retrofitHelper;
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