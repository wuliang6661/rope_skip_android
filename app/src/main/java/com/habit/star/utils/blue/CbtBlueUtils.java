package com.habit.star.utils.blue;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import com.habit.star.app.Constants;
import com.habit.star.utils.AppManager;
import com.sdwfqin.cbt.CbtManager;
import com.sdwfqin.cbt.callback.ConnectDeviceCallback;
import com.sdwfqin.cbt.callback.ScanCallback;
import com.sdwfqin.cbt.callback.SendDataCallback;
import com.sdwfqin.cbt.callback.ServiceListenerCallback;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1513:33
 * desc   : 经典蓝牙操作工具
 * version: 1.0
 */
public class CbtBlueUtils {


    /**
     * 蓝牙工具类对象,避免重复初始化
     */
    private static CbtBlueUtils blueUtils;


    /**
     * 上下文对象
     */
    private Context mContext;

    private OnSearchListenter listener;

    public static CbtBlueUtils getInstance() {
        if (blueUtils == null) {
            blueUtils = new CbtBlueUtils();
        }
        return blueUtils;
    }


    private CbtBlueUtils() {
        mContext = AppManager.getAppManager().curremtActivity();
        CbtManager.getInstance().setUUID(Constants.serviceUUID);
    }


    /**
     * 搜索蓝牙
     */
    public void searchDevices() {
        CbtManager
                .getInstance()
                .scan(new ScanCallback() {
                    @Override
                    public void onScanStart(boolean isOn) {
                        // 开始扫描
                        if (listener != null) {
                            listener.searchStart();
                        }
                    }

                    @Override
                    public void onScanStop(List<BluetoothDevice> devices) {
                        // 搜索完成
                        if (listener != null) {
                            listener.searchStop();
                        }
                    }

                    @Override
                    public void onFindDevice(BluetoothDevice device) {
                        // 搜索到设备
                        if (listener != null) {
                            listener.searchDevices(device);
                        }
                    }
                });
    }


    /**
     * 连接指定经典蓝牙
     */
    public void connectDevices(BluetoothDevice device) {
        CbtManager.getInstance()
                .connectDevice(device, new ConnectDeviceCallback() {
                    @Override
                    public void connectSuccess(BluetoothSocket socket, BluetoothDevice device) {
                        // 连接成功
                        Toast.makeText(mContext, "连接成功！", Toast.LENGTH_SHORT).show();
                        notifiBlue();
                    }

                    @Override
                    public void connectError(Throwable throwable) {
                        // 连接失败
                        Toast.makeText(mContext, "连接失败：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 监听蓝牙的回调
     */
    public void notifiBlue() {
        CbtManager
                .getInstance()
                .startServiceListener(new ServiceListenerCallback() {
                    @Override
                    public void onStartError(Throwable throwable) {
                        // 发生错误
                        CbtLogs.e(throwable.getMessage());
                    }

                    @Override
                    public void onDataListener(String s, BluetoothDevice device) {
                        // 获取到数据
                    }
                });
    }


    /**
     * 往蓝牙写入数据
     */
    public void sendData() {
        List<byte[]> bytes = new ArrayList<>();
//        bytes.add(BYTES[0]);
//        bytes.add(BYTES[1]);
//        bytes.add(data);
        CbtManager
                .getInstance()
                .sendData(bytes, new SendDataCallback() {
                    @Override
                    public void sendSuccess() {
                        // 发送成功
                    }

                    @Override
                    public void sendError(Throwable throwable) {
                        // 发送失败
                    }
                });
    }


    public void setListener(OnSearchListenter listener) {
        this.listener = listener;
    }
}
