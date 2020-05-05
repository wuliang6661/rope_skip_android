package com.habit.star.ui.train.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.train.bean.ImprovePlanModel;
import com.habit.star.ui.train.contract.TrainPlanListContract;

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
public class TrainPlanListPresenter extends RxPresenter<TrainPlanListContract.View> implements TrainPlanListContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public TrainPlanListPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getList(String type) {
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

        if (type=="1"){
            ///添加测试数据
            List<ImprovePlanModel> testData = new ArrayList<>();
            ImprovePlanModel model1 = new ImprovePlanModel();
            model1.state = 0;
            model1.trainIntensity = "X  2/7";
            model1.stateName = "去完成";
            model1.content = "跳绳高度训练";
            model1.trainTime = "5";
            model1.trainTimeUnit = "分钟";
            model1.energy = "+10能量";
            model1.planTime = "7";
            model1.planTimeUnit = "天";
            testData.add(model1);
            ImprovePlanModel model2 = new ImprovePlanModel();
            model2.state = 0;
            model2.trainIntensity = "X  2/7";
            model2.stateName = "去完成";
            model2.content = "跳绳高度训练";
            model2.trainTime = "5";
            model2.trainTimeUnit = "分钟";
            model2.energy = "+10能量";
            model2.planTime = "7";
            model2.planTimeUnit = "天";
            testData.add(model2);
            ImprovePlanModel model3 = new ImprovePlanModel();
            model3.state = 0;
            model3.trainIntensity = "X  2/7";
            model3.stateName = "去完成";
            model3.content = "跳绳高度训练";
            model3.trainTime = "5";
            model3.trainTimeUnit = "分钟";
            model3.energy = "+10能量";
            model3.planTime = "7";
            model3.planTimeUnit = "天";
            testData.add(model3);
            ImprovePlanModel model4 = new ImprovePlanModel();
            model4.state = 0;
            model4.trainIntensity = "X  2/7";
            model4.stateName = "去完成";
            model4.content = "跳绳高度训练";
            model4.trainTime = "5";
            model4.trainTimeUnit = "分钟";
            model4.energy = "+10能量";
            model4.planTime = "7";
            model4.planTimeUnit = "天";
            testData.add(model4);

            mView.setList(testData);

        }else {
            ///添加测试数据
            List<ImprovePlanModel> testData = new ArrayList<>();
            ImprovePlanModel model1 = new ImprovePlanModel();
            model1.state = 3;
            model1.trainIntensity = "X  2/7";
            model1.stateName = "已完成";
            model1.content = "跳绳高度训练";
            model1.trainTime = "5";
            model1.trainTimeUnit = "分钟";
            model1.energy = "+10能量";
            model1.planTime = "7";
            model1.planTimeUnit = "天";
            testData.add(model1);
            ImprovePlanModel model2 = new ImprovePlanModel();
            model2.state = 3;
            model2.trainIntensity = "X  2/7";
            model2.stateName = "已完成";
            model2.content = "跳绳高度训练";
            model2.trainTime = "5";
            model2.trainTimeUnit = "分钟";
            model2.energy = "+10能量";
            model2.planTime = "7";
            model2.planTimeUnit = "天";
            testData.add(model2);
            ImprovePlanModel model3 = new ImprovePlanModel();
            model3.state = 3;
            model3.trainIntensity = "X  2/7";
            model3.stateName = "已完成";
            model3.content = "跳绳高度训练";
            model3.trainTime = "5";
            model3.trainTimeUnit = "分钟";
            model3.energy = "+10能量";
            model3.planTime = "7";
            model3.planTimeUnit = "天";
            testData.add(model3);

            mView.setList(testData);

        }
    }
}