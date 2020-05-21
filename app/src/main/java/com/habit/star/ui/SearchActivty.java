package com.habit.star.ui;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.habit.star.R;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.BlueDeviceBO;
import com.habit.star.utils.blue.OnSearchListenter;
import com.habit.star.utils.blue.bleutils.BlueUtils;
import com.habit.star.utils.blue.btutil.BlueDeviceUtils;
import com.habit.star.utils.blue.btutil.BluetoothChatService;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.inuker.bluetooth.library.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    List<BlueDeviceBO> results;
    List<BluetoothDevice> devices;


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

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        results = new ArrayList<>();
        devices = new ArrayList<>();
        initDeviceBlue();
//        initBlue();
//        searchCbtBlue();
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
//        BluetoothDevice device = blueDeviceUtils.getConnectBlue();
//        if (device != null) {
//            BlueDeviceBO deviceBO = new BlueDeviceBO();
//            deviceBO.setConnect(true);
//            deviceBO.setDeviceName(device.getName());
//            deviceBO.setDeviceMac(device.getAddress());
//            results.add(deviceBO);
//            setAdapter();
//        }
        blueDeviceUtils.setListener(new OnSearchListenter() {

            @Override
            public void searchStart() {

            }

            @Override
            public void searchDevices(BluetoothDevice device) {
                for (BlueDeviceBO item : results) {
                    if (device.getName().equals(item.getDeviceName())) {
                        return;
                    }
                }
                BlueDeviceBO deviceBO = new BlueDeviceBO();
                deviceBO.setDeviceMac(device.getAddress());
                deviceBO.setDeviceName(device.getName());
                results.add(deviceBO);
                devices.add(device);
                setAdapter();
            }

            @Override
            public void searchStop() {

            }
        });
        blueDeviceUtils.startScanBluth();
    }


    /**
     * 蓝牙连接并获取数据
     */
    private void initBlue() {
        BlueUtils blueUtils = BlueUtils.getInstance();
        blueUtils.setListener(new BlueUtils.onBlueListener() {
            @Override
            public void onConnect(boolean isConnect) {
                stopProgress();
                if (isConnect) {
                    showToast("蓝牙连接成功！");
                } else {
                    showToast("蓝牙连接成功！");
                }
            }

            @Override
            public void searchStart() {

            }

            @Override
            public void searchStop() {
                stopProgress();
            }

            @Override
            public void searchMacs(SearchResult devices) {
                for (BlueDeviceBO item : results) {
                    if (item.getDeviceName().equals(devices.getName())) {
                        return;
                    }
                }
                BlueDeviceBO deviceBO = new BlueDeviceBO();
                deviceBO.setDeviceMac(devices.getAddress());
                deviceBO.setDeviceName(devices.getName());
                results.add(deviceBO);
                setAdapter();
            }
        });
        blueUtils.searchMac();
//        showProgress("蓝牙搜索中...");
    }


    private void setAdapter() {
        LGRecycleViewAdapter<BlueDeviceBO> adapter = new LGRecycleViewAdapter<BlueDeviceBO>(results) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blue;
            }

            @Override
            public void convert(LGViewHolder holder, BlueDeviceBO result, int position) {
                holder.setText(R.id.item_text, result.getDeviceName());
//                if (result.isConnect()) {
//                    holder.getView(R.id.connect).setVisibility(View.GONE);
//                } else {
//                    holder.getView(R.id.connect).setVisibility(View.VISIBLE);
//                }
            }
        };
        adapter.setOnItemClickListener(R.id.connect, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
//                blueUtils.connectMac(results.get(position).getDeviceMac());
//                boolean connect = BlueDeviceUtils.getInstance().connectBlue(devices.get(position));
//                if (connect) {
//                    showError("连接成功！");
//                } else {
//                    showError("连接失败！");
//                }
                connect(devices.get(position));
            }
        });
        recycleView.setAdapter(adapter);
    }


    BluetoothChatService service;

    /**
     * 连接蓝牙
     */
    private void connect(BluetoothDevice device) {
        if (service != null && service.getState() != BluetoothChatService.STATE_NONE) {
            return;
        }
        BlueDeviceUtils deviceUtils = BlueDeviceUtils.getInstance();
        deviceUtils.cancleScan();
        service = new BluetoothChatService(this, handler);
        service.connect(device);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlueDeviceUtils deviceUtils = BlueDeviceUtils.getInstance();
        deviceUtils.onDestory();
        if (service != null) {
            service.stop();
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothChatService.MESSAGE_DEVICE_NAME:

                    break;
                case BluetoothChatService.MESSAGE_READ:
                    byte[] data = (byte[]) msg.obj;
//
                    break;
                case BluetoothChatService.MESSAGE_TOAST:
                    showToast(msg.getData().getString(BluetoothChatService.TOAST));
                    break;
                case BluetoothChatService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_NONE:
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            showToast("蓝牙连接中...");
                            break;
                        case BluetoothChatService.STATE_CONNECTED:
                            showToast("蓝牙已连接！");
                            break;
                    }
                    break;
            }
        }
    };


    @OnClick(R.id.search_btn)
    public void search() {
        initDeviceBlue();
//        initBlue();
//        searchCbtBlue();
    }


}
