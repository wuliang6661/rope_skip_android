package com.tohabit.skip.ui.login.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.ui.login.contract.RetrievePasswordContract;
import com.tohabit.skip.ui.login.presenter.RetrievePasswordPresenter;
import com.tohabit.skip.utils.MD5;
import com.tohabit.skip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建日期：2020-01-21 16:33
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：RetrievePasswordFragment.java
 * 类说明：找回密码
 */
public class RetrievePasswordFragment extends BaseFragment<RetrievePasswordPresenter> implements RetrievePasswordContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.et_tel_fragment_register)
    AppCompatEditText etTel;
    @BindView(R.id.et_please_input_msg_code_fragment_register)
    AppCompatEditText etPleaseInputMsgCode;
    @BindView(R.id.btn_send_code_fragment_register)
    AppCompatTextView btnSendCode;
    @BindView(R.id.btn_next_fragment_register)
    AppCompatButton btnNext;
    @BindView(R.id.ll_type1_fragment_retrieve_password)
    LinearLayout llType1;
    @BindView(R.id.et_password_fragment_register)
    AppCompatEditText etPassword;
    @BindView(R.id.et_comfirm_password_fragment_register)
    AppCompatEditText etComfirmPassword;
    @BindView(R.id.btn_reset_confirm_fragment_register)
    AppCompatButton btnReset;
    @BindView(R.id.ll_type2_fragment_retrieve_password)
    LinearLayout llType2;
    @BindView(R.id.tv_type2_lable_fragment_retrieve_password)
    AppCompatTextView tvType2Lable;
    @BindView(R.id.tv_type1_lable_fragment_retrieve_password)
    AppCompatTextView tvType1Lable;

    private String phone;

    public static RetrievePasswordFragment newInstance(Bundle bundle) {
        RetrievePasswordFragment fragment = new RetrievePasswordFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_retrieve_password;
    }

    @Override
    protected String getLogTag() {
        return "RetrievePasswordFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
        String userName = App.spUtils.getString(Constants.PREF_KEY_USER, "");
        etTel.setText(userName);
    }


    private void initDialog() {

    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    @SingleClick
    @OnClick({R.id.btn_next_fragment_register,
            R.id.btn_reset_confirm_fragment_register,
            R.id.btn_send_code_fragment_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next_fragment_register:
                if (TextUtils.isEmpty(etTel.getText().toString())) {
                    showError("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(etPleaseInputMsgCode.getText().toString())) {
                    showError("请输入短信验证");
                    return;
                }
                mPresenter.verifyPhone(etTel.getText().toString(), etPleaseInputMsgCode.getText().toString());
                break;
            case R.id.btn_reset_confirm_fragment_register:
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    showError("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(etComfirmPassword.getText().toString())) {
                    showError("请输入确认密码");
                    return;
                }
                if (!etComfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
                    showError("确认密码错误，请重新输入");
                    return;
                }
                mPresenter.forgetPassword(phone, MD5.strToMd5Low32(MD5.strToMd5Low32(etPassword.getText().toString()) + "bby"));
                break;
            case R.id.btn_send_code_fragment_register:
                if (TextUtils.isEmpty(etTel.getText().toString())) {
                    showError("请输入手机号码");
                    return;
                }
                mPresenter.sendCode(etTel.getText().toString());
                break;
        }
    }

    @Override
    public void verifyPhoneSuress() {
        llType1.setVisibility(View.GONE);
        llType2.setVisibility(View.VISIBLE);
        tvType1Lable.setTextColor(getResources().getColor(R.color.color_C3C3C3));
        tvType2Lable.setTextColor(getResources().getColor(R.color.color_7EC7F5));
        phone = etTel.getText().toString();
    }

    @Override
    public void forwordPasswordSuress() {
        showError("修改成功！");
        _mActivity.onBackPressedSupport();
    }

    @Override
    public void getYZMSuccess() {
        timer.start();
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnSendCode.setEnabled(false);
            btnSendCode.setText("重新发送" + (millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            btnSendCode.setEnabled(true);
            btnSendCode.setText("重新发送");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

}
