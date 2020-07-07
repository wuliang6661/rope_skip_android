package com.tohabit.skip.ui.train.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.algorithm.skipevaluation.Evaluator;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.pojo.po.TestBO;
import com.tohabit.skip.pojo.po.TestDataBO;
import com.tohabit.skip.pojo.vo.BleYundongMsg;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.SearchActivty;
import com.tohabit.skip.ui.train.activity.TainMainActivity;
import com.tohabit.skip.ui.train.adapter.TranRecordListAdapter;
import com.tohabit.skip.ui.train.contract.TranHomeContract;
import com.tohabit.skip.ui.train.music.Player;
import com.tohabit.skip.ui.train.presenter.TranHomePresenter;
import com.tohabit.skip.ui.young.fragment.StatisticsActivity;
import com.tohabit.skip.utils.ByteUtils;
import com.tohabit.skip.utils.Example;
import com.tohabit.skip.utils.StringUtils;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.Utils;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;
import com.tohabit.skip.utils.blue.model.BleData;
import com.tohabit.skip.utils.blue.model.BlePoint;
import com.tohabit.skip.utils.blue.model.BleSport;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.lang.Math.max;


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

    /**
     * 是否填写过基本信息
     */
    public static boolean isEditMsg = false;

    /**
     * 跳绳次数
     */
    private int skipNum = 0;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT:
                    timeCount++;
                    String time = Utils.timeToString(timeCount);
                    tvTime.setText("时间  " + time);
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
//            mPresenter.getDeviceQC();
        } else {
            tvBlueConnectStatus.setText("已断开");
            blueStateImg.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
            tvBattery.setText("--");
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


    /**
     * 一次运动轨迹的数据长度
     */
    private int pointDataLength;

    /**
     * 当前运动详情的数据
     */
    private BleYundongMsg yundongMsg;

    private List<BlePoint> blePoints = new ArrayList<BlePoint>();   //存储ble获取的坐标点

    private int muluCount;

    private long dateTime;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
        BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
        if (UartService.COUNT_OPENTION == 0x11) {  //电量
            getDeviceQcAndType(String.valueOf(builder.getDataBody()[0]), String.valueOf(builder.getDataBody()[1]));
        }
        if (UartService.COUNT_OPENTION == 0x22) { //跳绳次数
            int cishu = Math.abs(ByteUtils.bytesToInt2(builder.getDataBody(), 0));
//            if (firstTiaoShengNum == Integer.MAX_VALUE) {
//                firstTiaoShengNum = cishu;
//            }
            skipNum = cishu;
            getDeviceCishu(String.valueOf(Math.abs(skipNum)));
        }
        if (UartService.COUNT_OPENTION == 0x10) {  //目录数
            byte[] changdu = new byte[]{builder.getDataBody()[0], builder.getDataBody()[1]};
            muluCount = ByteUtils.bytesToInt(changdu);
            LogUtils.e("获取的目录条数：" + muluCount);
            if (muluCount > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMuLuMessage(muluCount - 1);
                    }
                }, 500);
            } else {
                addTest();
            }
        }
        if (UartService.COUNT_OPENTION == 0x12) {  //目录内容
            BleSport bleSport = new BleSport(builder.getDataBody());
            dateTime = bleSport.getStart_time();
            long dateEndTime = bleSport.getEnd_time();
            int duration = max(1, (int) (dateEndTime - dateTime));
            pointDataLength = bleSport.getFrameLength();
            LogUtils.e("获取的跳绳时长=" + duration + "跳绳轨迹的数据长度 = " + pointDataLength);
            byte[] skipNumByte = new byte[]{builder.getDataBody()[13], builder.getDataBody()[14]};
            byte[] breakNumByte = new byte[]{builder.getDataBody()[15], builder.getDataBody()[16]};
            int skipNum = ByteUtils.bytesToInt(skipNumByte);
            int breakNum = ByteUtils.bytesToInt(breakNumByte);
            LogUtils.e("获取的跳绳次数：" + skipNum + "获取的断绳次数：" + breakNum);
            yundongMsg = new BleYundongMsg(duration, skipNum, breakNum, dateTime);
//            getYundongMsg(dateTime, muluCount - 1);
            addTest();
        }
        if (UartService.COUNT_OPENTION == 0x13) {  //跳绳轨迹分包数据
            BleData bleData = new BleData(event.getData(), pointDataLength);
            blePoints.addAll(bleData.getBlePointList());//把所有解析出来的坐标点保存起来
            if (bleData.isLastPage() && blePoints.size() > 0) {//如果是最后一包，说明此次运动数据已经取完，删除
                Log.d("chen", "接收到跳绳数据。");
                Log.d("chen", "圈序号：" + blePoints.get(0).getNumber() + "-" + blePoints.get(blePoints.size() - 1).getNumber());
                Log.d("chen", "共 " + blePoints.size() + " 个采样点");
                stopProgress();
                addTest();
            }
        }
    }


    /**
     * 获取所有目录
     */
    private void getAllMuLu() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x10;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createAllSportRecordCmd().getCmdByte());
        }
    }


    /**
     * 获取目录内容
     */
    private void getMuLuMessage(int muluCount) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x12;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createSportInfoCmd((short) muluCount).getCmdByte());
        }
    }


    /**
     * 获取运动分包数据
     */
    private void getYundongMsg(long date, int baoxuhao) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x13;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetPointCmd(date, baoxuhao).getCmdByte());
        }
    }

    /**
     * 删除一次运动
     */
    private void deleteYunDong(long date) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x14;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createDeleteSportCmd(date).getCmdByte());
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
                    if (player != null) {
                        player.stop();
                    }
