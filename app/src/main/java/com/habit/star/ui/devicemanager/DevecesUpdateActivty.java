package com.habit.star.ui.devicemanager;

import android.widget.EditText;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.DeviceBO;
import com.habit.star.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 新增或编辑设备页面
 */
public class DevecesUpdateActivty extends BaseActivity {

    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    @BindView(R.id.et_mac)
    EditText etMac;
    @BindView(R.id.bt_save)
    TextView btSave;

    private int type;

    private DeviceBO deviceBO;

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
        type = getIntent().getExtras().getInt("type");
        if (type == 0) {  //增加设备
            setTitleText("增加设备");
        } else {
            deviceBO = (DeviceBO) getIntent().getExtras().getSerializable("device");
            etDeviceName.setText(deviceBO.getName());
            etMac.setText(deviceBO.getMacAddress());
            setTitleText("编辑设备");
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


    @OnClick(R.id.bt_save)
    public void save() {
        saveDevices();
    }


    /**
     * 保存设备
     */
    private void saveDevices() {
        String deviceName = etDeviceName.getText().toString().trim();
        String mac = etMac.getText().toString().trim();
        if (StringUtils.isEmpty(deviceName)) {
            showToast("设备名称不能为空!");
            return;
        }
        if (StringUtils.isEmpty(mac)) {
            showToast("设备Mac地址不能为空！");
            return;
        }
        showProgress();
        HttpServerImpl.saveDevices(type == 0 ? 0 : deviceBO.getId(), deviceName, mac).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                showToast("保存成功！");
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
