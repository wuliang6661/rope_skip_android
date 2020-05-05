package com.habit.star.ui.mine.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.mine.contract.MyAddressListContract;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： MyAddressListPresenter.java
 * 类说明：
 */
public class MyAddressListPresenter extends RxPresenter<MyAddressListContract.View> implements MyAddressListContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public MyAddressListPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}