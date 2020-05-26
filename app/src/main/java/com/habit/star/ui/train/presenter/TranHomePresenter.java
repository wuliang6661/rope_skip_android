package com.habit.star.ui.train.presenter;

import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.TestBO;
import com.habit.star.pojo.po.TestDataBO;
import com.habit.star.ui.train.bean.TranRecordModel;
import com.habit.star.ui.train.contract.TranHomeContract;
import com.habit.star.service.UartService;
import com.habit.star.utils.blue.cmd.RequstBleCmd;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * @version V1.0
 * @date: 2020-02-11 11:18
 * @ClassName: TranHomePresenter.java
 * @Description:训练首页Presenter
 * @author: sundongdong
 */
public class TranHomePresenter extends RxPresenter<TranHomeContract.View> implements TranHomeContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public TranHomePresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRecodList() {
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
        List<TranRecordModel> testData = new ArrayList<>();
        TranRecordModel model1 = new TranRecordModel();
        model1.time = "2019-06-10";
        model1.jb = "A+";
        model1.payTime = "30";
        testData.add(model1);
        TranRecordModel model2 = new TranRecordModel();
        model2.time = "2019-06-1";
        model2.jb = "B+";
        model2.payTime = "33";
        testData.add(model2);
        TranRecordModel model3 = new TranRecordModel();
        model3.time = "2019-04-10";
        model3.jb = "A+";
        model3.payTime = "30";
        testData.add(model3);
//        mView.setRecordList(testData);
    }


    /**
     * 获取电量
     */
    public void getDeviceQC() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x11;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetEQCmd().getCmdByte());
        }
    }


    /**
     * 获取跳绳次数
     */
    public void getTiaoshenCishu() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x22;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createTodayFrequencyCmd().getCmdByte());
        }
    }


    /**
     * 获取测试总数据
     */
    public void getTestTotal() {
        HttpServerImpl.getTestTotal().subscribe(new HttpResultSubscriber<TestDataBO>() {
            @Override
            public void onSuccess(TestDataBO s) {
                if (mView != null) {
                    mView.getTestData(s);
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


    /**
     * 获取测试记录
     */
    public void getTestList() {
        HttpServerImpl.getTestList().subscribe(new HttpResultSubscriber<List<TestBO>>() {
            @Override
            public void onSuccess(List<TestBO> s) {
                if (mView != null) {
                    mView.setRecordList(s);
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