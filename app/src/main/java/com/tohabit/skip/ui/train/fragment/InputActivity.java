package com.tohabit.skip.ui.train.fragment;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.algorithm.skipevaluation.Evaluator;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.SearchActivty;
import com.tohabit.skip.utils.Example;
import com.tohabit.skip.utils.StringUtils;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手动输入页面
 */
public class InputActivity extends BaseActivity {


    @BindView(R.id.tv_battery_fragment_train_main)
    AppCompatTextView tvBatteryFragmentTrainMain;
    @BindView(R.id.blue_state_img)
    AppCompatImageView ivConnnetState;
    @BindView(R.id.tv_blue_connect_statusfragment_train_main)
    AppCompatTextView tvConnectState;
    @BindView(R.id.blue_layout)
    LinearLayout blueLayout;
    @BindView(R.id.et_shichang)
    EditText etShichang;
    @BindView(R.id.et_zongshu)
    EditText etZongshu;
    @BindView(R.id.et_cishu)
    EditText etCishu;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_input_hand;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("手动输入");
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


    @Override
    protected void onResume() {
        super.onResume();
        freshView();
        getDeviceQc();
    }


    @OnClick(R.id.btn_commit)
    public void commit() {
        String shichang = etShichang.getText().toString().trim();
        String zongshu = etZongshu.getText().toString().trim();
        String cishu = etCishu.getText().toString().trim();
        if (StringUtils.isEmpty(shichang)) {
            showToast("请输入跳绳时长！");
            return;
        }
        if (StringUtils.isEmpty(zongshu)) {
            showToast("请输入跳绳总数！");
            return;
        }
        if (StringUtils.isEmpty(cishu)) {
            showToast("请输入断绳次数！");
            return;
        }
        Example example = new Example(getAssets(), Integer.parseInt(cishu), Integer.parseInt(zongshu), Integer.parseInt(shichang));
        Evaluator evaluator = example.getData();
        Map<String, Object> params = new HashMap<>();
        params.put("actionScore", evaluator.getRopeSwingingScore());//动作分数
        params.put("breakNum", cishu);   //断绳数量
        params.put("coordinateScore", evaluator.getCoordinationScore()); //协调分数
        params.put("enduranceScore", evaluator.getEnduranceScore());  //耐力得分
        params.put("rhythmScore", evaluator.getSpeedStabilityScore());  //节奏得分
        params.put("skipNum", zongshu);  //跳绳次数
        params.put("skipTime", shichang);
        params.put("stableScore", evaluator.getPositionStabilityScore());
        params.put("deviceId", null);  //todo 设备id，暂时缺失
        HttpServerImpl.input(params).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("提交成功！");
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void freshView() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            tvConnectState.setText("已连接");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_home19);
        } else {
            tvConnectState.setText("已断开");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueEvent event) {
        if (event.isConnect == UartService.STATE_CONNECTED) {
            tvConnectState.setText("已连接");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_home19);
        } else if (event.isConnect == UartService.STATE_CONNECTING) {
            tvConnectState.setText("设备连接中...");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        } else if (event.isConnect == UartService.NITIFI_SOURESS) {  //监听已经开始建立
            getDeviceQc();
        } else {
            tvConnectState.setText("已断开");
        }
    }


    /**
     * 获取电量
     */
    private void getDeviceQc() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x11;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetEQCmd().getCmdByte());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
        BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
        if (UartService.COUNT_OPENTION == 0x11) {  //电量
            getDeviceQcAndType(String.valueOf(builder.getDataBody()[0]), String.valueOf(builder.getDataBody()[1]));
        }
    }

    public void getDeviceQcAndType(String dianliang, String type) {
        tvBatteryFragmentTrainMain.setText(dianliang + "%");
    }


    @OnClick(R.id.blue_layout)
    public void onClickBlue() {
        gotoActivity(SearchActivty.class, false);
    }
}
