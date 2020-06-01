package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.ui.mine.contract.MyHonorCertificateContract;

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
public class MyHonorCertificatePresenter extends RxPresenter<MyHonorCertificateContract.View> implements MyHonorCertificateContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public MyHonorCertificatePresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}