package com.habit.star.ui;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;
import com.habit.star.event.model.BlueEvent;
import com.habit.star.pojo.po.DeviceBO;
import com.habit.star.service.UartService;
import com.habit.star.utils.blue.OnSearchListenter;
import com.habit.star.utils.blue.btutil.BlueDeviceUtils;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    List<BluetoothDevice> devices;

    private List<DeviceBO> deviceBOS;

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
                    if (item.getName().equals(device.getName())) {
                        return;
                    }
                }
//                if (device.getName().startsWith("TH")) {
                devices.add(device);
                setAdapter();
//                }
            }

            @Override
            public void searchStop() {
                blueDeviceUtils.startScanBluth();
            }
        });
        blueDeviceUtils.startScanBluth();
    }


//    /**
//     * 蓝牙连接并获取数据
//     */
//    private void initBlue() {
//        BlueUtils blueUtils = BlueUtils.getInstance();
//        blueUtils.setListener(new BlueUtils.onBlueListener() {
//            @Override
//            public void onConnect(boolean isConnect) {
//                stopProgress();
//                if (isConnect) {
//                    showToast("蓝牙连接成功！");
//                } else {
//                    showToast("蓝牙连接成功！");
//                }
//            }
//
//            @Override
//            public void searchStart() {
//
//            }
//
//            @Override
//            public void searchStop() {
//                stopProgress();
//            }
//
//            @Override
//            public void searchMacs(SearchResult devices) {
//                setAdapter();
//            }
//        });
//        blueUtils.searchMac();
//    }


    private void setAdapter() {
        LGRecycleViewAdapter<BluetoothDevice> adapter = new LGRecycleViewAdapter<BluetoothDevice>(devices) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blue;
            }

            @Override
            public void convert(LGViewHolder holder, BluetoothDevice result, int position) {
                holder.setText(R.id.item_text, result.getName());
                holder.getView(R.id.connect).setEnabled(false);
                if (App.connectDevice != null && App.connectDevice.getAddress().equals(result.getAddress())) {
                    holder.setText(R.id.connect, "已连接");
                } else {
                    if (deviceBOS != null) {
                        for (DeviceBO deviceBO : deviceBOS) {
                            if (deviceBO.getMacAddress().equals(result.getAddress())) {
                                holder.getView(R.id.connect).setEnabled(true);
                            }
                        }
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.connect, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                showProgress("蓝牙连接中...");
//                BlueDeviceUtils.getInstance().cancleScan();
                EventBus.getDefault().post(devices.get(position));
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueEvent event) {
        stopProgress();
        if (event.isConnect == UartService.STATE_CONNECTED) {
        } else if (event.isConnect == UartService.STATE_CONNECTING) {
        } else if (event.isConnect == UartService.NITIFI_SOURESS) {  //监听已经开始建立
        } else {
        }
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
