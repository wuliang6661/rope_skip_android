package com.tohabit.skip.utils;

import android.os.Handler;
import android.util.Log;

import com.algorithm.skipevaluation.Evaluator;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.SyncSuressEvent;
import com.tohabit.skip.pojo.vo.BleYundongMsg;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;
import com.tohabit.skip.utils.blue.model.BleData;
import com.tohabit.skip.utils.blue.model.BlePoint;
import com.tohabit.skip.utils.blue.model.BleSport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class SyncHistoryUtils {

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

    /**
     * 同步状态
     */
    public static boolean isSync = false;


    private List<BlePoint> blePoints = new ArrayList<BlePoint>();   //存储ble获取的坐标点

    private static SyncHistoryUtils utils;

    //上一次的目录内容
    private byte[] muluMsg;

    public static SyncHistoryUtils getInstance(String deviceId) {
        if (utils == null) {
            utils = new SyncHistoryUtils(deviceId);
        }
        return utils;
    }


    private SyncHistoryUtils(String deviceId) {
        this.deviceId = deviceId;
        EventBus.getDefault().register(this);
    }


    public void start() {
        isSync = true;
        getAllMuLu();
        MyLog.delFile();
    }


    /**
     * 获取所有目录
     */
    private void getAllMuLu() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x33;
            startTime();
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createAllSportRecordCmd().getCmdByte());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
//        TimerUtil.stopTimerTask("sync");
        try {
            BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
            if (UartService.COUNT_OPENTION == 0x33) {  //目录数
                MyLog.e("获取目录条数", com.tohabit.skip.utils.blue.ByteUtils.byte2HexStr(event.getData(), event.getData().length));
                byte[] changdu = new byte[]{builder.getDataBody()[0], builder.getDataBody()[1]};
                muluCount = ByteUtils.bytesToInt(changdu);
                LogUtils.e("获取的目录条数：" + muluCount);
                MyLog.e("目录条数", muluCount);
                if (muluCount > 0) {
                    selectPosition = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getMuLuMessage(selectPosition);
                        }
                    }, 500);
                } else {
                    isSync = false;
                    ToastUtil.show("数据同步完成！");
                    onDestory();
                }
            }
            if (UartService.COUNT_OPENTION == 0x44) {  //目录内容
                MyLog.e("第" + selectPosition + "目录结果", com.tohabit.skip.utils.blue.ByteUtils.byte2HexStr(event.getData(), event.getData().length));
                muluMsg = event.getData();
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
                int skipNum = skipNumByte[0] << 8 | skipNumByte[1];
                int breakNum = breakNumByte[0] << 8 | breakNumByte[1];
                MyLog.e("获取第" + selectPosition + "目录结果", "获取的跳绳次数：" + skipNum + "获取的断绳次数：" + breakNum);
                yundongMsg = new BleYundongMsg(duration, skipNum, breakNum, dateTime);
                getYundongMsg(dateTime, selectPosition);
            }
            if (UartService.COUNT_OPENTION == 0x77) {  //跳绳轨迹分包数据
                if (event.getData().length == 6) {   //查询结果
                    if (builder.getDataBody()[0] != 0x00) {
                        ToastUtil.shortShow("同步失败！");
                        isSync = false;
                        onDestory();
                        MyLog.e("获取第" + selectPosition + "目录时报错，错误包=", com.tohabit.skip.utils.blue.ByteUtils.byte2HexStr(event.getData(), event.getData().length));
                    }
                    return;
                }
                if (Arrays.equals(muluMsg, event.getData())) {
                    MyLog.e("获取第" + selectPosition + "目录时返回的重复目录详情", com.tohabit.skip.utils.blue.ByteUtils.byte2HexStr(event.getData(), event.getData().length));
                    return;
                }
                MyLog.e("第" + selectPosition + "目录的采样数据", com.tohabit.skip.utils.blue.ByteUtils.byte2HexStr(event.getData(), event.getData().length));
                try {
                    BleData bleData = new BleData(event.getData(), pointDataLength);
                    blePoints.addAll(bleData.getBlePointList());//把所有解析出来的坐标点保存起来
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                byte[] head = new byte[]{event.getData()[0], event.getData()[1]};
                byte[] last = new byte[]{(byte) 0xFF, (byte) 0xFF};
                if (Arrays.equals(head, last) && blePoints.size() > 0) {//如果是最后一包，说明此次运动数据已经取完，删除
                    Log.d("chen", "接收到跳绳数据。");
                    Log.d("chen", "圈序号：" + blePoints.get(0).getNumber() + "-" + blePoints.get(blePoints.size() - 1).getNumber());
                    Log.d("chen", "共 " + blePoints.size() + " 个采样点");
                    save();
                    if (selectPosition < muluCount - 1) {
                        selectPosition++;
                        getMuLuMessage(selectPosition);
                    } else {
                        addTest();
                    }
                } else {
                    startTime();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            isSync = false;
            onDestory();
            ToastUtil.shortShow("同步失败！" + ex.getMessage());
            MyLog.e("获取第" + selectPosition + "目录时报错，错误包=", com.tohabit.skip.utils.blue.ByteUtils.byte2HexStr(event.getData(), event.getData().length));
        }
    }


    /**
     * 获取目录内容
     */
    private void getMuLuMessage(int muluCount) {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x44;
            startTime();
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
            startTime();
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetPointCmd(date, baoxuhao).getCmdByte());
        }
    }


    /**
     * 保存一个目录的结果
     */
    private void save() {
        Example example = new Example(AppManager.getAppManager().curremtActivity().getAssets(),
                yundongMsg.getBreakNum(), yundongMsg.getSkipNum(),
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
                ToastUtil.show("数据同步完成！");
                deleteAll();
                isSync = false;
                EventBus.getDefault().post(new SyncSuressEvent());
                onDestory();
            }

            @Override
            public void onFiled(String message) {
                ToastUtil.show(message);
                isSync = false;
                onDestory();
            }
        });
    }


    public void onDestory() {
        EventBus.getDefault().unregister(this);
    }


    private void startTime() {
//        TimerUtil.startTimerTask("sync", 5000, new TimerTaskDoCallBack() {
//            @Override
//            public void taskDo() {
//                ToastUtil.show("数据同步超时！");
//                isSync = false;
//                onDestory();
//            }
//        });
    }
}

