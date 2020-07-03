package com.tohabit.skip.ui;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.utils.ByteUtils;
import com.tohabit.skip.utils.Example;
import com.tohabit.skip.utils.blue.OnSearchListenter;
import com.tohabit.skip.utils.blue.btutil.BlueDeviceUtils;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;
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

public class SearchActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    List<BluetoothDevice> devices;

    private List<DeviceBO> deviceBOS;

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
            byte[] startTime = new byte[]{builder.getDataBody()[0], builder.getDataBody()[1],
                    builder.getDataBody()[2], builder.getDataBody()[3]};
            long dateTime = ByteUtils.bytesToInt2(startTime, 0);
            if (selectPosition == 0) {
                startUTC = dateTime;
            }
            byte[] endTime = new byte[]{builder.getDataBody()[4], builder.getDataBody()[5],
                    builder.getDataBody()[6], builder.getDataBody()[7]};
            long dateEndTime = ByteUtils.bytesToInt2(endTime, 0);
            if (selectPosition == muluCount - 1) {
                endUTC = dateEndTime;
            }
            LogUtils.e("获取的跳绳时长=" + (dateEndTime - dateTime));
            byte[] skipNumByte = new byte[]{builder.getDataBody()[13], builder.getDataBody()[14]};
            byte[] breakNumByte = new byte[]{builder.getDataBody()[15], builder.getDataBody()[16]};
            int skipNum = ByteUtils.bytesToInt(skipNumByte);
            int breakNum = ByteUtils.bytesToInt(breakNumByte);
            LogUtils.e("获取的跳绳次数：" + skipNum + "获取的断绳次数：" + breakNum);
//            getYundongMsg(dateTime);
            addTest(skipNum, breakNum, (int) (dateEndTime - dateTime), dateTime);
            if (selectPosition < muluCount - 1) {
                selectPosition++;
                getMuLuMessage(selectPosition);
            } else {
                stopProgress();
                showToast("同步完成！");
//                deleteAll();
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
    private void getYundongMsg(long date) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x77;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetPointCmd(date).getCmdByte());
        }
    }


    /**
     * 添加测试结果
     */
    private void addTest(int skipNum, int breakNum, int timeCount, long time) {
        Example example = new Example(getAssets(), breakNum, skipNum, timeCount);
        Evaluator evaluator = example.getData();
        Map<String, Object> params = new HashMap<>();
        params.put("actionScore", evaluator.getRopeSwingingScore());//动作分数
        params.put("breakNum", breakNum);   //断绳数量
        params.put("coordinateScore", evaluator.getCoordinationScore()); //协调分数
        params.put("enduranceScore", evaluator.getEnduranceScore());  //耐力得分
        params.put("rhythmScore", evaluator.getSpeedStabilityScore());  //节奏得分
        params.put("skipNum", skipNum);  //跳绳次数
        params.put("skipTime", timeCount);
        params.put("stableScore", evaluator.getPositionStabilityScore());
        params.put("deviceId", null);  //todo 设备id，暂时缺失
        params.put("skipDate", TimeUtils.millis2String(time * 1000));
        HttpServerImpl.addTest(params).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFiled(String message) {
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
