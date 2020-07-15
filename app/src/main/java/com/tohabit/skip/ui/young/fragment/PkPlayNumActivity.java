package com.tohabit.skip.ui.young.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.pojo.BaseResult;
import com.tohabit.skip.pojo.po.PkChangCiBO;
import com.tohabit.skip.pojo.po.PkResultBO;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.SearchActivty;
import com.tohabit.skip.ui.train.fragment.RopeSkipSettingFragment;
import com.tohabit.skip.ui.train.music.Player;
import com.tohabit.skip.ui.young.websocket.WebSocketUtils;
import com.tohabit.skip.utils.ByteUtils;
import com.tohabit.skip.utils.StringUtils;
import com.tohabit.skip.utils.SyncHistoryUtils;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.Utils;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;
import com.tohabit.skip.widget.CircleProgressbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PkPlayNumActivity extends BaseActivity {


    @BindView(R.id.tv_battery_fragment_train_plan)
    AppCompatTextView tvBattery;
    @BindView(R.id.tv_connect_state_fragment_train_plan)
    AppCompatTextView tvConnectState;
    @BindView(R.id.tv_time_second_fragment_train_plan)
    AppCompatTextView tvTimeSecond;
    @BindView(R.id.tv_time_count_fragment_train_main)
    AppCompatTextView tvTimeCountFragmentTrainMain;
    @BindView(R.id.tv_contral_fragment_train_plan)
    AppCompatTextView tvContral;
    @BindView(R.id.iv_connnet_state_fragment_train_plan)
    AppCompatImageView ivConnnetState;
    @BindView(R.id.music_text)
    AppCompatTextView musicText;
    @BindView(R.id.music_layout)
    LinearLayout musicLayout;
    @BindView(R.id.countdown_bar)
    CircleProgressbar countdownBar;
    @BindView(R.id.title_text)
    AppCompatTextView titleText;

    private boolean connectState;
    private boolean testState;
    private int timeCount = 0;

    /**
     * 跳绳次数
     */
    private int skipNum = 0;

    /**
     * 默认获取的第一次跳绳数量
     */
    private int firstTiaoShengNum = Integer.MAX_VALUE;

    /**
     * 倒计数
     */
    private int maxNum;

    private int pkChangCiId;

    private PkChangCiBO data;

    private CountDownTimer timer;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_play_num;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        tvTimeCountFragmentTrainMain.setTypeface(App.getInstance().tf);

        data = (PkChangCiBO) getIntent().getExtras().getSerializable("data");
        maxNum = data.getMaxNum();
        tvBattery.setText(PKHomeActivity.dianliang + "%");
        tvTimeSecond.setText(Utils.timeToString(timeCount));
        tvTimeCountFragmentTrainMain.setText(maxNum + "");
        countdownBar.setTimeMillis(100);
        String title = data.getTitle();
        titleText.setText(title + "PK");
        pkChangCiId = data.getId();
        notifi();
    }



    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        freshView();
        if (App.musicBeatBO != null) {
            musicLayout.setVisibility(View.VISIBLE);
            musicText.setText(App.musicBeatBO.getMusicName());
        }
        setTimer();
        Cishu();
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

    @SingleClick
    @OnClick({R.id.ll_back_fragment_train_plan,
            R.id.ll_setting_fragment_train_plan,
            R.id.ll_record_model_fragment_train_plan,
            R.id.tv_contral_fragment_train_plan,
            R.id.music_layout,
            R.id.iv_fresh_fragment_train_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_train_plan:
                finish();
                break;
            case R.id.ll_setting_fragment_train_plan:
            case R.id.music_layout:
                start(RopeSkipSettingFragment.newInstance(null));
                break;
            case R.id.ll_record_model_fragment_train_plan:
                connectState = !connectState;
                freshView();
                break;
            case R.id.tv_contral_fragment_train_plan:
                if (!App.isConnect()) {
                    showToast("请先连接跳绳设备！");
                    return;
                }
                if (SyncHistoryUtils.isSync) {
                    ToastUtil.shortShow("数据同步中，请稍后");
                    return;
                }
                if (testState) {//结束
                    sendPk();
                    testState = false;
                    timer.cancel();
                    firstTiaoShengNum = Integer.MAX_VALUE;
                    tvContral.setText("开始");
                } else {//未开始
                    startMusic();
                    testState = true;
                    timeCount = 0;
                    String time = Utils.timeToString(timeCount);
                    tvTimeSecond.setText(time);
                    tvContral.setText("结束");
                    countdownBar.setProgress(0);
                    timer.start();
                }
                break;
            case R.id.iv_fresh_fragment_train_main:
                if (testState) {
                    showToast("正在跳绳中...");
                    return;
                }
                finish();
                break;
        }
    }


    private void setTimer() {
        timer = new CountDownTimer(data.getTimeOut() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeCount++;
                String time = Utils.timeToString(timeCount);
                tvTimeSecond.setText(time);
                Cishu();
            }

            @Override
            public void onFinish() {
                if(timeCount < data.getTimeOut()){
                    timeCount++;
                    String time = Utils.timeToString(timeCount);
                    tvTimeSecond.setText(time);
                }
                onViewClicked(tvContral);
            }
        };
    }


    /**
     * 开始PK
     */
    private void sendPk() {
        tvContral.setEnabled(false);
        Map<String, String> params = new HashMap<>();
        params.put("skipNum", skipNum + "");
        params.put("skipTime", timeCount + "");
        params.put("breakNum", 0 + "");
        params.put("finishStatus", 1 + "");
        WebSocketUtils utils = WebSocketUtils.getInstance();
        utils.sendMsg(new Gson().toJson(params));

        showToast("正在等待PK结果，请稍后...");
    }


    /**
     * 设置长连接监听
     */
    private void notifi() {
        WebSocketUtils.getInstance().setOnNotifiListener(new WebSocketUtils.onNotifiListener() {
            @Override
            public void onNotifi(String message) {
                try {
                    BaseResult<PkResultBO> userBO = new Gson().fromJson(message, new TypeToken<BaseResult<PkResultBO>>() {
                    }.getType());
                    if (userBO.getCode() == 300) {   //胜利
                        stopPk();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 0);
                        bundle.putSerializable("data", userBO.getData());
                        gotoActivity(PKResultActivity.class, bundle, true);
                    }
                    if (userBO.getCode() == 302) {   //失败
                        stopPk();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 1);
                        bundle.putSerializable("data", userBO.getData());
                        gotoActivity(PKResultActivity.class, bundle, true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    /**
     * 结束PK
     */
    private void stopPk() {
        //开始匹配
        Map<String, Object> params = new HashMap<>();
        params.put("pkChallengeId", pkChangCiId);
        params.put("contentText", 4);
        WebSocketUtils utils = WebSocketUtils.getInstance();
        utils.sendMsg(new Gson().toJson(params));
    }


    Player player;

    /**
     * 播放音乐
     */
    private void startMusic() {
        if (App.musicBeatBO != null && !StringUtils.isEmpty(App.musicBeatBO.getMusicUrl())) {
            if (player != null) {
                player.stop();
                player = null;
            }
            player = new Player();
            player.playUrl(App.musicBeatBO.getMusicUrl());
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.stop();
        }
        if (timer != null) {
            timer.cancel();
        }
        WebSocketUtils.getInstance().setOnNotifiListener(null);
        super.onDestroy();
    }


    /**
     * 连接蓝牙
     */
    @OnClick(R.id.blue_status_layout)
    public void clickBlue() {
        gotoActivity(SearchActivty.class, false);
    }


    /**
     * 获取跳绳次数
     */
    private void Cishu() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x18;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createTodayFrequencyCmd().getCmdByte());
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
        } else {
            tvConnectState.setText("已断开");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
        BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
        if (UartService.COUNT_OPENTION == 0x18) { //跳绳次数
            int cishu = Math.abs(ByteUtils.bytesToInt2(builder.getDataBody(), 0));
            if (firstTiaoShengNum == Integer.MAX_VALUE) {
                firstTiaoShengNum = cishu;
                onViewClicked(tvContral);
                return;
            }
            skipNum = cishu - firstTiaoShengNum;
            if (skipNum == maxNum) {
                tvTimeCountFragmentTrainMain.setText("0");
                countdownBar.setProgress(100);
            } else {
                tvTimeCountFragmentTrainMain.setText((maxNum - skipNum) + "");
                countdownBar.setProgress(skipNum / (float) maxNum * 100);
            }
            if (maxNum - skipNum <= 0) {   //倒计数结束
                onViewClicked(tvContral);
            }
        }
    }

}
