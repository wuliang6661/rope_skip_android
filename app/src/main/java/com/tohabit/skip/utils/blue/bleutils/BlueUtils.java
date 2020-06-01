package com.tohabit.skip.utils.blue.bleutils;


import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.event.model.BlueEvent;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import static com.tohabit.skip.app.Constants.characterUUID1;
import static com.tohabit.skip.app.Constants.serviceUUID;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

/**
 * 蓝牙连接工具类，提供连接指定蓝牙设备的方法 （低功耗蓝牙）
 */
public class BlueUtils {

    private static BlueUtils blueUtils;

    private BluetoothClient mClient;

    private onBlueListener listener;

    private onBlueNotifiListener notifiListener;

    private String MAC;

    /**
     * 蓝牙是否连接
     *
     * @return
     */
    private boolean isConnect = false;


    public static BlueUtils getInstance() {
        if (blueUtils == null) {
            blueUtils = new BlueUtils();
        }
        return blueUtils;
    }


    private BlueUtils() {
        mClient = new BluetoothClient(Utils.getApp());
    }


    /**
     * 连接指定蓝牙
     */
    public void connectMac(String MAC) {
        if (!StringUtils.isEmpty(MAC)) {   //如果之前已经连接过该蓝牙
            disConnectBlue();  //断开连接
        }
        this.MAC = MAC;
        //判断蓝牙是否开启
        mClient.registerBluetoothStateListener(bluetoothStateListener);
        if (mClient.isBluetoothOpened()) {
            disConnectBlue();
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(3)   // 连接如果失败重试3次
                    .setConnectTimeout(30000)   // 连接超时30s
                    .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                    .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                    .build();
            mClient.connect(MAC, options, new BleConnectResponse() {

                @Override
                public void onResponse(int code, BleGattProfile data) {
                    if (code == REQUEST_SUCCESS) {
                        if (listener != null) {
                            listener.onConnect(true);
                            BlueEvent event = new BlueEvent();
//                            event.isConnect = true;
                            EventBus.getDefault().post(event);
                            isConnect = true;
                            App.spUtils.put(Constants.MAC, MAC);
                        }
                    }
                }
            });
            mClient.registerConnectStatusListener(MAC, mBleConnectStatusListener);
        } else {
            mClient.openBluetooth();
        }
    }


    /**
     * 搜索蓝牙
     */
    public void searchMac() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 2)   // 先扫BLE设备3次，每次3s
//                .searchBluetoothClassicDevice(5)
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        mClient.search(request, new SearchResponse() {

            @Override
            public void onSearchStarted() {//开始搜素
                if (listener != null) {
                    listener.searchStart();
                }
            }

            @Override
            public void onDeviceFounded(SearchResult device) {//找到设备 可通过manufacture过滤
                Beacon beacon = new Beacon(device.scanRecord);
                BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
                if (listener != null) {
                    listener.searchMacs(device);
                }
                EventBus.getDefault().post(device);
            }

            @Override
            public void onSearchStopped() {//搜索停止
                if (listener != null) {
                    listener.searchStop();
                }
            }

            @Override
            public void onSearchCanceled() {//搜索取消
                if (listener != null) {
                    listener.searchStop();
                }
            }
        });
    }


    /**
     * 写入数据
     */
    public void writeData(byte[] bytes) {
        // 要注意这里写的byte[]不能超过20字节，如果超过了需要自己分成几次写。建议的办法是第一个byte放剩余要写的字节的长度。
//        if(bytes.length > 20){
//
//        }
        if (StringUtils.isEmpty(MAC)) {
            return;
        }
        mClient.write(MAC, UUID.fromString(serviceUUID), UUID.fromString(characterUUID1),
                bytes, new BleWriteResponse() {

                    @Override
                    public void onResponse(int code) {
                        if (code == REQUEST_SUCCESS) {
                            LogUtils.e("写入成功！");
                        }
                    }
                });
    }


    /**
     * 建立接收消息的状态
     */
    public void createNotifion(onBlueNotifiListener listener) {
        if (StringUtils.isEmpty(MAC)) {
            return;
        }
        mClient.unnotify(MAC, UUID.fromString(serviceUUID), UUID.fromString(characterUUID1)
                , new BleUnnotifyResponse() {
                    @Override
                    public void onResponse(int code) {

                    }
                });
        onBlueNotify(MAC, listener);
    }


    /**
     * 监听蓝牙的连接状态
     */
    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == STATUS_CONNECTED) {
//                if (listener != null) {
//                    listener.onConnect(true);
//                }
            } else if (status == STATUS_DISCONNECTED) {
//                if (listener != null) {
//                    listener.onConnect(false);
//                }
                BlueEvent event = new BlueEvent();
//                event.isConnect = false;
                EventBus.getDefault().post(event);
                isConnect = false;
            }
        }
    };


    /**
     * 监听蓝牙的开启状态
     */
    private BluetoothStateListener bluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            if (openOrClosed) {
                //搜索蓝牙
//                connectMac(MAC);
            }
        }
    };


    /**
     * 蓝牙配对状态监听
     */
    private final BluetoothBondListener mBluetoothBondListener = new BluetoothBondListener() {
        @Override
        public void onBondStateChanged(String mac, int bondState) {
            Log.d("hlib", " mac " + mac + " bondState " + bondState);
        }
    };


    /**
     * 监听蓝牙的返回
     */
    private void onBlueNotify(String MAC, onBlueNotifiListener listener) {
        mClient.notify(MAC, UUID.fromString(serviceUUID), UUID.fromString(characterUUID1),
                new BleNotifyResponse() {

                    @Override
                    public void onNotify(UUID service, UUID character, byte[] value) {
                        if (listener != null) {
                            listener.onNotifiBlue(value);
                        }
                    }

                    @Override
                    public void onResponse(int code) {
                        if (code == REQUEST_SUCCESS) {   //监听建立之后再发送数据
                            notifiListener = listener;
                            if (listener != null) {
                                listener.notifiConnectSourcess();
                            }
                        } else {
                            if (listener != null) {
                                listener.notifiConnectError();
                            }
                        }
                    }
                });
    }


    /**
     * 断开蓝牙连接
     */
    public void disConnectBlue() {
        if (StringUtils.isEmpty(MAC)) {
            return;
        }
        mClient.unnotify(MAC, UUID.fromString(serviceUUID), UUID.fromString(characterUUID1), new BleUnnotifyResponse() {

            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {
                    if (listener != null) {
                        listener.onConnect(false);
                    }
                }
            }
        });
        mClient.unregisterConnectStatusListener(MAC, mBleConnectStatusListener);
        mClient.unregisterBluetoothStateListener(bluetoothStateListener);
        mClient.disconnect(MAC);
    }


    public void setListener(onBlueListener listener) {
        this.listener = listener;
    }

    /**
     * 如果监听接收的方法已开启
     *
     * @return
     */
    public boolean isNotifiOpen() {
        return notifiListener != null;
    }

    public boolean isConnect() {
        return isConnect;
    }

    /**
     * 暴露监听接口
     */
    public interface onBlueListener {

        /**
         * 是否连接上
         *
         * @param isConnect
         */
        void onConnect(boolean isConnect);

        /**
         * 开始搜索蓝牙
         */
        void searchStart();

        /**
         * 搜索停止
         */
        void searchStop();

        /**
         * 搜索到的蓝牙设备
         */
        void searchMacs(SearchResult result);

    }


    public interface onBlueNotifiListener {

        /**
         * 蓝牙返回的数据
         */
        void onNotifiBlue(byte[] value);

        void notifiConnectSourcess();

        void notifiConnectError();
    }

}
