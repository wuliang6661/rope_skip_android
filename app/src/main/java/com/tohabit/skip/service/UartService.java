package com.tohabit.skip.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.utils.blue.ByteUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UartService extends Service {


    private final static String TAG = UartService.class.getSimpleName();
    List<BluetoothGattService> serviceList = new ArrayList<BluetoothGattService>();//发现的服务列表

    private BluetoothAdapter mBluetoothAdapter;//本地蓝牙适配器
    private String mBluetoothDeviceAddress;//本地蓝牙MAC地址
    private BluetoothGatt mBluetoothGatt;//GTAA
    /**
     * 假设生产商提供了一个服务，该服务里面有两个特征值
     */
    private BluetoothGattService mBluetoothGattService;//gatt服务
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic1;//gatt特征值1
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic2;//gatt特征值2
    private int mConnectionState = STATE_DISCONNECTED;


    //连接状态常量
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;

    //蓝牙厂商提供的UUID
    private static final UUID UUID_SERVICE = UUID.fromString(Constants.serviceUUID); //服务
    private static final UUID UUID_CHARA1 = UUID.fromString(Constants.characterUUID1); //特征值1
    private static final UUID UUID_CHARA2 = UUID.fromString(Constants.characterUUID2); //特征值2

    private static final String UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb";

    public static final int MESSAGE_STATE_CHANGE = 0x11;
    public static final int MESSAGE_DEVICE_NAME = 0x22;
    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_TOAST = 0x44;
    public static final String TOAST = "toast";
    public static final int MESSAGE_WRITE = 0x55;
    public static final int MESSAGE_READ = 0x66;
    public static final int NITIFI_SOURESS = 0X77;

    /**
     * 当前正在进行的操作
     */
    public static int COUNT_OPENTION = 0;


    private Handler mHandler;


    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnectionState = STATE_CONNECTED;
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());
                // Give the new state to the Handler so the UI Activity can update
                mHandler.obtainMessage(MESSAGE_STATE_CHANGE, mConnectionState, -1).sendToTarget();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                connectionLost();
                mHandler.obtainMessage(MESSAGE_STATE_CHANGE, mConnectionState, -1).sendToTarget();
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                mHandler.obtainMessage(MESSAGE_STATE_CHANGE, mConnectionState, -1).sendToTarget();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {//服务被发现
            if (status == BluetoothGatt.GATT_SUCCESS) {
                System.out.println("Service has bean discover.");
                mBluetoothGattService = gatt.getService(UUID_SERVICE);//发现服务
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
            List<BluetoothGattService> list = mBluetoothGatt.getServices();
            for (BluetoothGattService bluetoothGattService : list) {
                String str = bluetoothGattService.getUuid().toString();
                List<BluetoothGattCharacteristic> gattCharacteristics = bluetoothGattService
                        .getCharacteristics();
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    Log.e("onServicesDisc中中中", " ：" + gattCharacteristic.getUuid());
                    if (Constants.characterUUID1.equals(gattCharacteristic.getUuid().toString())) {
                        mBluetoothGattService = bluetoothGattService;
                        mBluetoothGattCharacteristic1 = gattCharacteristic;
                        setCharacteristic2Notification(mBluetoothGattCharacteristic1, true);
                    }
                    if (Constants.characterUUID2.equals(gattCharacteristic.getUuid().toString())) {
                        mBluetoothGattService = bluetoothGattService;
                        mBluetoothGattCharacteristic2 = gattCharacteristic;
                        setCharacteristic2Notification(mBluetoothGattCharacteristic2, true);
                    }
                }

            }
//            setCharacteristicNotification(mBluetoothGattCharacteristic1, true);//必须要有，否则接收不到数据
//            try {
//                Thread.sleep(600);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            setCharacteristic2Notification(mBluetoothGattCharacteristic2, true);//必须要有，否则接收不到数据
            mHandler.sendEmptyMessageDelayed(NITIFI_SOURESS, 500);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.e("读取数据成功！");
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic,
                                          int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.e("写入数据成功！");
            } else {
                LogUtils.e("写入数据失败！");
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            LogUtils.e("收到蓝牙反馈数据" + ByteUtils.byte2HexStr(characteristic.getValue(), characteristic.getValue().length));
            mHandler.obtainMessage(MESSAGE_READ, characteristic.getValue().length, -1, characteristic.getValue())
                    .sendToTarget();  //将消息传回主界面
        }


        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //开启监听成功，可以像设备写入命令了
                Log.e(TAG, "开启监听成功");
            }

        }


    };


    /**
     * 指示连接已丢失并通知UI活动
     */
    private void connectionLost() {
        // 将失败消息发送回活动
        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "丢失设备连接");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }


    /**
     * 指示连接尝试失败并通知UI活动.
     */
    private void connectionFailed() {
        // 将失败消息发送回活动
        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "无法连接设备");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }


    /**
     * 获取当前连接状态
     */
    public int getConnectionState() {
        return mConnectionState;
    }


    public class LocalBinder extends Binder {
        public UartService getService() {
            return UartService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {//初始化
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(final String address) {//连接服务
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mConnectionState = STATE_DISCONNECTED;
        mHandler.obtainMessage(MESSAGE_STATE_CHANGE, mConnectionState, -1).sendToTarget();
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public boolean writeCharacteristic1Info(byte[] data) {
        if (mBluetoothGattService == null) {
            return false;
        }
        mBluetoothGattCharacteristic1 = mBluetoothGattService.getCharacteristic(UUID_CHARA1);//获得特征值1
        mBluetoothGattCharacteristic1.setValue(data);
        writeCharacteristic(mBluetoothGattCharacteristic1);
        return true;
    }


    public boolean readCharacteristic1Info(byte[] data) {
        if (mBluetoothGattService == null) {
            return false;
        }
        mBluetoothGattCharacteristic2 = mBluetoothGattService.getCharacteristic(UUID_CHARA2);//获得特征值1
        mBluetoothGattCharacteristic2.setValue(data);
        mBluetoothGatt.readCharacteristic(mBluetoothGattCharacteristic2);
        return true;
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled        If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

//        BluetoothGattDescriptor uuid2 =  new BluetoothGattDescriptor(UUID_CHARA2, BluetoothGattDescriptor.PERMISSION_WRITE);
//        uuid2.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//        characteristic.addDescriptor(uuid2);
        boolean isEnableNotification = mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        if (isEnableNotification) {
            List<BluetoothGattDescriptor> descriptorList = characteristic.getDescriptors();
            if (descriptorList != null && descriptorList.size() > 0) {
                for (BluetoothGattDescriptor descriptor : descriptorList) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(descriptor);
                    Log.w("wuliang", "设置特征值开启监听成功！");
                }
            }
        }
    }


    public void setCharacteristic2Notification(BluetoothGattCharacteristic characteristic,
                                               boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        boolean isEnableNotification = mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
//        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR));
//        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//        mBluetoothGatt.writeDescriptor(descriptor);
        if (isEnableNotification) {
            List<BluetoothGattDescriptor> descriptorList = characteristic.getDescriptors();
            if (descriptorList != null && descriptorList.size() > 0) {
                for (BluetoothGattDescriptor descriptor : descriptorList) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(descriptor);
                    Log.w("wuliang", "设置特征值开启监听成功！");
                }
            }
        }
    }


    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }

    @Override//使用startService启动服务时回调
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("UartService Start");
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 设置主线程handler
     *
     * @param handler
     */
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

}
