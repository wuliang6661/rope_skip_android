package com.tohabit.skip.ui.mine.fragment;

import android.content.Intent;
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
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.ui.login.activity.LoginActivity;
import com.tohabit.skip.ui.login.contract.ModifyTelephoneContract;
import com.tohabit.skip.ui.mine.presenter.ModifyTelephonePresenter;
import com.tohabit.skip.utils.AppManager;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @version V1.0
 * @date: 2020-04-25 21:52
 * @ClassName: ModifyTelephoneFragment.java
 * @Description:修改手机密码
 * @author: sundongdong
 */
public class ModifyTelephoneFragment extends BaseFragment<ModifyTelephonePresenter> implements ModifyTelephoneContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.tv_type2_lable_fragment_retrieve_password)
    AppCompatTextView tvType2Lable;
    @BindView(R.id.tv_type1_lable_fragment_retrieve_password)
    AppCompatTextView tvType1Lable;
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
    @BindView(R.id.et_tel2_fragment_register)
    AppCompatEditText etTel2;
    @BindView(R.id.et2_please_input_msg_code_fragment_register)
    AppCompatEditText et2PleaseInputMsgCode;
    @BindView(R.id.btn2_send_code_fragment_register)
    AppCompatTextView btn2SendCode;
    @BindView(R.id.btn2_next_fragment_register)
    AppCompatButton btn2Next;
    @BindView(R.id.ll_type2_fragment_retrieve_password)
    LinearLayout llType2;


    public static ModifyTelephoneFragment newInstance(Bundle bundle) {
        ModifyTelephoneFragment fragment = new ModifyTelephoneFragment();
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
        return R.layout.fragment_modify_telephone;
    }

    @Override
    protected String getLogTag() {
        return "ModifyTelephoneFragment %s";
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
        etTel.setHint("当前手机号码" + Utils.settingphone(App.userBO.getPhone()));
        etTel.setEnabled(false);
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
            R.id.btn2_next_fragment_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next_fragment_register:
//                if (TextUtils.isEmpty(etTel.getText().toString())) {
//                    showError("请输入手机号码");
//                    return;
//                }
                if (TextUtils.isEmpty(etPleaseInputMsgCode.getText().toString())) {
                    showError("请输入短信验证");
                    return;
                }
                mPresenter.verifyUserInfo(etTel.getText().toString(), etPleaseInputMsgCode.getText().toString());
                break;
            case R.id.btn2_next_fragment_register:
                if (TextUtils.isEmpty(etTel2.getText().toString())) {
                    showError("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(et2PleaseInputMsgCode.getText().toString())) {
                    showError("请输入短信验证");
                    return;
                }
                break;
        }
    }

    int selectSend = 0; // 默认第一页的

    @OnClick({R.id.btn_send_code_fragment_register, R.id.btn2_send_code_fragment_register})
    public void sendCode(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code_fragment_register:
                selectSend = 0;
                mPresenter.sendCode(App.userBO.getPhone());
                break;
            case R.id.btn2_send_code_fragment_register:
                selectSend = 1;
                mPresenter.sendCode(etTel2.getText().toString());
                break;
        }
    }


    @Override
    public void getYZMSuccess() {
        if (timer != null) {
            timer.cancel();
        }
        timer.start();
    }

    @Override
    public void YzSouress() {
        llType1.setVisibility(View.GONE);
        llType2.setVisibility(View.VISIBLE);
        tvType1Lable.setTextColor(getResources().getColor(R.color.color_C3C3C3));
        tvType2Lable.setTextColor(getResources().getColor(R.color.color_7EC7F5));
    }

    @Override
    public void updateSourcess() {
        showError("修改成功");
        Intent intent = new Intent();
        intent.putExtra(RouterConstants.ARG_MODE, LoginActivity.FLAG_LOGIN_TAG);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(_mActivity, LoginActivity.class);
        AppManager.getAppManager().finishAllActivity();
        startActivity(intent);
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (selectSend == 0) {
                btnSendCode.setEnabled(false);
                btnSendCode.setText("重新发送" + (millisUntilFinished / 1000) + "S");
            } else {
                btn2SendCode.setEnabled(false);
                btn2SendCode.setText("重新发送" + (millisUntilFinished / 1000) + "S");
            }
        }

        @Override
        public void onFinish() {
            if (selectSend == 0) {
                btnSendCode.setEnabled(true);
                btnSendCode.setText("重新发送");
            } else {
                btn2SendCode.setEnabled(true);
                btn2SendCode.setText("重新发送");
            }
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
