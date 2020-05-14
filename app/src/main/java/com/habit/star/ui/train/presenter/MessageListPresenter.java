package com.habit.star.ui.train.presenter;

import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.MessageBO;
import com.habit.star.ui.mine.contract.MessageListContract;
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
public class MessageListPresenter extends RxPresenter<MessageListContract.View> implements MessageListContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public MessageListPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getList(String type) {
        HttpServerImpl.getMessageList().subscribe(new HttpResultSubscriber<List<MessageBO>>() {
            @Override
            public void onSuccess(List<MessageBO> s) {
                if (mView != null) {
                    mView.setList(s);
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