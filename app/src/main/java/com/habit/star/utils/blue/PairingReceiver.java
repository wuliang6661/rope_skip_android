package com.habit.star.utils.blue;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2016:06
 * desc   : 自动配对的Receiver
 * version: 1.0
 */
public class PairingReceiver extends BroadcastReceiver {

    String strPsw = "1234";
    final String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
    static BluetoothDevice remoteDevice = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_PAIRING_REQUEST)) {
            BluetoothDevice device = intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                try {
                    ClsUtils.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
                    Toast.makeText(context, "配对信息" + device.getName(), Toast.LENGTH_SHORT)
                            .show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "请求连接错误...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
