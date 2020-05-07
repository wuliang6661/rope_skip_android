package com.habit.star.ui.devicemanager;


import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.mvp.MVPBaseActivity;
import com.habit.star.widget.AlertDialog;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class DeviceManagerActivity extends MVPBaseActivity<DeviceManagerContract.View, DeviceManagerPresenter>
        implements DeviceManagerContract.View {


    @BindView(R.id.recycle_view)
    SwipeMenuRecyclerView recycleView;

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

        setSwipeMenu();
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
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

            SwipeMenuItem deleteItem = new SwipeMenuItem(this);
            deleteItem.setText("删除")
                    .setBackgroundColor(Color.parseColor("#7EC7F5"))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(14) // 文字大小。
                    .setWidth(SizeUtils.dp2px(60))
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(editItem);
            rightMenu.addMenuItem(deleteItem);
            // 注意：哪边不想要菜单，那么不要添加即可。
        };
        // 设置监听器。
        recycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            if (menuBridge.getPosition() == 0) {  // 编辑

            } else {    //删除
                new AlertDialog(DeviceManagerActivity.this).builder().setGone().setMsg("是否确认删除该设备？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> deleteDevices(0)).show();

            }
        };
        // 菜单点击监听。
        recycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }


    /**
     * 设置数据
     */
    private void getData() {
        HttpServerImpl.getDeviceList().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

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
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(new ArrayList<>()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_devices;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {
                holder.setText(R.id.device_name, s);
            }
        };
        recycleView.setAdapter(adapter);
    }


    /**
     * 删除设备
     */
    private void deleteDevices(int id) {
        HttpServerImpl.delDevice(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
