package com.habit.star.ui.devicemanager;

import com.habit.star.R;
import com.habit.star.base.BaseActivity;


/**
 * 新增或编辑设备页面
 */
public class DevecesUpdateActivty extends BaseActivity {
    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_devices_update;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {

        goBack();

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


    /**
     * 保存设备
     */
    private void saveDevices(){

    }
}