//                    addTest();
                    getAllMuLu();
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                } else {//未开始
                    if (!App.isConnect()) {
                        showToast("请先连接跳绳！");
                        return;
                    }
//                    if (!isEditMsg) {
//                        intent = new Intent();
//                        intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.BASE_MSG_INPUT);
//                        intent.setClass(_mActivity, TainMainActivity.class);
//                        startActivity(intent);
//                        return;
//                    }
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
                    startMusic();
                }
                break;
            case R.id.tv_tj_fragment_train_main:
                gotoActivity(StatisticsActivity.class, false);
                break;
            case R.id.tv_battery_fragment_train_main:
                break;
            case R.id.ll_sd_input_fragment_train_main:
                gotoActivity(InputActivity.class, false);
                break;
            case R.id.iv_fresh_fragment_train_main:
                if (testState) {
                    showToast("正在跳绳中...");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("trainLength", "60");  //60秒
                bundle.putInt("type", 1);
                Intent intent1 = new Intent();
                intent1.putExtra(RouterConstants.ARG_MODE, RouterConstants.ROPE_SKIP_RESULTS);
                intent1.putExtras(bundle);
                intent1.setClass(getActivity(), TainMainActivity.class);
                startActivity(intent1);
                break;
        }
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


    /**
     * 添加测试结果
     */
    private void addTest() {
        Example example = null;
        if (yundongMsg == null) {
            example = new Example(getActivity().getAssets(), 0, skipNum,
                    timeCount);
        } else {
            example = new Example(getActivity().getAssets(), yundongMsg.getBreakNum(), yundongMsg.getSkipNum(),
                    yundongMsg.getTimeCount());
        }
        Evaluator evaluator = example.getData();
        Map<String, Object> params = new HashMap<>();
        params.put("actionScore", evaluator.getRopeSwingingScore());//动作分数
        params.put("breakNum", 0);   //断绳数量
        params.put("coordinateScore", evaluator.getCoordinationScore()); //协调分数
        params.put("enduranceScore", evaluator.getEnduranceScore());  //耐力得分
        params.put("rhythmScore", evaluator.getSpeedStabilityScore());  //节奏得分
        params.put("skipNum", yundongMsg == null ? skipNum : yundongMsg.getSkipNum());  //跳绳次数
        params.put("skipTime", yundongMsg == null ? timeCount : yundongMsg.getTimeCount());
        params.put("stableScore", evaluator.getPositionStabilityScore());
        params.put("deviceId", null);  //todo 设备id，暂时缺失
        params.put("skipDate", TimeUtils.getNowString());
        showProgress(null);
        HttpServerImpl.addTest(params).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                showToast("已生成测试记录！");
                yundongMsg = null;
                deleteYunDong(dateTime);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(RouterConstants.KEY_STRING, s);
                intent.putExtra(RouterConstants.ARG_BUNDLE, bundle);
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.TEST_RESULT);
                intent.setClass(_mActivity, TainMainActivity.class);
                startActivity(intent);
                testState = false;
                timeCount = 0;
                String time = Utils.timeToString(timeCount);
                tvTime.setText("时间  " + time);
                ivStartTest.setBackgroundResource(R.mipmap.start_img);
                isEditMsg = false;
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
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
        if (isEditMsg) {
            ivStartTest.setBackgroundResource(R.mipmap.start_img);
        }
    }

}
