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
import com.habit.commonlibrary.widget.CountDownProgressBar;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.base.BaseFragment;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.utils.ToastUtil;
import com.habit.star.utils.Utils;

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
    @BindView(R.id.ll_connect_state_fragment_train_plan)
    LinearLayout llConnectState;
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


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT:
                    timeCount++;
                    String time = Utils.timeToString(timeCount);
                    tvTimeSecond.setText(time);
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
        initDialog();
        freshView();
    }

    private void freshView() {
        if (connectState) {
            tvConnectState.setText("已断开");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        } else {
            tvConnectState.setText("已连接");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_home19);
        }
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
//                    mCdProcess.startCountDown();
                    if (timer == null) {
                        timer = new Timer();
                    }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
