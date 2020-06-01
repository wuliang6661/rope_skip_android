package com.tohabit.skip.ui.mine.presenter;


import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.pojo.po.FamilyUserBO;
import com.tohabit.skip.ui.mine.contract.FamilyMemberContract;
import com.tohabit.skip.utils.ToastUtil;

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