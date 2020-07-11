package com.tohabit.skip.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tohabit.commonlibrary.widget.NoScrollViewPager;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.common.adapter.CommonFragmentAdapter;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.event.model.CancleEvent;
import com.tohabit.skip.event.model.HideDialogEvent;
import com.tohabit.skip.event.model.SwitchMainEvent;
import com.tohabit.skip.pojo.po.MusicBeatBO;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.presenter.MainPresenter;
import com.tohabit.skip.presenter.contract.MainContract;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.find.fragment.FindMainFragment;
import com.tohabit.skip.ui.mine.activity.MineMainActivity;
import com.tohabit.skip.ui.mine.fragment.MineFragment;
import com.tohabit.skip.ui.train.fragment.TranHomeFragment;
import com.tohabit.skip.ui.young.fragment.YoungHomeFragment;
import com.tohabit.skip.utils.AppManager;
import com.tohabit.skip.utils.DensityUtil;
import com.tohabit.skip.utils.SyncHistoryUtils;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.UpdateUtils;
import com.tohabit.skip.utils.Utils;
import com.tohabit.skip.utils.blue.btutil.BlueDeviceUtils;
import com.tohabit.skip.utils.blue.btutil.BluetoothChatService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/*
 * 创建日期：2020-01-21 17:04
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：Demo.java
 * 类说明：主界面
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fragment_vp)
    NoScrollViewPager fragmentVp;

    @BindView(R.id.iv_tab_item1)
    AppCompatImageView ivTabItem1;
    @BindView(R.id.tv_xunlian_item_bottom)
    AppCompatTextView tvXunlianItemBottom;
    @BindView(R.id.ll_tab_item1)
    LinearLayout llTabItem1;
    @BindView(R.id.iv_tab_item2)
    AppCompatImageView ivTabItem2;
    @BindView(R.id.tv_xiaojiang_item_bottom)
    AppCompatTextView tvXiaojiangItemBottom;
    @BindView(R.id.ll_tab_item2)
    LinearLayout llTabItem2;
    @BindView(R.id.iv_tab_item3)
    AppCompatImageView ivTabItem3;
    @BindView(R.id.tv_find_item_bottom)
    AppCompatTextView tvFindItemBottom;
    @BindView(R.id.ll_tab_item3)
    LinearLayout llTabItem3;
    @BindView(R.id.iv_tab_item4)
    AppCompatImageView ivTabItem4;
    @BindView(R.id.tv_mine_item_bottom)
    AppCompatTextView tvMineItemBottom;
    @BindView(R.id.ll_tab_item4)
    LinearLayout llTabItem4;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;


    private List<Fragment> list;
    private CommonFragmentAdapter fragmentAdapter;
    private int currentIndex = 1;
    //打开蓝牙对话框
    private MaterialDialog openBlueDialog;
    //购买对话框
    private Dialog buyDialog;
    //创建对话框
    private Dialog createDialog;

    /**
     * 存放device的map
     */
    private Map<String, BluetoothDevice> maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected String getLogTag() {
        return "MainActivity %s";
    }

    @Override
    protected void initEventAndData() {
        list = new ArrayList<Fragment>();
        list.add(TranHomeFragment.newInstance(null));
        list.add(YoungHomeFragment.newInstance(null));
        list.add(FindMainFragment.newInstance(null));
        list.add(MineFragment.newInstance(null));
        fragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), list);
        fragmentVp.setAdapter(fragmentAdapter);
        fragmentVp.setCurrentItem(currentIndex);
        fragmentVp.setOffscreenPageLimit(fragmentAdapter.getCount() - 1);//设置缓存所有
        maps = new HashMap<>();

        fragmentVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            /**
             * pagerView被切换后自动执行的方法
             */
            public void onPageSelected(int position) {
                currentIndex = position;
                freshView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getUserInfo();
        initDialog();
        chikcBlue();
        registerPush();
        checkUpdate();
        getMusic();
    }


    /**
     * 获取保存的音乐节拍
     */
    private void getMusic() {
        HttpServerImpl.getMusicAndBeat().subscribe(new HttpResultSubscriber<MusicBeatBO>() {
            @Override
            public void onSuccess(MusicBeatBO s) {
                App.musicBeatBO = s;
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        HttpServerImpl.getUserInfo().subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO userBO) {
                App.userBO = userBO;
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 检查更新
     */
    private void checkUpdate() {
        new UpdateUtils().checkUpdate(this, new UpdateUtils.onUpdateListener() {
            @Override
            public void noUpdate() {
            }
        });
    }


    /**
     * 注册极光
     */
    private void registerPush() {
        JPushInterface.setAlias(this, 1, App.userBO.getPhone());
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add(App.userBO.getPhone());
        JPushInterface.setTags(this, 1, treeSet);
    }


    /**
     * 退到后台
     */
    public void moveToStack() {
//        TRouter.mCurActivityExtra=null;
        moveTaskToBack(true);
    }


    private void showDialogManager() {
        if (App.userBO == null) {
            return;
        }
        if (App.userBO.getYoungGeneralCount() == 0) {  //未拥有小将，创建
            showCreateYoung();
        } else {
            if (App.userBO.getIsBuy() == 0) {   //未购买设备
                showBuyDialog();
            }
        }
    }


    /**
     * 是否开启蓝牙
     */
    void initDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.layout_fragment_dialog_open_blue, null);
        dialogView.findViewById(R.id.tv_refuse_layout_fragment_dialog_open_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBlueDialog.hide();
                showDialogManager();
            }
        });
        dialogView.findViewById(R.id.tv_pass_layout_fragment_dialog_open_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turnOnBluetooth()) {
//                    mPresenter.connectBlue();
                } else {
                    showToast("关闭蓝牙可能会影响跳绳功能！");
                }
                openBlueDialog.hide();
                openBlueDialog.dismiss();
                showDialogManager();
            }
        });
        openBlueDialog = new MaterialDialog.Builder(this)
                .customView(dialogView, false)
                .build();
    }


    /**
     * 是否创建过小将
     */
    private void showCreateYoung() {
        View receiveYoungDialogView = getLayoutInflater().inflate(R.layout.layout_fragment_receive_young_dialog, null);
        receiveYoungDialogView.findViewById(R.id.tv_create_monkey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_PERFECT_INFORMATION);
                intent.setClass(MainActivity.this, MineMainActivity.class);
                startActivity(intent);
            }
        });
        createDialog = new Dialog(this, R.style.MaterialDialogSheet);
        createDialog.setContentView(receiveYoungDialogView);
        createDialog.setCancelable(false);
        createDialog.setCanceledOnTouchOutside(false);
        createDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showBuyDialog();
            }
        });
        Window window = createDialog.getWindow();
        WindowManager.LayoutParams paramsWindow = window.getAttributes();
        paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow.height = window.getWindowManager().getDefaultDisplay().getHeight() - DensityUtil.dp2px(mContext, 80);
        window.setAttributes(paramsWindow);
        createDialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HideDialogEvent event) {
        if (createDialog != null)
            createDialog.hide();
    }


    /**
     * 是否购买了设备
     */
    private void showBuyDialog() {
        if (App.userBO.getIsBuy() == 1) {   //购买设备
            return;
        }
        View buyDialogView = getLayoutInflater().inflate(R.layout.layout_fragment_buy_device_dialog, null);
        buyDialogView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDialog.hide();
            }
        });
        buyDialogView.findViewById(R.id.tv_buy_btn_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDialog.hide();
                if (App.userBO.getType() == 0) {
                    if (Utils.isPkgInstalled(MainActivity.this, "com.taobao.taobao")) {
                        Utils.gotoShop(MainActivity.this, App.userBO.getUrl());
                        updateIsBuy();
                    } else {
                        showToast("您还没有安装淘宝客户端！");
                    }
                } else {
                    if (Utils.isPkgInstalled(MainActivity.this, "com.tmall.wireless")) {
                        Utils.gotoShop(MainActivity.this, App.userBO.getUrl());
                        updateIsBuy();
                    } else {
                        showToast("您还没有安装天猫客户端！");
                    }
                }
            }
        });
        buyDialog = new Dialog(this, R.style.MaterialDialogSheet);
        buyDialog.setContentView(buyDialogView);
        buyDialog.setCancelable(true);
        Window window1 = buyDialog.getWindow();
        WindowManager.LayoutParams paramsWindow1 = window1.getAttributes();
        paramsWindow1.width = window1.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow1.height = window1.getWindowManager().getDefaultDisplay().getHeight() - DensityUtil.dp2px(mContext, 80);
        window1.setAttributes(paramsWindow1);
        buyDialog.show();
    }


    private void updateIsBuy() {
        HttpServerImpl.updateIsBuy().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {

            }
        });
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        showMsg(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    @OnClick({
            R.id.ll_tab_item1,
            R.id.ll_tab_item2,
            R.id.ll_tab_item3,
            R.id.ll_tab_item4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_item1:
                currentIndex = 0;
                break;
            case R.id.ll_tab_item2:
                currentIndex = 1;
                break;
            case R.id.ll_tab_item3:
                currentIndex = 2;
                break;
            case R.id.ll_tab_item4:
                currentIndex = 3;
                break;
        }
        fragmentVp.setCurrentItem(currentIndex);
        freshView();
    }

    /**
     * 切换页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switechFragment(SwitchMainEvent event) {
        currentIndex = event.toSwitch;
        fragmentVp.setCurrentItem(currentIndex);
        freshView();
    }


    ///刷新界面
    private void freshView() {
        switch (currentIndex) {
            case 0:
                //背景
                llTabItem1.setBackground(getResources().getDrawable(R.mipmap.ic_train_pre));
                llTabItem2.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem3.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem4.setBackgroundColor(getResources().getColor(R.color.transparent));

                //颜色
                ivTabItem1.setBackground(getResources().getDrawable(R.mipmap.icon_train_pre));
                ivTabItem2.setBackground(getResources().getDrawable(R.mipmap.icon_xiaojiang_nor));
                ivTabItem3.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_find));
                ivTabItem4.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_mine));

                //文字
                tvXunlianItemBottom.setTextColor(getResources().getColor(R.color.white));
                tvXiaojiangItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvFindItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvMineItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                llTab.setBackground(getResources().getDrawable(R.mipmap.ic_bg_bottom_white));
                llTab.setBackground(getResources().getDrawable(R.mipmap.ic_bg_bottom_white));

                break;
            case 1:
                //背景
                llTabItem1.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem2.setBackground(getResources().getDrawable(R.mipmap.ic_xiaojiang_pre));
                llTabItem3.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem4.setBackgroundColor(getResources().getColor(R.color.transparent));

                //颜色
                ivTabItem1.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_xunlian));
                ivTabItem2.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_xiaojiang));
                ivTabItem3.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_find));
                ivTabItem4.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_mine));


                //文字
                tvXunlianItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvXiaojiangItemBottom.setTextColor(getResources().getColor(R.color.white));
                tvFindItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvMineItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));

                llTab.setBackground(getResources().getDrawable(R.mipmap.ic_tab_bg2));

                break;
            case 2:
                //背景
                llTabItem1.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem2.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem3.setBackground(getResources().getDrawable(R.mipmap.ic_find_bg_pre1));
                llTabItem4.setBackgroundColor(getResources().getColor(R.color.transparent));

                //颜色
                ivTabItem1.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_xunlian));
                ivTabItem2.setBackground(getResources().getDrawable(R.mipmap.icon_xiaojiang_nor));
                ivTabItem3.setBackground(getResources().getDrawable(R.mipmap.icon_sc_icon_pre));
                ivTabItem4.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_mine));

                //文字
                tvXunlianItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvXiaojiangItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvFindItemBottom.setTextColor(getResources().getColor(R.color.white));
                tvMineItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                llTab.setBackground(getResources().getDrawable(R.mipmap.ic_bg_bottom_white));


                break;
            case 3:
                //背景
                llTabItem1.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem2.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem3.setBackgroundColor(getResources().getColor(R.color.transparent));
                llTabItem4.setBackground(getResources().getDrawable(R.mipmap.ic_mine_pre));


                //颜色
                ivTabItem1.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_xunlian));
                ivTabItem2.setBackground(getResources().getDrawable(R.mipmap.icon_xiaojiang_nor));
                ivTabItem3.setBackground(getResources().getDrawable(R.mipmap.icon_shouye_find));
                ivTabItem4.setBackground(getResources().getDrawable(R.mipmap.icon_mine_icon_pre));

                //文字
                tvXunlianItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvXiaojiangItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvFindItemBottom.setTextColor(getResources().getColor(R.color.color_BBBBBB));
                tvMineItemBottom.setTextColor(getResources().getColor(R.color.white));
                llTab.setBackground(getResources().getDrawable(R.mipmap.ic_tab_bg2));
                llTab.setBackground(getResources().getDrawable(R.mipmap.ic_bg_bottom_white));


                break;
        }
    }

    ///检查蓝牙是否打开
    public void chikcBlue() {
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        //支持蓝牙模块
        if (blueadapter != null) {
            if (!blueadapter.isEnabled()) {
                openBlueDialog.show();
            } else {
//                mPresenter.connectBlue();
                showDialogManager();
            }
        } else {//不支持蓝牙模块
            ToastUtil.shortShow("该设备不支持蓝牙或没有蓝牙模块");
            showDialogManager();
        }
    }


    /**
     * 强制开启当前 Android 设备的 Bluetooth
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     */
    public static boolean turnOnBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }

    @Override
    public void getBlueDevice(BluetoothDevice device) {
        startConnectService(device);
    }


    private BluetoothDevice device;

    /**
     * 启动蓝牙监听服务
     */
    public void startConnectService(BluetoothDevice device) {
        if (StringUtils.isEmpty(device.getName())) {
            return;
        }
        if (maps.containsKey(device.getName())) {
            return;
        }
        maps.put(device.getName(), device);
        if (device.getName().startsWith("TH")) {
            BlueDeviceUtils.getInstance().cancleScan();
            Intent bindIntent = new Intent(this, UartService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
        }
    }


    /**
     * 别的页面请求连接
     *
     * @param device
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BluetoothDevice device) {
        this.device = device;
        if (blueService != null) {
            BlueDeviceUtils.getInstance().cancleScan();
            blueService.initialize();
            blueService.setHandler(handler);
            blueService.connect(device.getAddress());
        } else {
            BlueDeviceUtils.getInstance().cancleScan();
            Intent bindIntent = new Intent(this, UartService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CancleEvent event) {
        if (blueService != null) {
            blueService.disconnect();
            blueService.close();
            unbindService(connection);
        }
        App.connectDevice = null;
        App.blueService = null;
        blueService = null;
    }


    private UartService blueService;

    /**
     * service连接服务
     */
    private ServiceConnection connection = new ServiceConnection() {
        //可交互的后台服务与普通服务的不同之处，就在于这个connection建立起了两者的联系
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("获取服务的对象开始");
            UartService.LocalBinder localBinder = (UartService.LocalBinder) service;
            blueService = localBinder.getService();
            App.blueService = blueService;
            blueService.initialize();
            blueService.setHandler(handler);
            blueService.connect(device.getAddress());
        }
    };


    @Override
    protected void onDestroy() {
        if (blueService != null) {
            blueService.disconnect();
            blueService.close();
            unbindService(connection);
        }
        App.connectDevice = null;
        App.blueService = null;
        blueService = null;
        super.onDestroy();
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UartService.MESSAGE_DEVICE_NAME:

                    break;
                case UartService.MESSAGE_READ:
                    byte[] data = (byte[]) msg.obj;
                    BlueDataEvent event = new BlueDataEvent();
                    event.setData(data);
                    EventBus.getDefault().post(event);
                    break;
                case UartService.MESSAGE_TOAST:
                    showToast(msg.getData().getString(BluetoothChatService.TOAST));
                    break;
                case UartService.MESSAGE_STATE_CHANGE:
                    App.connectDevice = null;
                    switch (msg.arg1) {
                        case UartService.STATE_DISCONNECTED:
                            showToast("蓝牙连接已断开！");
                            if (SyncHistoryUtils.isSync) {
                                showToast("历史数据同步失败！");
                                SyncHistoryUtils.isSync = false;
                            }
                            //                            mPresenter.connectBlue();
                            break;
                        case UartService.STATE_CONNECTING:
//                            showToast("蓝牙连接中...");
                            break;
                        case UartService.STATE_CONNECTED:
                            App.connectDevice = device;
                            showToast("蓝牙已连接！");
                            break;
                    }
                    EventBus.getDefault().post(new BlueEvent(msg.arg1));
                    break;
                case UartService.NITIFI_SOURESS:   //监听一开始建立
                    EventBus.getDefault().post(new BlueEvent(UartService.NITIFI_SOURESS));
                    saveDevices();
                    break;
            }
        }
    };


    /**
     * 保存设备
     */
    private void saveDevices() {
        HttpServerImpl.saveDevices(0, App.connectDevice.getName(), App.connectDevice.getAddress()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                SyncHistoryUtils.getInstance(s).start();
            }

            @Override
            public void onFiled(String message) {
                ToastUtil.show(message);
            }
        });
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
