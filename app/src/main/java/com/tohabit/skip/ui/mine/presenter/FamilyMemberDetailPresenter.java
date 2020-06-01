package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.FamilyUserDetailsBO;
import com.tohabit.skip.ui.mine.contract.FamilyMemberDetailContract;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： CommonPresenter.java
 * 类说明：
 */
public class FamilyMemberDetailPresenter extends RxPresenter<FamilyMemberDetailContract.View> implements FamilyMemberDetailContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public FamilyMemberDetailPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    /**
     * 获取家庭成员跳绳记录
     */
    public void getUser(int id) {
        HttpServerImpl.getUser(id).subscribe(new HttpResultSubscriber<FamilyUserDetailsBO>() {
            @Override
            public void onSuccess(FamilyUserDetailsBO s) {
               if(mView != null){
                   mView.getUserDetails(s);
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