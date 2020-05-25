package com.habit.star.ui.train.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.event.model.BlueDataEvent;
import com.habit.star.event.model.BlueEvent;
import com.habit.star.pojo.po.TestBO;
import com.habit.star.pojo.po.TestDataBO;
import com.habit.star.service.UartService;
import com.habit.star.ui.SearchActivty;
import com.habit.star.ui.train.activity.TainMainActivity;
import com.habit.star.ui.train.adapter.TranRecordListAdapter;
import com.habit.star.ui.train.contract.TranHomeContract;
import com.habit.star.ui.train.presenter.TranHomePresenter;
import com.habit.star.ui.young.fragment.StatisticsActivity;
import com.habit.star.utils.ToastUtil;
import com.habit.star.utils.Utils;
import com.habit.star.utils.blue.cmd.BleCmd;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @version V1.0
 * @date: 2020-02-10 16:56
 * @ClassName: TranHomeFragment.java
 * @Description:训练首页
 * @author: sundongdong
 */

public class TranHomeFragment extends BaseFragment<TranHomePresenter> implements TranHomeContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.tv_battery_fragment_train_main)
    AppCompatTextView tvBattery;
    @BindView(R.id.ll_sd_input_fragment_train_main)
    LinearLayout llSdInput;
    @BindView(R.id.toolbar_layout_toolbar)
    LinearLayout toolbarLayoutToolbar;
    @BindView(R.id.iv_fresh_fragment_train_main)
    AppCompatImageView ivFresh;
    @BindView(R.id.tv_blue_connect_statusfragment_train_main)
    AppCompatTextView tvBlueConnectStatus;
    @BindView(R.id.tv_time_count_fragment_train_main)
    AppCompatTextView tvTimeCount;
    @BindView(R.id.rl_count_fragment_train_main)
    FrameLayout rlCount;
    @BindView(R.id.tv_tj_fragment_train_main)
    LinearLayout tvTj;
    @BindView(R.id.rv_test_record_fragment_train_main)
    RecyclerView rvTestRecord;
    @BindView(R.id.iv_start_test_fragment_train_main)
    AppCompatImageView ivStartTest;
    @BindView(R.id.tv_test_time_train)
    AppCompatTextView tvTime;
    @BindView(R.id.blue_state_img)
    AppCompatImageView blueStateImg;
    Unbinder unbinder;
    @BindView(R.id.leiji_fenzhong)
    AppCompatTextView leijiFenzhong;
    @BindView(R.id.leiji_day)
    AppCompatTextView leijiDay;
    @BindView(R.id.tiaoshengzongshu)
    AppCompatTextView tiaoshengzongshu;
    @BindView(R.id.xunlian_zongshu)
    AppCompatTextView xunlianZongshu;
    Unbinder unbinder1;

    private TranRecordListAdapter mRecordListAdapter;

    private final static int COUNT = 1;
    private boolean testState;
    Timer timer;
    private int timeCount;

    /**
     * 默认获取的第一次跳绳数量
     */
    private int firstTiaoShengNum = Integer.MAX_VALUE;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT:
                    timeCount++;
                    String time = Utils.timeToString(timeCount);
                    tvTime.setText("时间  " + time);
