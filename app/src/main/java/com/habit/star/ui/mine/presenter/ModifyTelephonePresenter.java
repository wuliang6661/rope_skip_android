package com.habit.star.ui.mine.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.login.contract.ModifyTelephoneContract;
import com.habit.star.ui.login.contract.RetrievePasswordContract;

import javax.inject.Inject;

/**
 * @date:  2020-04-25 21:53
 * @ClassName: ModifyTelephonePresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class ModifyTelephonePresenter extends RxPresenter<ModifyTelephoneContract.View> implements ModifyTelephoneContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public ModifyTelephonePresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }
}