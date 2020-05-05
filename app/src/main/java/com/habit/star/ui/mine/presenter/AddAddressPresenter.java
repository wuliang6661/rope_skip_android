package com.habit.star.ui.mine.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.mine.contract.AddAddressContract;

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
public class AddAddressPresenter extends RxPresenter<AddAddressContract.View> implements AddAddressContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public AddAddressPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}