package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.mine.contract.PersonalDataContract;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： PersonalDataContract.java
 * 类说明：
 */
public class PersonalDataPresenter extends RxPresenter<PersonalDataContract.View> implements PersonalDataContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public PersonalDataPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


    public void getUserInfo() {
        HttpServerImpl.getUserInfo().subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO userBO) {
                if (mView != null) {
                    mView.getUserInfo(userBO);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.showError(message);
                }
            }
        });
    }

}