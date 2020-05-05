package com.habit.star.ui.login.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.app.Constants;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.login.presenter.SplashPresenter;
import com.habit.star.ui.login.contract.SplashContract;
import com.habit.star.ui.login.bean.LoginBean;
import com.habit.star.ui.mine.bean.UserInfoMode;
import com.habit.star.utils.PrefUtils;
import com.habit.star.utils.ToastUtil;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;

/**
 * 创建日期：2018/6/1 11:04
 *
 * @author sundongdong
 * @version 3.0
 * @since 文件名称： SplashFragment.java
 * 类说明：启动界面
 */
public final class SplashFragment extends BaseFragment<SplashPresenter> implements SplashContract.View {

    public static final int splashTime = 1500;
    Handler mHandler;

    private String token;
    private int type;
    private int cate;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startJump(true);
        }
    };

    /**
     * 开始界面跳转(默认进入登录界面)
     */
    private void startJump() {
        startJump(false);
    }

    /**
     * 开始界面跳转
     *
     * @param isLogged true表示自动登录成功，false表示没有自动登录
     */
    private void startJump(boolean isLogged) {
//        if (TextUtils.isEmpty(token)){
            startWithPop(LoginFragment.newInstance(null));
//            return;
//        }
//        mPresenter.getUserInfo();
    }


    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getLogTag() {
        return "SplashFragment";
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initEventAndData() {
        _mActivity.setFragmentAnimator(new DefaultNoAnimator());
        mHandler = new Handler();
        token = PrefUtils.getPrefString(mContext,Constants.PREF_KEY_TOKEN,"");
        type = PrefUtils.getPrefInt(mContext,Constants.PREF_KEY_TYPE,-10);
        cate = PrefUtils.getPrefInt(mContext,Constants.PREF_KEY_CATE,-10);
        log(token);
        if (!TextUtils.isEmpty(token)){
            LoginBean loginBean =new LoginBean();
            loginBean.token = token;
            App.getInstance().loginBean = loginBean;
        }
        mHandler.postDelayed(runnable, splashTime);
    }


    @Override
    public void getUserInfo(UserInfoMode userInfoMode) {
//        App.getInstance().userInfoMode = userInfoMode;
//        Intent intent = new Intent();
//        intent.setClass(_mActivity, MainActivity.class);
//        startActivity(intent);
//        _mActivity.finish();
    }

    @Override
    public void getUserInfoError() {
        startWithPop(LoginFragment.newInstance(null));
    }

    @Override
    public void setInitComponentSuccess() {

    }

    @Override
    public void setInitComponentFailed() {

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }


    @Override
    public void showError(int errorCode) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnable);
        }
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressedSupport() {
        _mActivity.finish();
        return true;
    }

}
