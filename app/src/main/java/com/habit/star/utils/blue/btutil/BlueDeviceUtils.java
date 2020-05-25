package com.habit.star.utils.blue.btutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.habit.star.app.Constants;
import com.habit.star.utils.AppManager;
import com.habit.star.utils.StringUtils;
import com.habit.star.utils.blue.ByteUtils;
import com.habit.star.utils.blue.ClsUtils;
import com.habit.star.utils.blue.OnConnectListener;
import com.habit.star.utils.blue.OnSearchListenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/159:44
 * desc   :  使用原生接口调用蓝牙
 * version: 1.0
 */
public class BlueDeviceUtils {

    /**
     * 蓝牙适配器
     */
    private BluetoothAdapter mBluetoothAdapter;

    /**
     * 蓝牙工具类对象,避免重复初始化
     */
    private static BlueDeviceUtils blueUtils;

    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 搜索监听器
     */
    private OnSearchListenter listener;

    /**
     * 连接监听器
     */
    private OnConnectListener connectListener;

    /**
     * 存放搜索到的蓝牙列表
     */
    private Map<String, BluetoothDevice> booth;

    private List<BluetoothDevice> mBlueList = new ArrayList<>();

    /**
     * 读取线程是否继续执行
     */
    private boolean isRead = false;

    /**
     * 连接蓝牙的线程
     */
    private ConnectThread connectThread;


    public static BlueDeviceUtils getInstance() {
        if (blueUtils == null) {
            blueUtils = new BlueDeviceUtils();
        }
        return blueUtils;
    }


    private BlueDeviceUtils() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = AppManager.getAppManager().curremtActivity();
        initBluetooth();
    }

    private void initBluetooth() {
        // 判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            AppManager.getAppManager().curremtActivity().startActivityForResult(intent, 0x11);
        } else {
            // 不做提示，强行打开
            mBluetoothAdapter.enable();
        }
    }


    private void registerBrodcast() {
        // 找到设备的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 注册广播
        mContext.registerReceiver(receiver, filter);
        // 搜索完成的广播
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播
        mContext.registerReceiver(receiver, filter1);

    }


    /**
     * 开始搜索蓝牙
     */
    public void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        booth = new HashMap<>();
        registerBrodcast();
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
        if (this.listener != null) {
            listener.searchStart();
        }
    }


    public void cancleScan(){
        mBluetoothAdapter.cancelDiscovery();
    }

    /**
     * 获取当前正在连接的蓝牙设备
     */
    public BluetoothDevice getConnectBlue() {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            if (!StringUtils.isEmpty(device.getName())) {
                if(device.getName().startsWith("TH")){
                    return device;
                }
            }
        }
        return null;
    }


    /**
     * 配对蓝牙
     */
    public boolean pairBlue(BluetoothDevice device) {
        try {
            return ClsUtils.createBond(device.getClass(), device);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 作为客户端连接蓝牙
     */
    public void connectBlue(BluetoothDevice device, OnConnectListener listener) {
        isRead = true;
        this.connectListener = listener;
        connectThread = new ConnectThread(device);
        connectThread.start();
    }


    /**
     * 写入数据
     */
    public void writeData(byte[] data) {
        if (connectThread != null) {
            connectThread.writeByte(data);
        } else {
            LogUtils.e("蓝牙连接线程未启动！");
        }
    }


    /**
     * 断开连接
     */
    public void disConnenctBlue() {
        isRead = false;
    }


    /**
     * 作为服务端连接蓝牙
     */
    public void asServiceConnectBlue(BluetoothDevice device) {
        new AcceptThread().start();
    }


    /**
     * 启动一个客户端连接线程
     */
    private class ConnectThread extends Thread {

        private BluetoothSocket mSocket;

        public ConnectThread(BluetoothDevice device) {
            // 这里的 UUID 需要和服务器的一致
            try {
                mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(Constants.serviceUUID));
                if (mSocket != null) {
                    if (connectListener != null) {
                        connectListener.onConnectSourcess();
                    }
                } else {
                    if (connectListener != null) {
                        connectListener.onConnectError();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 启动读取数据
         */
        public void run() {
            // 判断是否在搜索,如果在搜索，就取消搜索
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            try {
                mSocket.connect();
                while (isRead) {
                    InputStream inputStream = mSocket.getInputStream();
                    if (inputStream == null) {
                        continue;
                    }
                    byte[] result = ByteUtils.readStream1(inputStream);
                    if (result == null || result.length == 0) {
                        continue;
                    }
                    if (connectListener != null) {
                        connectListener.onNitifion(result);
                    }
                }
                if (!isRead) {
                    cancle();
                }
            } catch (IOException connectException) {
                cancle();
            }
        }


        /**
         * 写入数据
         */
        public void writeByte(byte[] data) {
            OutputStream outputStream = null;
            try {
                if (mSocket == null) {
                    return;
                }
//                if (data.length > 40) {   //大于40个字节长度 ，分割循环发送
//
//                }
                outputStream = mSocket.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }


        public void cancle() {
            try {
                if (mSocket != null) {
                    mSocket.close();
                    mSocket = null;
                }
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
        }
    }


    /**
     * 启动一个服务端线程 ，等待连接
     */
    private class AcceptThread extends Thread {

        private BluetoothServerSocket mServerSocket;

        public AcceptThread() {
            try {
                mServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(AppUtils.getAppPackageName()
                        , UUID.fromString(Constants.serviceUUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = mServerSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null) {
                    // 自定义方法
                    InputStream inputStream = null;
                    try {
                        inputStream = socket.getInputStream();
                        byte[] result = ByteUtils.readStream1(inputStream);
                        mServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            }
        }

        public void cancle() {
            try {
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到的广播类型
            String action = intent.getAction();
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtils.e(device.getName() + "    Mac===" + device.getAddress());
                if (!StringUtils.isEmpty(device.getName())) {
                    booth.remove(device.getName());
                    booth.put(device.getName(), device);
                    if (listener != null) {
                        listener.searchDevices(device);
                    }
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 关闭进度条
//                for (String key : booth.keySet()) {
//                    if (!mBlueList.contains(booth.get(key))) {
//                        if (booth.get(key).getName().contains("RP4")) {
//                            mBlueList.add(booth.get(key));
//                        }
//
//                    }
//                }
                if (listener != null) {
                    listener.searchStop();
                }
            }
        }
    };


    public void onDestory() {
        cancleScan();
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
        }
    }


    /**
     * 设置搜索连接
     */
    public void setListener(OnSearchListenter listener) {
        this.listener = listener;
    }

}
