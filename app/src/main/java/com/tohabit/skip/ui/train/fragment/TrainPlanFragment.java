package com.tohabit.skip.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.algorithm.skipevaluation.Evaluator;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.pojo.po.MusicBO;
import com.tohabit.skip.presenter.CommonPresenter;
import com.tohabit.skip.presenter.contract.CommonContract;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.SearchActivty;
import com.tohabit.skip.ui.train.music.Player;
import com.tohabit.skip.utils.Example;
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


/**
 * @version V1.0
 * 训练计划页面
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
    @BindView(R.id.music_text)
    AppCompatTextView musicText;
    @BindView(R.id.music_layout)
    LinearLayout musicLayout;
    @BindView(R.id.countdown_bar)
    CircleProgressbar countdownBar;
    /**
     * 播放的音乐
     */
    public static MusicBO musicBO;

    /**
     * 使用的节拍
     */
    public static String beat;

    private boolean connectState;
    private boolean testState;
    private int timeCount;

    //训练id
    private String trainPlanId;

    /**
     * 跳绳次数
     */
    private int skipNum = 0;

    /**
     * 默认获取的第一次跳绳数量
     */
    private int firstTiaoShengNum = Integer.MAX_VALUE;

    /**
     * 倒计时时长
     */
    private int trainLength;


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

        trainPlanId = getArguments().getString("id", "");
        trainLength = Integer.parseInt(getArguments().getString("trainLength", "0"));
        timeCount = trainLength * 60;  //转换为秒数
        tvTimeSecond.setText(Utils.timeToString(timeCount));
        countdownBar.setTimeMillis(timeCount * 1000);
        countdownBar.setCountdownProgressListener(0, new CircleProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, float progress) {
                timeCount--;
                String time = Utils.timeToString(timeCount);
                tvTimeSecond.setText(time);
                Cishu();
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        freshView();
        getDeviceQc();
        if (musicBO != null) {
            musicLayout.setVisibility(View.VISIBLE);
            musicText.setText(musicBO.getName());
        }
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
            R.id.rl_start_fragment_train_plan,
            R.id.music_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_train_plan:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.ll_setting_fragment_train_plan:
            case R.id.music_layout:
                start(RopeSkipSettingFragment.newInstance(null));
                break;
            case R.id.ll_record_model_fragment_train_plan:
                connectState = !connectState;
                freshView();
                break;
            case R.id.rl_start_fragment_train_plan:
                if (testState) {//开始
                    updateData();
                    testState = false;
                    timeCount = trainLength * 60;
                    countdownBar.stop();
                    String time = Utils.timeToString(timeCount);
                    tvTimeSecond.setText(time);
                    tvContral.setText("开始");
                } else {//未开始
                    startMusic();
                    testState = true;
                    tvContral.setText("结束");
                    firstTiaoShengNum = Integer.MAX_VALUE;
                    countdownBar.start();

                }
                break;
        }
    }


    /**
     * 上报训练结果，生成综合得分
     */
    private void updateData() {
        Example example = new Example(getActivity().getAssets(), 0, skipNum, timeCount);
        Evaluator evaluator = example.getData();
        Map<String, Object> params = new HashMap<>();
        params.put("actionScore", evaluator.getRopeSwingingScore());//动作分数
        params.put("breakNum", 0);   //断绳数量
        params.put("coordinateScore", evaluator.getCoordinationScore()); //协调分数
        params.put("enduranceScore", evaluator.getEnduranceScore());  //耐力得分
        params.put("rhythmScore", evaluator.getSpeedStabilityScore());  //节奏得分
        params.put("skipNum", skipNum);  //跳绳次数
        params.put("skipTime", timeCount);
        params.put("stableScore", evaluator.getPositionStabilityScore());
        params.put("backgroundMusicId", musicBO == null ? null : musicBO.getId());
        params.put("beat", beat);
        params.put("trainPlanId", trainPlanId);   //训练id
        showProgress(null);
        HttpServerImpl.addTrain(params).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Bundle bundle = new Bundle();
                bundle.putString("id", trainPlanId);
                start(RopeSkipResultFragment.newInstance(bundle));
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    Player player;

    /**
     * 播放音乐
     */
    private void startMusic() {
        if (musicBO != null) {
            if (player != null) {
                player.stop();
                player = null;
            }
            player = new Player();
            player.playUrl(musicBO.getUrl());
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (player != null) {
            player.pause();
        }
        testState = false;
        timeCount = trainLength * 60;
        String time = Utils.timeToString(timeCount);
        tvTimeSecond.setText(time);
        if (countdownBar != null) {
            countdownBar.setProgress(100);
            countdownBar.stop();
        }
        tvContral.setText("开始");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        musicBO = null;
        beat = null;
        if (player != null) {
            player.stop();
        }
        if (countdownBar != null) {
            countdownBar.stop();
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
    private void Cishu() {
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
            //String.valueOf(builder.getDataBody()[1]
            String dianliang = String.valueOf(builder.getDataBody()[0]);
            tvBattery.setText(dianliang + "%");
        }
        if (UartService.COUNT_OPENTION == 0x22) { //跳绳次数
            int cishu = Math.abs(builder.getDataBody()[builder.getDataBody().length - 1]);
            if (firstTiaoShengNum == Integer.MAX_VALUE) {
                firstTiaoShengNum = cishu;
            }
            skipNum = cishu - firstTiaoShengNum;
            tvTimeCountFragmentTrainMain.setText(skipNum + "");
        }
    }

}
