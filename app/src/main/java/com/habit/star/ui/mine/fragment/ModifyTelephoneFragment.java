package com.habit.star.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.login.activity.LoginActivity;
import com.habit.star.ui.login.contract.ModifyTelephoneContract;
import com.habit.star.ui.mine.presenter.ModifyTelephonePresenter;
import com.habit.star.utils.ToastUtil;

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
        etTel.setText(App.userBO.getPhone());
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
                if (TextUtils.isEmpty(etTel.getText().toString())) {
                    showError("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(etPleaseInputMsgCode.getText().toString())) {
                    showError("请输入短信验证");
                    return;
                }
                llType1.setVisibility(View.GONE);
                llType2.setVisibility(View.VISIBLE);
                tvType1Lable.setTextColor(getResources().getColor(R.color.color_C3C3C3));
                tvType2Lable.setTextColor(getResources().getColor(R.color.color_7EC7F5));

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
                showError("修改成功");

                Intent intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, LoginActivity.FLAG_LOGIN_TAG);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(_mActivity, LoginActivity.class);
                startActivity(intent);
                _mActivity.finish();
                break;
        }
    }
}
