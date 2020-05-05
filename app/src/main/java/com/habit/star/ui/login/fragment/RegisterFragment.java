package com.habit.star.ui.login.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.login.presenter.RegisterPresenter;
import com.habit.star.ui.login.contract.RegisterContract;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/6/2 10:24
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： RegisterFragment.java
 * 类说明：注册界面
 */
public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress mToolbar;
    @BindView(R.id.et_tel_fragment_register)
    AppCompatEditText mEtTelFragmentRegister;
    @BindView(R.id.et_please_input_msg_code_fragment_register)
    AppCompatEditText mEtPleaseInputMsgCodeFragmentRegister;
    @BindView(R.id.btn_send_code_fragment_register)
    AppCompatTextView mBtnSendCodeFragmentRegister;
    @BindView(R.id.et_password_fragment_register)
    AppCompatEditText mEtPasswordFragmentRegister;
    @BindView(R.id.ll_taggle_close_fragment_register)
    LinearLayout mLlTaggleCloseFragmentRegister;
    @BindView(R.id.ll_taggle_open_fragment_register)
    LinearLayout mLlTaggleOpenFragmentRegister;
    @BindView(R.id.cb_read_fragment_register)
    CheckBox mCbReadFragmentRegister;
    @BindView(R.id.tv_read_remind_fragment_register)
    AppCompatTextView mTvReadRemindFragmentRegister;
    @BindView(R.id.btn_next_fragment_register)
    AppCompatButton mBtnNextFragmentRegister;
    @BindView(R.id.progress_fragment_register)
    ProgressbarLayout progressbarLayout;

    public static RegisterFragment newInstance(Bundle args) {
        RegisterFragment fragment = new RegisterFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected String getLogTag() {
        return "RegisterFragment %s";
    }

    @Override
    protected void initEventAndData() {
        mToolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }

    @Override
    public void showProgress() {
        progressbarLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressbarLayout.setVisibility(View.GONE);
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
            R.id.btn_send_code_fragment_register,
            R.id.ll_taggle_close_fragment_register,
            R.id.ll_taggle_open_fragment_register,
            R.id.tv_read_remind_fragment_register,
            R.id.tv_has_account_fragment_register,
            R.id.btn_next_fragment_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code_fragment_register:
                break;
            case R.id.ll_taggle_close_fragment_register:
                mLlTaggleCloseFragmentRegister.setVisibility(View.GONE);
                mLlTaggleOpenFragmentRegister.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_taggle_open_fragment_register:
                mLlTaggleOpenFragmentRegister.setVisibility(View.GONE);
                mLlTaggleCloseFragmentRegister.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_has_account_fragment_register:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.tv_read_remind_fragment_register:
                break;
            case R.id.btn_next_fragment_register:
                break;
        }
    }

//    @Override
//    public void registerSuccess(String token) {
//        ToastUtil.show("注册成功");
//    }
//
//    @Override
//    public void getYZMSuccess(String str) {
//        ToastUtil.shortShow(str);
//    }

//    @SingleClick
//    @OnClick({R.id.btn_submit_fragment_login
//            , R.id.tb_check_number, R.id.tv_read_remind})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tb_check_number:
//                if (TextUtils.isEmpty(mEtTel.getText().toString())) {
//                    showError("请输入手机号码");
//                    return;
//                }
//                mPresenter.getYZM(mEtTel.getText().toString());
//                break;
//            case R.id.btn_submit_fragment_login:
//
//                if (TextUtils.isEmpty(mEtTel.getText().toString())) {
//                    showError("请输入手机号码");
//                    return;
//                }
//                if (TextUtils.isEmpty(etCheckNumber.getText().toString())) {
//                    showError("请输入验证号码");
//                    return;
//                }
//                if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
//                    showError("请输入密码");
//                    return;
//                }
//                if (!cbRead.isChecked()) {
//                    showError("\'用户须知\'未选择");
//                    return;
//                }
//
//                mPresenter.register(mEtTel.getText().toString(), mEtPassword.getText().toString(), etCheckNumber.getText().toString());
//                break;
//            case R.id.tv_read_remind:
//                EventBus.getDefault().post(new WebMessgeEvent(Constants.URL_USER_PROTOCOL));
//                break;
//
//        }
//    }
}
