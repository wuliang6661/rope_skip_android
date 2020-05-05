package com.habit.star.ui.mine.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.mine.contract.HelpCenterContract;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： HelpCenterPresenter.java
 * 类说明：
 */
public class HelpCenterPresenter extends RxPresenter<HelpCenterContract.View> implements HelpCenterContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public HelpCenterPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}