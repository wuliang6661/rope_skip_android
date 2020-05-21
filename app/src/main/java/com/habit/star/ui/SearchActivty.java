package com.habit.star.ui;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.habit.star.R;
import com.habit.star.base.BaseActivity;
import com.habit.star.utils.blue.OnSearchListenter;
import com.habit.star.utils.blue.bleutils.BlueUtils;
import com.habit.star.utils.blue.btutil.BlueDeviceUtils;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.inuker.bluetooth.library.search.SearchResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivty extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

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
        devices = new ArrayList<>();
        initDeviceBlue();
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
        BluetoothDevice device = blueDeviceUtils.getConnectBlue();
        if(device != null){
            devices.add(device);
            setAdapter();
        }
        blueDeviceUtils.setListener(new OnSearchListenter() {

            @Override
            public void searchStart() {

            }

            @Override
            public void searchDevices(BluetoothDevice device) {
                if (device.getName().startsWith("TH")) {
                    devices.add(device);
                    setAdapter();
                }
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
                setAdapter();
            }
        });
        blueUtils.searchMac();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<BluetoothDevice> adapter = new LGRecycleViewAdapter<BluetoothDevice>(devices) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blue;
            }

            @Override
            public void convert(LGViewHolder holder, BluetoothDevice result, int position) {
                holder.setText(R.id.item_text, result.getName());
            }
        };
        adapter.setOnItemClickListener(R.id.connect, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                EventBus.getDefault().post(devices.get(position));
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlueDeviceUtils deviceUtils = BlueDeviceUtils.getInstance();
        deviceUtils.onDestory();
    }


    @OnClick(R.id.search_btn)
    public void search() {
        initDeviceBlue();
    }


}
