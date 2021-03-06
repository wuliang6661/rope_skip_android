package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.mine.contract.AddAddressContract;

import java.util.Map;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： CommonPresenter.java
 * 类说明：
 */
public class AddAddressPresenter extends RxPresenter<AddAddressContract.View> implements AddAddressContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public AddAddressPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    /**
     * 获取省市区
     */
    public void getShengshiqu() {
        HttpServerImpl.getAreaInfo().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

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
     * 保存地址
     */
    public void saveAddress(Map<String, Object> params) {
        HttpServerImpl.saveAddress(params).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.saveSouress();
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