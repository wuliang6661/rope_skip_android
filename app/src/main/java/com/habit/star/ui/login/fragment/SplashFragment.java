package com.habit.star.ui.login.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.blankj.utilcode.util.StringUtils;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.app.Constants;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.ui.activity.MainActivity;
import com.habit.star.ui.login.contract.SplashContract;
import com.habit.star.ui.login.presenter.SplashPresenter;
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startJump();
        }
    };


    /**
     * 开始界面跳转
     */
    private void startJump() {
        App.token = App.spUtils.getString(Constants.PREF_KEY_TOKEN);
        if (StringUtils.isEmpty(App.token)) {
            startWithPop(LoginFragment.newInstance(null));
        } else {
            mPresenter.getUserInfo();
        }
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
        mHandler.postDelayed(runnable, splashTime);
    }


    @Override
    public void getUserInfo(UserBO userInfoMode) {
        App.userBO = userInfoMode;
        ///保存token
        App.spUtils.put(Constants.PREF_KEY_TOKEN, userInfoMode.getToken());
        gotoActivity(MainActivity.class, true);
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
