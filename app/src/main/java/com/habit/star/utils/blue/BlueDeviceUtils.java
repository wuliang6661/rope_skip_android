package com.habit.star.utils.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.blankj.utilcode.util.LogUtils;
import com.habit.star.utils.AppManager;
import com.habit.star.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private OnSearchListenter listener;

    /**
     * 存放搜索到的蓝牙列表
     */
    private Map<String, BluetoothDevice> booth;

    private List<BluetoothDevice> mBlueList = new ArrayList<>();


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
        booth = new HashMap<>();
        registerBrodcast();
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
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
        if (this.listener != null) {
            listener.searchStart();
        }
    }


    /**
     * 获取当前正在连接的蓝牙设备
     */
    public BluetoothDevice getConnectBlue() {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            if (!StringUtils.isEmpty(device.getName())) {
                booth.remove(device.getName());
                booth.put(device.getName(), device);
                return device;
            }
        }
        return null;
    }


    /**
     * 连接蓝牙
     */
    public boolean connectBlue(BluetoothDevice device) {
        try {
            return ClsUtils.createBond(device.getClass(), device);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
                // 没否配对
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!StringUtils.isEmpty(device.getName())) {
                        booth.remove(device.getName());
                        booth.put(device.getName(), device);
                        if (listener != null) {
                            listener.searchDevices(device);
                        }
                    }
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 关闭进度条
                for (String key : booth.keySet()) {
                    if (!mBlueList.contains(booth.get(key))) {
                        if (booth.get(key).getName().contains("RP4")) {
                            mBlueList.add(booth.get(key));
                        }

                    }
                }
                if (listener != null) {
                    listener.searchStop();
                }
            }
        }
    };


    public void onDestory() {
        mContext.unregisterReceiver(receiver);
    }


    public void setListener(OnSearchListenter listener) {
        this.listener = listener;
    }

}
