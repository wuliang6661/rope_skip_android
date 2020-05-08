package com.habit.star.ui.devicemanager;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SizeUtils;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.mvp.MVPBaseActivity;
import com.habit.star.pojo.po.DeviceBO;
import com.habit.star.widget.AlertDialog;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class DeviceManagerActivity extends MVPBaseActivity<DeviceManagerContract.View, DeviceManagerPresenter>
        implements DeviceManagerContract.View {


    @BindView(R.id.recycle_view)
    SwipeMenuRecyclerView recycleView;

    private List<DeviceBO> deviceBOS;

    @Override
    protected int getLayoutId() {
        return R.layout.act_device_manager;
    }

    @Override
    protected void initEventAndData() {

        goBack();
        setTitleText("我的设备");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_line));
        recycleView.addItemDecoration(divider);
        setSwipeMenu();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void setSwipeMenu() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            // 2 删除
            SwipeMenuItem editItem = new SwipeMenuItem(this);
            editItem.setText("编辑")
                    .setBackgroundColor(Color.parseColor("#CCCCCC"))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(14) // 文字大小。
                    .setWidth(SizeUtils.dp2px(60))
                    .setHeight(SizeUtils.dp2px(52));

            SwipeMenuItem deleteItem = new SwipeMenuItem(this);
            deleteItem.setText("删除")
                    .setBackgroundColor(Color.parseColor("#7EC7F5"))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(14) // 文字大小。
                    .setWidth(SizeUtils.dp2px(60))
                    .setHeight(SizeUtils.dp2px(52));
            rightMenu.addMenuItem(editItem);
            rightMenu.addMenuItem(deleteItem);
            // 注意：哪边不想要菜单，那么不要添加即可。
        };
        // 设置监听器。
        recycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = (menuBridge, position) -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            if (menuBridge.getPosition() == 0) {  // 编辑
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putSerializable("device", deviceBOS.get(position));
                gotoActivity(DevecesUpdateActivty.class, bundle, false);
            } else {    //删除
                new AlertDialog(DeviceManagerActivity.this).builder().setGone().setMsg("是否确认删除该设备？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> deleteDevices(deviceBOS.get(position).getId())).show();

            }
        };
        // 菜单点击监听。
        recycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }


    @OnClick(R.id.add_devices)
    public void addDevices() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        gotoActivity(DevecesUpdateActivty.class, bundle, false);
    }

    /**
     * 设置数据
     */
    private void getData() {
        HttpServerImpl.getDeviceList().subscribe(new HttpResultSubscriber<List<DeviceBO>>() {
            @Override
            public void onSuccess(List<DeviceBO> s) {
                deviceBOS = s;
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        LGRecycleViewAdapter<DeviceBO> adapter = new LGRecycleViewAdapter<DeviceBO>(deviceBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_devices;
            }

            @Override
            public void convert(LGViewHolder holder, DeviceBO s, int position) {
                holder.setText(R.id.device_name, s.getName());
            }
        };
        recycleView.setAdapter(adapter);
    }


    /**
     * 删除设备
     */
    private void deleteDevices(int id) {
        showProgress();
        HttpServerImpl.delDevice(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                getData();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

}
