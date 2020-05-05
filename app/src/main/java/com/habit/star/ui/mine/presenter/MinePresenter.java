package com.habit.star.ui.mine.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.ui.mine.contract.MineContract;

import javax.inject.Inject;

/**
 * 创建日期：2018/5/30 11:33
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MineFragment.java
 * 类说明：
 */
public class MinePresenter extends RxPresenter<MineContract.View> implements MineContract.Presenter {

    RetrofitHelper mRetrofitHelper;
    @Inject
    public MinePresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper= retrofitHelper;
    }

//    @Override
//    public void getUserInfo() {
//        if (App.getInstance().loginBean ==null){
//            return;
//        }
//        addSubscrebe(mRetrofitHelper.getUserInfo(App.getInstance().loginBean.token,App.getInstance().loginBean.type,App.getInstance().loginBean.cate).subscribe(new Action1<UserInfoMode>() {
//            @Override
//            public void call(UserInfoMode userInfoMode) {
//                mView.getUserInfo(userInfoMode);
//            }
//        }, new ActionError(App.getStringResource(R.string.getUserInfoError)) {
//            @Override
//            public void onError(String msg, int code) {
//                mView.showError(msg);
//            }
//        }));
//    }
}