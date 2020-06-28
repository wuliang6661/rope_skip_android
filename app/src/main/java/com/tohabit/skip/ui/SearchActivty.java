package com.tohabit.skip.ui;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.pojo.po.DeviceBO;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.utils.blue.OnSearchListenter;
import com.tohabit.skip.utils.blue.btutil.BlueDeviceUtils;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

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
                    if (item.getAddress().equals(device.getAddress())) {
                        return;
                    }
                }
                if (device.getName().startsWith("TH")) {
                    devices.add(device);
                    setAdapter();
                }
            }

            @Override
            public void searchStop() {
                blueDeviceUtils.startScanBluth();
            }
        });
        blueDeviceUtils.startScanBluth();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<BluetoothDevice> adapter = new LGRecycleViewAdapter<BluetoothDevice>(devices) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blue;
            }

            @Override
            public void convert(LGViewHolder holder, BluetoothDevice result, int position) {
                holder.setText(R.id.item_text, "绳柄名称：" + result.getName());
                holder.setText(R.id.mac_text, "蓝牙名称：" + result.getAddress());
                if (App.connectDevice != null && App.connectDevice.getAddress().equals(result.getAddress())) {
                    holder.getView(R.id.connect).setEnabled(false);
                    holder.setText(R.id.connect, "已绑定");
                } else {
                    if (deviceBOS != null) {
//                        for (DeviceBO deviceBO : deviceBOS) {
//                            if (deviceBO.getMacAddress().equals(result.getAddress())) {
//                                holder.getView(R.id.connect).setEnabled(true);
//                            }
//                        }
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.connect, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (App.connectDevice != null && App.connectDevice.getAddress().equals(devices.get(position).getAddress())) {
                    if (App.blueService != null) {
                        App.blueService.disconnect();
                        App.blueService.close();
                    }
                    App.connectDevice = null;
                    App.blueService = null;
                    adapter.notifyDataSetChanged();
                    showToast("已断开连接！");
                } else {
                    if (App.blueService != null) {
                        App.blueService.disconnect();
                        App.blueService.close();
                    }
                    App.connectDevice = null;
                    App.blueService = null;
                    showProgress("蓝牙连接中...");
                    EventBus.getDefault().post(devices.get(position));
                }
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueEvent event) {
        stopProgress();
        if (event.isConnect == UartService.STATE_CONNECTED) {
            setAdapter();
            saveDevices();
        } else if (event.isConnect == UartService.STATE_CONNECTING) {
        } else if (event.isConnect == UartService.NITIFI_SOURESS) {  //监听已经开始建立
        } else {
        }
    }


    /**
     * 保存设备
     */
    private void saveDevices() {
        HttpServerImpl.saveDevices(0, App.connectDevice.getName(), App.connectDevice.getAddress()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
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
