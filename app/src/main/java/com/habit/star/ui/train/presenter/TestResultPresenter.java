package com.habit.star.ui.train.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.train.bean.ImprovePlanModel;
import com.habit.star.ui.train.bean.TranRecordModel;
import com.habit.star.ui.train.contract.TestResultContract;
import com.habit.star.ui.train.contract.TranHomeContract;

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
public class TestResultPresenter extends RxPresenter<TestResultContract.View> implements TestResultContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public TestResultPresenter(RetrofitHelper retrofitHelper) {
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
        List<ImprovePlanModel> testData = new ArrayList<>();
        ImprovePlanModel model1 = new ImprovePlanModel();
        model1.state = 1;
        model1.stateName = "添加计划";
        model1.content = "跳绳高度训练";
        model1.trainTime = "5";
        model1.trainTimeUnit = "分钟";
        model1.energy = "+10能量";
        model1.planTime = "7";
        model1.planTimeUnit = "天";
        testData.add(model1);
        ImprovePlanModel model2 = new ImprovePlanModel();
        model2.state = 2;
        model2.stateName = "进行中";
        model2.content = "跳绳高度训练";
        model2.trainTime = "5";
        model2.trainTimeUnit = "分钟";
        model2.energy = "+10能量";
        model2.planTime = "7";
        model2.planTimeUnit = "天";
        testData.add(model2);
        ImprovePlanModel model3 = new ImprovePlanModel();
        model3.state = 3;
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