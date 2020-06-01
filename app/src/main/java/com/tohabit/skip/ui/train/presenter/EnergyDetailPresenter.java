package com.tohabit.skip.ui.train.presenter;

import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.train.bean.EnergyRewardModel;
import com.tohabit.skip.ui.train.contract.EnergyDetailContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * @version V1.0
 * @date: 2020-02-11 23:36
 * @ClassName: TestResultPresenter.java
 * @Description:
 * @author: sundongdong
 */
public class EnergyDetailPresenter extends RxPresenter<EnergyDetailContract.View> implements EnergyDetailContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public EnergyDetailPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getList() {
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


        ///添加测试数据
        List<EnergyRewardModel> testData = new ArrayList<>();
        EnergyRewardModel model1 = new EnergyRewardModel();
        model1.title = "登录奖励";
        model1.value = "+10";
        model1.time = "2019-09-11 12:00";
        testData.add(model1);
        mView.setList(testData);
        EnergyRewardModel model2 = new EnergyRewardModel();
        model2.title = "任务奖励";
        model2.value = "+10";
        model2.time = "2019-09-11 12:00";
        testData.add(model2);
        mView.setList(testData);
        EnergyRewardModel model3 = new EnergyRewardModel();
        model3.title = "能量兑换";
        model3.value = "+10";
        model3.time = "2019-09-11 12:00";
        testData.add(model3);
        mView.setList(testData);
        EnergyRewardModel model4 = new EnergyRewardModel();
        model4.title = "任务奖励";
        model4.value = "+10";
        model4.time = "2019-09-11 12:00";
        testData.add(model4);
        mView.setList(testData);
        EnergyRewardModel model5 = new EnergyRewardModel();
        model5.title = "任务奖励";
        model5.value = "+10";
        model5.time = "2019-09-11 12:00";
        testData.add(model5);
        mView.setList(testData);
    }
}