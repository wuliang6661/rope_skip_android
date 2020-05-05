package com.habit.star.ui.mine.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.mine.contract.AddAddressContract;
import com.habit.star.ui.mine.contract.MyAchievementContract;

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
public class MyAchievementPresenter extends RxPresenter<MyAchievementContract.View> implements MyAchievementContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public MyAchievementPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}