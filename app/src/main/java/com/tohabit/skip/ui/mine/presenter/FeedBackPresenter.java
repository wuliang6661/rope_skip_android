package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.FeedBackBO;
import com.tohabit.skip.ui.mine.contract.FeedBackContract;

import java.util.List;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： FeedBackPresenter.java
 * 类说明：
 */
public class FeedBackPresenter extends RxPresenter<FeedBackContract.View> implements FeedBackContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public FeedBackPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    public void getFeedbackType() {
        HttpServerImpl.getFeedbackType().subscribe(new HttpResultSubscriber<List<FeedBackBO>>() {
            @Override
            public void onSuccess(List<FeedBackBO> s) {
                if (mView != null) {
                    mView.getFeedBack(s);
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


    public void addFeedback(int id, String phone, String message, String imageUrl) {
        HttpServerImpl.addFeedback(id, phone, message, imageUrl).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.addSouress();
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