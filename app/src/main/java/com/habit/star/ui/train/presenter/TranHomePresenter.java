package com.habit.star.ui.train.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.train.bean.TranRecordModel;
import com.habit.star.ui.train.contract.TranHomeContract;
import com.habit.star.utils.blue.BlueUtils;
import com.habit.star.utils.blue.cmd.BleCmd;
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
        mView.setRecordList(testData);
    }


    /**
     * 获取电量
     */
    public void getDeviceQC() {
        BlueUtils blueUtils = BlueUtils.getInstance();
        blueUtils.createNotifion(new BlueUtils.onBlueNotifiListener() {
            @Override
            public void onNotifiBlue(byte[] value) {
                BleCmd.Builder builder = new BleCmd.Builder().setBuilder(value);
                if (mView != null) {
                    mView.getDeviceQcAndType(String.valueOf(builder.getDataBody()[0]), String.valueOf(builder.getDataBody()[1]));
                }
            }

            @Override
            public void notifiConnectSourcess() {   //监听创建成功之后发送
                blueUtils.writeData(RequstBleCmd.createGetEQCmd().getCmdByte());
            }

            @Override
            public void notifiConnectError() {

            }
        });

    }


    /**
     * 获取跳绳次数
     */
    public void getTiaoshenCishu() {
        BlueUtils blueUtils = BlueUtils.getInstance();
        blueUtils.createNotifion(new BlueUtils.onBlueNotifiListener() {
            @Override
            public void onNotifiBlue(byte[] value) {
                BleCmd.Builder builder = new BleCmd.Builder().setBuilder(value);
                if (mView != null) {
                    mView.getDeviceCishu(
                            String.valueOf(Math.abs(builder.getDataBody()[builder.getDataBody().length - 1])));
                }
            }

            @Override
            public void notifiConnectSourcess() {   //监听创建成功之后发送
                blueUtils.writeData(RequstBleCmd.createTodayFrequencyCmd().getCmdByte());
            }

            @Override
            public void notifiConnectError() {

            }
        });
    }

}