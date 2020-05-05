package com.habit.star.ui.train.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.train.contract.BaseMsgInputContract;
import com.habit.star.ui.train.contract.TrainPlanContract;

import javax.inject.Inject;


/**
 * @date:  2020-02-11 12:27
 * @ClassName: TrainPlanPresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class TrainPlanPresenter extends RxPresenter<TrainPlanContract.View> implements TrainPlanContract.Presenter {

    RetrofitHelper mRetrofitHelper;
    @Inject
    public TrainPlanPresenter(RetrofitHelper retrofitHelper) {
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