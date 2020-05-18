package com.habit.star.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.presenter.contract.CommonContract;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： CommonPresenter.java
 * 类说明：
 */
public class CommonPresenter extends RxPresenter<CommonContract.View> implements CommonContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public CommonPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


}