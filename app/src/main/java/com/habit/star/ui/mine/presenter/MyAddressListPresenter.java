package com.habit.star.ui.mine.presenter;


import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.AddressBO;
import com.habit.star.ui.mine.contract.MyAddressListContract;

import java.util.List;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MyAddressListPresenter.java
 * 类说明：
 */
public class MyAddressListPresenter extends RxPresenter<MyAddressListContract.View> implements MyAddressListContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public MyAddressListPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


    /**
     * 获取地址列表
     */
    public void getAddressList() {
        HttpServerImpl.getAddressList().subscribe(new HttpResultSubscriber<List<AddressBO>>() {
            @Override
            public void onSuccess(List<AddressBO> s) {
                if (mView != null) {
                    mView.getAddressList(s);
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
     * 设置默认地址
     */
    public void setDefaltAddress(int id) {
        HttpServerImpl.defaultAddress(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if(mView != null){
                    mView.defaltAddressSourcess();
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


    public void deleteAddress(int id){
        HttpServerImpl.delAddress(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if(mView != null){
                    mView.deleteAddressSouress();
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