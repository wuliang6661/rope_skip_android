package com.tohabit.skip.ui.login.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.ui.XieYiActivity;
import com.tohabit.skip.ui.login.contract.RegisterContract;
import com.tohabit.skip.ui.login.presenter.RegisterPresenter;
import com.tohabit.skip.utils.MD5;
import com.tohabit.skip.utils.ToastUtil;

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

    private int isBuy = 1; // 0未购买， 1 为购买

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
                String phone = mEtTelFragmentRegister.getText().toString().trim();
                if (StringUtils.isEmpty(phone)) {
                    showToast("请填写手机号！");
                    return;
                }
                mPresenter.sendCode(phone);
                break;
            case R.id.ll_taggle_close_fragment_register:
                mLlTaggleCloseFragmentRegister.setVisibility(View.GONE);
                mLlTaggleOpenFragmentRegister.setVisibility(View.VISIBLE);
                isBuy = 1;
                break;
            case R.id.ll_taggle_open_fragment_register:
                mLlTaggleOpenFragmentRegister.setVisibility(View.GONE);
                mLlTaggleCloseFragmentRegister.setVisibility(View.VISIBLE);
                isBuy = 0;
                break;
            case R.id.tv_has_account_fragment_register:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.tv_read_remind_fragment_register:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                gotoActivity(XieYiActivity.class, bundle, false);
                break;
            case R.id.btn_next_fragment_register:
                register();
                break;
        }
    }


    private void register() {
        String phone = mEtTelFragmentRegister.getText().toString().trim();
        String password = mEtPasswordFragmentRegister.getText().toString().trim();
        String msgCode = mEtPleaseInputMsgCodeFragmentRegister.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请填写正确手机号！");
            return;
        }
        if (StringUtils.isEmpty(msgCode)) {
            showToast("请填写验证码！");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            showToast("请填写密码！");
            return;
        }
        if (!mCbReadFragmentRegister.isChecked()) {  //是否选中协议
            showToast("请先同意用户服务协议！");
            return;
        }
        mPresenter.register(phone, MD5.strToMd5Low32(MD5.strToMd5Low32(password) + "bby"), msgCode, isBuy);

    }


    @Override
    public void registerSuccess() {
        App.spUtils.put(Constants.PREF_KEY_USER, mEtTelFragmentRegister.getText().toString());
        App.spUtils.put(Constants.PREF_KEY_PASSWORD, mEtPasswordFragmentRegister.getText().toString());
        ToastUtil.show("注册成功");
        _mActivity.onBackPressedSupport();
    }

    @Override
    public void getYZMSuccess() {
        timer.start();
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnSendCodeFragmentRegister.setEnabled(false);
            mBtnSendCodeFragmentRegister.setText("重新获取" + (millisUntilFinished / 1000) + "S");
        }

        @Override
        public void onFinish() {
            mBtnSendCodeFragmentRegister.setEnabled(true);
            mBtnSendCodeFragmentRegister.setText("重新获取");
        }
    };

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }


}
