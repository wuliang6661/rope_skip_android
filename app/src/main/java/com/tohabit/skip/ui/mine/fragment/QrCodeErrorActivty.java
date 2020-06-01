package com.tohabit.skip.ui.mine.fragment;

import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.zxing.activity.CaptureActivity;

import butterknife.OnClick;

public class QrCodeErrorActivty extends BaseActivity {
    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_qr_code_error;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("扫描结果");
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

    @OnClick(R.id.btn_commit)
    public void btnClick() {
        gotoActivity(CaptureActivity.class, true);
    }
}
