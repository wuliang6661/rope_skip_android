package com.habit.star.ui.train.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.base.BaseFragment;
import com.habit.star.event.model.BlueDataEvent;
import com.habit.star.event.model.BlueEvent;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.ui.SearchActivty;
import com.habit.star.utils.ToastUtil;
import com.habit.star.utils.Utils;
import com.habit.star.service.UartService;
import com.habit.star.utils.blue.cmd.BleCmd;
import com.habit.star.utils.blue.cmd.RequstBleCmd;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @version V1.0
 * @date: 2020-02-11 12:25
 * @ClassName: TrainPlanFragment.java
 * @Description:训练计划页面
 * @author: sundongdong
 */
public class TrainPlanFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.ll_back_fragment_train_plan)
    LinearLayout llBack;
    @BindView(R.id.ll_setting_fragment_train_plan)
    LinearLayout llSetting;
    @BindView(R.id.toolbar_layout_toolbar)
    LinearLayout toolbarLayoutToolbar;
    @BindView(R.id.tv_battery_fragment_train_plan)
    AppCompatTextView tvBattery;
    @BindView(R.id.ll_battery_fragment_train_plan)
    LinearLayout llBattery;
    @BindView(R.id.tv_connect_state_fragment_train_plan)
    AppCompatTextView tvConnectState;
    @BindView(R.id.iv_fresh_fragment_train_main)
    AppCompatImageView ivFreshFragmentTrainMain;
    @BindView(R.id.ll_record_model_fragment_train_plan)
    LinearLayout llRecordModel;
    @BindView(R.id.tv_time_second_fragment_train_plan)
    AppCompatTextView tvTimeSecond;
    @BindView(R.id.rl_count_fragment_train_main)
    FrameLayout rlCountFragmentTrainMain;
    @BindView(R.id.tv_time_count_fragment_train_main)
    AppCompatTextView tvTimeCountFragmentTrainMain;
    @BindView(R.id.tv_contral_fragment_train_plan)
    AppCompatTextView tvContral;
    @BindView(R.id.rl_start_fragment_train_plan)
    RelativeLayout rlStart;
    @BindView(R.id.iv_connnet_state_fragment_train_plan)
    AppCompatImageView ivConnnetState;
//    @BindView(R.id.cd_process_fragment_train_plan)
//    CountDownProgressBar mCdProcess;

    private final static int COUNT = 1;
    private boolean connectState;
    private boolean testState;
    Timer timer;
    private int timeCount;


    /**
     * 默认获取的第一次跳绳数量
     */
    private int firstTiaoShengNum = Integer.MAX_VALUE;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT:
                    timeCount++;
                    String time = Utils.timeToString(timeCount);
                    tvTimeSecond.setText(time);
                    Cishu();
                    break;
                default:
                    break;
            }
        }

        ;
    };


    public static TrainPlanFragment newInstance(Bundle bundle) {
        TrainPlanFragment fragment = new TrainPlanFragment();
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
        return R.layout.fragment_train_plan;
    }

    @Override
    protected String getLogTag() {
        return "TrainPlanFragment %s";
    }

    @Override
    protected void initEventAndData() {
        tvTimeCountFragmentTrainMain.setTypeface(App.getInstance().tf);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        freshView();
        getDeviceQc();
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
    @OnClick({R.id.ll_back_fragment_train_plan,
            R.id.ll_setting_fragment_train_plan,
            R.id.ll_record_model_fragment_train_plan,
            R.id.rl_start_fragment_train_plan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_train_plan:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.ll_setting_fragment_train_plan:
                start(RopeSkipSettingFragment.newInstance(null));
                break;
            case R.id.ll_record_model_fragment_train_plan:
                connectState = !connectState;
                freshView();
                break;
            case R.id.rl_start_fragment_train_plan:
                if (testState) {//开始
                    testState = false;
                    timeCount = 0;
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    String time = Utils.timeToString(timeCount);
                    tvTimeSecond.setText(time);
                    tvContral.setText("开始");
                    start(RopeSkipResultFragment.newInstance(null));
                } else {//未开始
                    testState = true;
                    tvContral.setText("结束");
                    if (timer == null) {
                        timer = new Timer();
                    }
                    firstTiaoShengNum = Integer.MAX_VALUE;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(COUNT);
                        }
                    }, 0, 1000);

                }
                break;
        }
    }


    /**
     * 连接蓝牙
     */
    @OnClick(R.id.blue_status_layout)
    public void clickBlue() {
        gotoActivity(SearchActivty.class, false);
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

    /**
     * 获取跳绳次数
     */
    private void Cishu(){
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x22;
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
            getDeviceQc();
        } else {
            tvConnectState.setText("已断开");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
        BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
        if (UartService.COUNT_OPENTION == 0x11) {  //电量
            getDeviceQcAndType(String.valueOf(builder.getDataBody()[0]), String.valueOf(builder.getDataBody()[1]));
        }
        if (UartService.COUNT_OPENTION == 0x22) { //跳绳次数
            int cishu = Math.abs(builder.getDataBody()[builder.getDataBody().length - 1]);
            if (firstTiaoShengNum == Integer.MAX_VALUE) {
                firstTiaoShengNum = cishu;
            }
            getDeviceCishu(String.valueOf(cishu - firstTiaoShengNum));
        }
    }


    public void getDeviceQcAndType(String dianliang, String type) {
        tvBattery.setText(dianliang + "%");
    }


    public void getDeviceCishu(String cichu) {
        tvTimeCountFragmentTrainMain.setText(cichu);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
