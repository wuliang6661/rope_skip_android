package com.tohabit.skip.ui.train.presenter;

import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.TrainBO;
import com.tohabit.skip.ui.train.bean.RopeSkipResultModel;
import com.tohabit.skip.ui.train.contract.RopeSkipResultContract;

import javax.inject.Inject;


/**
 * @version V1.0
 * @date: 2020-02-12 15:43
 * @ClassName: RoseSkipSettingPresenter.java
 * @Description:
 * @author: sundongdong
 */
public class RopeSkipResultPresenter extends RxPresenter<RopeSkipResultContract.View> implements RopeSkipResultContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public RopeSkipResultPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getData() {

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

        RopeSkipResultModel model = new RopeSkipResultModel();
        model.time="00'35''";
        model.ropeNumber="126";
        model.breakNumber="06";
        model.averageVelocity="111";
        model.acceleration="88";
        model.ropeHeight="110";
//        mView.getData(model);
    }
    @Override
    public void tryAgainRope() {

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

        RopeSkipResultModel model = new RopeSkipResultModel();
        model.time="00'00''";
        model.ropeNumber="0";
        model.breakNumber="0";
        model.averageVelocity="0";
        model.acceleration="0";
        model.ropeHeight="0";
//        mView.getData(model);
    }


    /**
     * 获取训练结果
     */
    public void getTrain(String id){
        HttpServerImpl.getTrain(id).subscribe(new HttpResultSubscriber<TrainBO>() {
            @Override
            public void onSuccess(TrainBO s) {
                if(mView != null){
                    mView.getData(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if(mView != null){
                    mView.showError(message);
                }
            }
        });
    }

}