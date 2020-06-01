package com.tohabit.skip.ui.login.presenter;


import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.login.contract.PerfectInformationContract;

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