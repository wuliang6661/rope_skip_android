package com.tohabit.skip.ui;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.algorithm.skipevaluation.Evaluator;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.event.model.CancleEvent;
import com.tohabit.skip.pojo.po.DeviceBO;
import com.tohabit.skip.pojo.vo.BleYundongMsg;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.utils.ByteUtils;
import com.tohabit.skip.utils.Example;
import com.tohabit.skip.utils.blue.OnSearchListenter;
import com.tohabit.skip.utils.blue.btutil.BlueDeviceUtils;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;
import com.tohabit.skip.utils.blue.model.BleData;
import com.tohabit.skip.utils.blue.model.BlePoint;
import com.tohabit.skip.utils.blue.model.BleSport;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static java.lang.Math.max;

public class SearchActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    List<BluetoothDevice> devices;

    private List<DeviceBO> deviceBOS;
    private String deviceId;

    /**
     * 当前正在获取的目录序号
     */
    private int selectPosition = 0;

    /**
     * 总共的目录条数
     */
    private int muluCount = 0;

    /**
     * 删除的开始时间
     */
    private long startUTC;

    /**
     * 删除的结束时间
     */
    private long endUTC;

    /**
     * 一次运动轨迹的数据长度
     */
    private int pointDataLength;

    /**
     * 当前运动详情的数据
     */
    private BleYundongMsg yundongMsg;

    /**
     * 所有的历史数据
     */
    private List<Map<String, Object>> list = new ArrayList<>();


    private List<BlePoint> blePoints = new ArrayList<BlePoint>();   //存储ble获取的坐标点

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_search_blue;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("蓝牙搜索中...");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE).build());
        devices = new ArrayList<>();
        initDeviceBlue();
        getData();
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
     * 本机蓝牙搜索
     */
    private void initDeviceBlue() {
        BlueDeviceUtils blueDeviceUtils = BlueDeviceUtils.getInstance();
        if (App.connectDevice != null) {   //如果当前已连接设备 ，则显示该设备
            devices.add(App.connectDevice);
            setAdapter();
        }
        blueDeviceUtils.setListener(new OnSearchListenter() {

            @Override
            public void searchStart() {

            }

            @Override
            public void searchDevices(BluetoothDevice device) {
                for (BluetoothDevice item : devices) {   //避免搜索到重复的设备
                    if (item.getAddress().equals(device.getAddress())) {
                        return;
                    }
                }
                if (device.getName().startsWith("TH") || device.getName().startsWith("XS")) {
                    devices.add(device);
                    setAdapter();
                }
            }

            @Override
            public void searchStop() {
                blueDeviceUtils.startScanBluth();
            }
        });
        blueDeviceUtils.startScanBluth();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<BluetoothDevice> adapter = new LGRecycleViewAdapter<BluetoothDevice>(devices) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blue;
            }

            @Override
            public void convert(LGViewHolder holder, BluetoothDevice result, int position) {
                holder.setText(R.id.item_text, "绳柄名称：" + result.getName());
                holder.setText(R.id.mac_text, "蓝牙名称：" + result.getAddress());
                holder.setText(R.id.connect, "绑定");
                if (App.connectDevice != null && App.connectDevice.getAddress().equals(result.getAddress())) {
//                    holder.getView(R.id.connect).setEnabled(false);
                    holder.setText(R.id.connect, "已绑定");
                } else {
                    if (deviceBOS != null) {
//                        for (DeviceBO deviceBO : deviceBOS) {
//                            if (deviceBO.getMacAddress().equals(result.getAddress())) {
//                                holder.getView(R.id.connect).setEnabled(true);
//                            }
//                        }
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.connect, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (App.connectDevice != null && App.connectDevice.getAddress().equals(devices.get(position).getAddress())) {
                    EventBus.getDefault().post(new CancleEvent());
                    App.connectDevice = null;
                    adapter.notifyDataSetChanged();
                } else {
                    EventBus.getDefault().post(new CancleEvent());
                    showProgress("蓝牙连接中...");
                    EventBus.getDefault().post(devices.get(position));
                }
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueEvent event) {
        stopProgress();
        if (event.isConnect == UartService.STATE_CONNECTED) {
            setAdapter();
            saveDevices();
        } else if (event.isConnect == UartService.STATE_CONNECTING) {
        } else if (event.isConnect == UartService.NITIFI_SOURESS) {  //监听已经开始建立
            showProgress("同步跳绳历史数据中...");
            tongbuTime();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getAllMuLu();
                }
            }, 500);
        } else {
        }
    }

    /**
     * 获取所有目录
     */
    private void getAllMuLu() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x33;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createAllSportRecordCmd().getCmdByte());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
        BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
        if (UartService.COUNT_OPENTION == 0x33) {  //目录数
            byte[] changdu = new byte[]{builder.getDataBody()[0], builder.getDataBody()[1]};
            muluCount = ByteUtils.bytesToInt(changdu);
            LogUtils.e("获取的目录条数：" + muluCount);
//            showToast("获取的历史目录条数：" + muluCount);
            if (muluCount > 0) {
                selectPosition = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMuLuMessage(selectPosition);
                    }
                }, 500);
            } else {
                stopProgress();
            }
        }
        if (UartService.COUNT_OPENTION == 0x44) {  //目录内容
            BleSport bleSport = new BleSport(builder.getDataBody());
            long dateTime = bleSport.getStart_time();
            long dateEndTime = bleSport.getEnd_time();
            int duration = max(1, (int) (dateEndTime - dateTime));
            pointDataLength = bleSport.getFrameLength();
            if (selectPosition == 0) {
                startUTC = dateTime;
            }
            if (selectPosition == muluCount - 1) {
                endUTC = dateEndTime;
            }
            LogUtils.e("获取的跳绳时长=" + duration + "跳绳轨迹的数据长度 = " + pointDataLength);
            byte[] skipNumByte = new byte[]{builder.getDataBody()[13], builder.getDataBody()[14]};
            byte[] breakNumByte = new byte[]{builder.getDataBody()[15], builder.getDataBody()[16]};
            int skipNum = ByteUtils.bytesToInt(skipNumByte);
            int breakNum = ByteUtils.bytesToInt(breakNumByte);
            LogUtils.e("获取的跳绳次数：" + skipNum + "获取的断绳次数：" + breakNum);
            yundongMsg = new BleYundongMsg(duration, skipNum, breakNum, dateTime);
            getYundongMsg(dateTime, selectPosition);
        }
        if (UartService.COUNT_OPENTION == 0x77) {  //跳绳轨迹分包数据
            BleData bleData = new BleData(event.getData(), pointDataLength);
            blePoints.addAll(bleData.getBlePointList());//把所有解析出来的坐标点保存起来
            if (bleData.isLastPage() && blePoints.size() > 0) {//如果是最后一包，说明此次运动数据已经取完，删除
                Log.d("chen", "接收到跳绳数据。");
                Log.d("chen", "圈序号：" + blePoints.get(0).getNumber() + "-" + blePoints.get(blePoints.size() - 1).getNumber());
                Log.d("chen", "共 " + blePoints.size() + " 个采样点");
                save();
                if (selectPosition < muluCount - 1) {
                    selectPosition++;
                    getMuLuMessage(selectPosition);
                } else {
                    stopProgress();
                    showToast("同步完成！");
                    addTest();
                }
            }
        }
    }


    /**
     * 获取目录内容
     */
    private void getMuLuMessage(int muluCount) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x44;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createSportInfoCmd((short) muluCount).getCmdByte());
        }
    }

    /**
     * 同步时间
     */
    private void tongbuTime() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x55;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createSynTimeCmd().getCmdByte());
        }
    }

    /**
     * 删除所有目录
     */
    private void deleteAll() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x66;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createBatDeleteSportCmd(startUTC, endUTC).getCmdByte());
        }
    }

    /**
     * 获取运动分包数据
     */
    private void getYundongMsg(long date, int baoxuhao) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x77;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetPointCmd(date, baoxuhao).getCmdByte());
        }
    }


    /**
     * 保存一个目录的结果
     */
    private void save() {
        Example example = new Example(getAssets(), yundongMsg.getBreakNum(), yundongMsg.getSkipNum(),
                yundongMsg.getTimeCount(), blePoints);
        Evaluator evaluator = example.getData();
        Map<String, Object> params = new HashMap<>();
        params.put("actionScore", evaluator.getRopeSwingingScore());//动作分数
        params.put("breakNum", yundongMsg.getBreakNum());   //断绳数量
        params.put("coordinateScore", evaluator.getCoordinationScore()); //协调分数
        params.put("enduranceScore", evaluator.getEnduranceScore());  //耐力得分
        params.put("rhythmScore", evaluator.getSpeedStabilityScore());  //节奏得分
        params.put("skipNum", yundongMsg.getSkipNum());  //跳绳次数
        params.put("skipTime", yundongMsg.getTimeCount());
        params.put("stableScore", evaluator.getPositionStabilityScore());
        params.put("deviceId", deviceId);
        params.put("skipDate", TimeUtils.millis2String(yundongMsg.getTime() * 1000));
        list.add(params);
    }


    /**
     * 添加测试结果
     */
    private void addTest() {
        HttpServerImpl.addTestBatch(list).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                deleteAll();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 保存设备
     */
    private void saveDevices() {
        HttpServerImpl.saveDevices(0, App.connectDevice.getName(), App.connectDevice.getAddress()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                deviceId = s;
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlueDeviceUtils deviceUtils = BlueDeviceUtils.getInstance();
        deviceUtils.onDestory();
    }

    private void getData() {
        HttpServerImpl.getDeviceList().subscribe(new HttpResultSubscriber<List<DeviceBO>>() {
            @Override
            public void onSuccess(List<DeviceBO> s) {
                deviceBOS = s;
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


}
