package com.tohabit.skip.ui.mine.presenter;

import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.DeviceBO;
import com.tohabit.skip.pojo.po.DeviceLinkBO;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.mine.contract.MineContract;

import javax.inject.Inject;

/**
 * 创建日期：2018/5/30 11:33
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MineFragment.java
 * 类说明：
 */
public class MinePresenter extends RxPresenter<MineContract.View> implements MineContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public MinePresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


    public void getUserInfo() {
        HttpServerImpl.getUserInfo().subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO userBO) {
                if (mView != null) {
                    mView.getUserInfo(userBO);
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
     * 获取当前设备连接
     */
    public void getLinkDevice() {
        HttpServerImpl.getLinkDevice().subscribe(new HttpResultSubscriber<DeviceBO>() {
            @Override
            public void onSuccess(DeviceBO deviceBO) {
                if (mView != null) {
                    mView.getLinkDevice(deviceBO);
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
     * 获取设备数量
     */
    public void getDeviceData() {
        HttpServerImpl.getDeviceData().subscribe(new HttpResultSubscriber<DeviceLinkBO>() {
            @Override
            public void onSuccess(DeviceLinkBO s) {
                if (mView != null) {
                    mView.getDeviceData(s);
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
     * 操作推送开关
     */
    public void isPushDay() {
        HttpServerImpl.isDayPush().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    getUserInfo();
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