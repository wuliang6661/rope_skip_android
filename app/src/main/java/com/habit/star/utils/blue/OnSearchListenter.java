package com.habit.star.utils.blue;

import android.bluetooth.BluetoothDevice;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1513:45
 * desc   :  搜索蓝牙的监听
 * version: 1.0
 */
public interface OnSearchListenter {


    /**
     * 开始搜索
     */
    void searchStart();


    /**
     * 搜索到设备
     */
    void searchDevices(BluetoothDevice device);


    /**
     * 搜索结束
     */
    void searchStop();
}
