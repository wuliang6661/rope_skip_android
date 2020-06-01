package com.tohabit.skip.ui.mine.presenter;

import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.ui.mine.contract.SystemSettingContract;

import javax.inject.Inject;

/**
 * @date:  2020-02-16 12:30
 * @ClassName: SystemSettingPresenter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class SystemSettingPresenter extends RxPresenter<SystemSettingContract.View> implements SystemSettingContract.Presenter {

    @Inject
    public SystemSettingPresenter() {
    }

//    @Override
//    public void logout() {
//        mView.showProgress();
//        addSubscrebe(ApiFactory.logout().subscribe(new Action1<String>() {
//            @Override
//            public void call(String bean) {
//                RetrofitHelper.getCookie().clear();
//                PrefUtils.setPrefString(App.getInstance(), Constants.PREF_KEY_COOKIE, "");
//                mView.hideProgress();
//                mView.setLogoutSuccess();
//            }
//        }, new ActionError(App.getStringResource(R.string.exit_login_failed)) {
//            @Override
//            public void onError(String msg, int code) {
//                RetrofitHelper.getCookie().clear();
//                PrefUtils.setPrefString(App.getInstance(), Constants.PREF_KEY_COOKIE, "");
//                mView.hideProgress();
//                mView.setLogoutFailed(msg);
//            }
//        }));
//    }
}