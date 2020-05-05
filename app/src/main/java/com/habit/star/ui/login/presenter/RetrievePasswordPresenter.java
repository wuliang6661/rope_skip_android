package com.habit.star.ui.login.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.login.contract.RetrievePasswordContract;

import javax.inject.Inject;

/**
 * @date:  2020-02-16 12:06
 * @ClassName: RetrievePasswordPresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class RetrievePasswordPresenter extends RxPresenter<RetrievePasswordContract.View> implements RetrievePasswordContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public RetrievePasswordPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}