//                    tvTimeCount.setText(timeCount * 2 < 10 ? ("0" + timeCount * 2) : (timeCount * 2 + ""));
                    mPresenter.getTiaoshenCishu();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public static TranHomeFragment newInstance(Bundle bundle) {
        TranHomeFragment fragment = new TranHomeFragment();
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
        return R.layout.fragment_train_main;
    }

    @Override
    protected String getLogTag() {
        return "TranHomeFragment %s";
    }

    @Override
    protected void initEventAndData() {
        tvTimeCount.setTypeface(App.getInstance().tf);
        initAdapter();
        mPresenter.getRecodList();
        if (App.blueService == null || App.blueService.getConnectionState() == UartService.STATE_DISCONNECTED) {
            tvBlueConnectStatus.setText("已断开");
            blueStateImg.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        } else {
            if (App.blueService.getConnectionState() == UartService.STATE_CONNECTING) {
                tvBlueConnectStatus.setText("设备连接中...");
                blueStateImg.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
            } else {
                tvBlueConnectStatus.setText("已连接");
                blueStateImg.setBackgroundResource(R.mipmap.ic_home19);
                mPresenter.getDeviceQC();
            }
        }
    }


    private void initAdapter() {
        rvTestRecord.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_address).colorResId(R.color.transparent).build());
        rvTestRecord.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecordListAdapter = new TranRecordListAdapter(mContext);
        rvTestRecord.setAdapter(mRecordListAdapter);
        rvTestRecord.addOnItemTouchListener(new OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_look_layout_fragment_train_record_list_item:
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(RouterConstants.KEY_STRING, mRecordListAdapter.getItem(position).getId() + "");
                        intent.putExtra(RouterConstants.ARG_BUNDLE, bundle);
                        intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.TEST_RESULT);
                        intent.setClass(_mActivity, TainMainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueEvent event) {
        if (event.isConnect == UartService.STATE_CONNECTED) {
            tvBlueConnectStatus.setText("已连接");
            blueStateImg.setBackgroundResource(R.mipmap.ic_home19);
        } else if (event.isConnect == UartService.STATE_CONNECTING) {
            tvBlueConnectStatus.setText("设备连接中...");
            blueStateImg.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        } else if (event.isConnect == UartService.NITIFI_SOURESS) {  //监听已经开始建立
            mPresenter.getDeviceQC();
        } else {
            tvBlueConnectStatus.setText("已断开");
            blueStateImg.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);

        }
    }


    @Override
    public void setRecordList(List<TestBO> data) {
        mRecordListAdapter.setNewData(data);
    }

    @Override
    public void getDeviceQcAndType(String dianliang, String type) {
        tvBattery.setText(dianliang + "%");
    }

    @Override
    public void getDeviceCishu(String cichu) {
        tvTimeCount.setText(cichu);
    }


    @Override
    public void getTestData(TestDataBO dataBO) {
        leijiFenzhong.setText(String.valueOf(dataBO.getTotalMinute()));
        leijiDay.setText(String.valueOf(dataBO.getTotalDay()));
        tiaoshengzongshu.setText(String.valueOf(dataBO.getSkipTotalNum()));
        xunlianZongshu.setText(String.valueOf(dataBO.getTestTotalNum()));
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
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @SingleClick
    @OnClick({R.id.tv_battery_fragment_train_main,
            R.id.ll_sd_input_fragment_train_main,
            R.id.tv_tj_fragment_train_main,
            R.id.iv_start_test_fragment_train_main,
            R.id.iv_fresh_fragment_train_main})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_start_test_fragment_train_main:
                if (testState) {//开始
                    testState = false;
                    timeCount = 0;
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    String time = Utils.timeToString(timeCount);
                    tvTime.setText("时间  " + time);
                    ivStartTest.setBackgroundResource(R.mipmap.ic_home8);
                    intent = new Intent();
                    intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.TEST_RESULT);
                    intent.setClass(_mActivity, TainMainActivity.class);
                    startActivity(intent);
                } else {//未开始
                    testState = true;
                    ivStartTest.setBackgroundResource(R.mipmap.ic_finish_test);
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
            case R.id.tv_tj_fragment_train_main:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.ENERGY_VALUE);
//                intent.setClass(_mActivity, TainMainActivity.class);
//                startActivity(intent);
                gotoActivity(StatisticsActivity.class,false);
                break;
            case R.id.tv_battery_fragment_train_main:
                break;
            case R.id.ll_sd_input_fragment_train_main:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.BASE_MSG_INPUT);
                intent.setClass(_mActivity, TainMainActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_fresh_fragment_train_main:
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


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getDeviceQC();
        mPresenter.getTestTotal();
        mPresenter.getTestList();
    }

}
