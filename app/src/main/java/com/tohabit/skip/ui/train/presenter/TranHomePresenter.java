package com.tohabit.skip.ui.train.presenter;

import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.TestBO;
import com.tohabit.skip.pojo.po.TestDataBO;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.train.contract.TranHomeContract;
import com.tohabit.skip.utils.SyncHistoryUtils;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;

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

    }


    /**
     * 获取电量
     */
    public void getDeviceQC() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            if(SyncHistoryUtils.isSync){
                return;
            }
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