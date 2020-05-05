package com.habit.star.ui.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.app.Constants;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.activity.MainActivity;
import com.habit.star.ui.login.presenter.LoginPresenter;
import com.habit.star.ui.login.contract.LoginContract;
import com.habit.star.utils.PrefUtils;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/6/2 10:24
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： LoginFragment.java
 * 类说明：登陆界面
 */
public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_tel_fragment_login)
    AppCompatEditText mEtTel;
    @BindView(R.id.et_password_fragment_login)
    AppCompatEditText mEtPassword;
    @BindView(R.id.ll_taggle_close_fragment_login)
    LinearLayout mLlTaggleClose;
    @BindView(R.id.ll_taggle_open_fragment_login)
    LinearLayout mLlTaggleOpen;
    @BindView(R.id.tv_forget_password_fragment_login)
    AppCompatButton mTvForgetPassword;
    @BindView(R.id.btn_submit_fragment_login)
    AppCompatButton mBtnSubmit;
    @BindView(R.id.tv_regist_new_user_fragment_login)
    AppCompatTextView mTvRegistNewUser;

    @BindView(R.id.progress_fragment_login)
    ProgressbarLayout progressbar;

    public static LoginFragment newInstance(Bundle args) {
        LoginFragment fragment = new LoginFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected String getLogTag() {
        return "LoginFragment %s";
    }

    @Override
    protected void initEventAndData() {
        String userName = PrefUtils.getPrefString(mContext, Constants.PREF_KEY_USER, "");
        String passWord = PrefUtils.getPrefString(mContext, Constants.PREF_KEY_PASSWORD, "");
        mEtTel.setText(userName);
        mEtPassword.setText(passWord);
    }

//    @Override
//    public void loginSuccess(LoginBean loginBean) {
//        if (loginBean == null) {
//            return;
//        }
//        App.getInstance().loginBean = loginBean;
//        ///保存token
//        PrefUtils.setPrefString(mContext, Constants.PREF_KEY_TOKEN, loginBean.token);
//        PrefUtils.setPrefInt(mContext, Constants.PREF_KEY_TYPE, loginBean.type);
//        PrefUtils.setPrefInt(mContext, Constants.PREF_KEY_CATE, loginBean.cate);
//        mPresenter.getUserInfo();
//    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.shortShow(msg);
    }

    @Override
    public void showError(int errorCode) {
    }

    @SingleClick
    @OnClick({
            R.id.ll_taggle_close_fragment_login,
            R.id.ll_taggle_open_fragment_login,
            R.id.tv_forget_password_fragment_login,
            R.id.btn_submit_fragment_login,
            R.id.tv_regist_new_user_fragment_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_taggle_close_fragment_login:
                mLlTaggleClose.setVisibility(View.GONE);
                mLlTaggleOpen.setVisibility(View.VISIBLE);
                mEtPassword.setInputType(128);
                break;
            case R.id.ll_taggle_open_fragment_login:
                mLlTaggleOpen.setVisibility(View.GONE);
                mLlTaggleClose.setVisibility(View.VISIBLE);
                mEtPassword.setInputType(129);
                break;
            case R.id.tv_forget_password_fragment_login:
                start(RetrievePasswordFragment.newInstance(null));
                break;
            case R.id.btn_submit_fragment_login:
                if (TextUtils.isEmpty(mEtTel.getText().toString())) {
                    showError("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
                    showError("请输入密码");
                    return;
                }
                PrefUtils.setPrefString(mContext, Constants.PREF_KEY_USER, mEtTel.getText().toString());
                PrefUtils.setPrefString(mContext, Constants.PREF_KEY_PASSWORD, mEtPassword.getText().toString());
                Intent intent = new Intent();
                intent.setClass(_mActivity, MainActivity.class);
                startActivity(intent);
                _mActivity.finish();
                break;
            case R.id.tv_regist_new_user_fragment_login:
                start(RegisterFragment.newInstance(null));
                break;
        }
    }

//    @Override
//    public void getUserInfo(UserInfoMode userInfoMode) {
//        PrefUtils.setPrefString(mContext, Constants.PREF_KEY_USER, mEtTel.getText().toString());
//        PrefUtils.setPrefString(mContext, Constants.PREF_KEY_PASSWORD, mEtPassword.getText().toString());
//
//        App.getInstance().userInfoMode = userInfoMode;
//        Intent intent = new Intent();
//        intent.setClass(_mActivity, MainActivity.class);
//        startActivity(intent);
//        _mActivity.finish();
//    }

//    @SingleClick
//    @OnClick({R.id.tv_forget_password, R.id.btn_submit_fragment_login})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_forget_password:
//                break;
//            case R.id.btn_submit_fragment_login:
//                if (TextUtils.isEmpty(mEtTel.getText().toString())) {
//                    showError("请输入手机号码");
//                    return;
//
//                }
//                if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
//                    showError("请输入密码");
//                    return;
//                }
//                mPresenter.login(mEtTel.getText().toString(), mEtPassword.getText().toString());
//                break;
//        }
//    }
}
