package com.tohabit.skip.ui.train.presenter;

import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.MessageBO;
import com.tohabit.skip.ui.mine.contract.MessageListContract;

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
    public void getList(String type, int pageNum) {
        HttpServerImpl.getMessageList(pageNum).subscribe(new HttpResultSubscriber<List<MessageBO>>() {
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