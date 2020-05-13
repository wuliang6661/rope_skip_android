package com.habit.star.ui.mine.presenter;


import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.FamilyUserDetailsBO;
import com.habit.star.ui.mine.contract.FamilyMemberDetailContract;

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