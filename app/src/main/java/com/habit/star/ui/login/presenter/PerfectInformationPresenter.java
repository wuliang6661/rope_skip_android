package com.habit.star.ui.login.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.login.contract.PerfectInformationContract;

import javax.inject.Inject;

/**
 * @date:  2020-02-16 12:06
 * @ClassName: RetrievePasswordPresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class PerfectInformationPresenter extends RxPresenter<PerfectInformationContract.View> implements PerfectInformationContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public PerfectInformationPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}