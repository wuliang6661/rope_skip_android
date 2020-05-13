package com.habit.star.ui.mine.presenter;


import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.FamilyUserBO;
import com.habit.star.ui.mine.contract.FamilyMemberContract;
import com.habit.star.ui.mine.contract.FamilyMemberDetailContract;
import com.habit.star.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： FamilyMemberPresenter.java
 * 类说明：
 */
public class FamilyMemberPresenter extends RxPresenter<FamilyMemberContract.View> implements FamilyMemberContract.Presenter {
    RetrofitHelper mRetrofitHelper;

    @Inject
    public FamilyMemberPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    public void getFamilyUserList() {
        HttpServerImpl.getFamilyUserList().subscribe(new HttpResultSubscriber<List<FamilyUserBO>>() {
            @Override
            public void onSuccess(List<FamilyUserBO> s) {
                if (mView != null) {
                    mView.getAllUserBO(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    ToastUtil.show(message);
                }
            }
        });
    }


    public void delFamliayUser(int id) {
        HttpServerImpl.delUser(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    ToastUtil.show("移除成功！");
                    getFamilyUserList();
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    ToastUtil.show(message);
                }
            }
        });
    }
}