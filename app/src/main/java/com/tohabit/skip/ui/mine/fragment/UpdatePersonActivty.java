package com.tohabit.skip.ui.mine.fragment;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改身高体重的界面
 */
public class UpdatePersonActivty extends BaseActivity {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbarLayoutToolbar;
    @BindView(R.id.et_tel_fragment_register)
    AppCompatEditText etTelFragmentRegister;
    @BindView(R.id.btn_submit_fragment_modify_nick_name)
    AppCompatButton btnSubmitFragmentModifyNickName;

    private int type;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_person_message;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        toolbarLayoutToolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getExtras().getInt("type");
        if (type == 0) {
            toolbarLayoutToolbar.setTitle("修改体重");
            etTelFragmentRegister.setHint("请输入体重  单位：kg");
        } else {
            toolbarLayoutToolbar.setTitle("修改身高");
            etTelFragmentRegister.setHint("请输入身高  单位：cm");
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError(int errorCode) {

    }

    @OnClick(R.id.btn_submit_fragment_modify_nick_name)
    public void onClick() {
        if (type == 0) {
            updateWeight();
        } else {
            updateHeigjht();
        }
    }


    private void updateWeight() {
        showProgress(null);
        HttpServerImpl.updateWeight(etTelFragmentRegister.getText().toString().trim()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                finish();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void updateHeigjht() {
        showProgress(null);
        HttpServerImpl.updateHeight(etTelFragmentRegister.getText().toString().trim()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                finish();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }
}
