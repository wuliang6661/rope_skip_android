package com.habit.star.ui.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.habit.commonlibrary.widget.NoScrollViewPager;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseActivity;
import com.habit.star.common.adapter.CommonFragmentAdapter;
import com.habit.star.presenter.MainPresenter;
import com.habit.star.presenter.contract.MainContract;
import com.habit.star.ui.find.fragment.FindMainFragment;
import com.habit.star.ui.mine.activity.MineMainActivity;
import com.habit.star.ui.mine.fragment.MineFragment;
import com.habit.star.ui.train.fragment.TranHomeFragment;
import com.habit.star.ui.young.fragment.YoungHomeFragment;
import com.habit.star.utils.DensityUtil;
import com.habit.star.utils.ToastUtil;
import com.habit.star.utils.blue.BlueUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建日期：2020-01-21 17:04
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MainActivity.java
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
        initDialog();
        chikcBlue();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlueUtils.getInstance().disConnectBlue();
    }

    /**
     * 退到后台
     */
    public void moveToStack() {
//        TRouter.mCurActivityExtra=null;
        moveTaskToBack(true);
    }

    void initDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.layout_fragment_dialog_open_blue, null);
        View buyDialogView = getLayoutInflater().inflate(R.layout.layout_fragment_buy_device_dialog, null);
        View receiveYoungDialogView = getLayoutInflater().inflate(R.layout.layout_fragment_receive_young_dialog, null);
        receiveYoungDialogView.findViewById(R.id.tv_create_monkey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog.hide();
                buyDialog.show();
                Intent intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_PERFECT_INFORMATION);
                intent.setClass(MainActivity.this, MineMainActivity.class);
                startActivity(intent);
            }
        });
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
            }
        });
        dialogView.findViewById(R.id.tv_refuse_layout_fragment_dialog_open_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBlueDialog.hide();
            }
        });

        dialogView.findViewById(R.id.tv_pass_layout_fragment_dialog_open_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turnOnBluetooth()) {
                    mPresenter.connectBlue();
                } else {
                    showToast("关闭蓝牙可能会影响跳绳功能！");
                }
                openBlueDialog.hide();
                openBlueDialog.dismiss();
                buyDialog.show();
            }
        });
        openBlueDialog = new MaterialDialog.Builder(this)
                .customView(dialogView, false)
                .build();
//        buyDialog = new MaterialDialog.Builder(this)
//                .canceledOnTouchOutside(false)
//                .backgroundColor(getResources().getColor(R.color.transparent))
//                .customView(buyDialogView, false)
//                .cancelable(false)
//                .build();
//        buyDialog.getWindow().setBackgroundDrawable(setDialogBack(16, 16, 16, 16, 00, 0, 0, 0));


        buyDialog = new Dialog(this, R.style.MaterialDialogSheet);
        buyDialog.setContentView(buyDialogView);
        buyDialog.setCancelable(true);
        Window window1 = buyDialog.getWindow();
        WindowManager.LayoutParams paramsWindow1 = window1.getAttributes();
        paramsWindow1.width = window1.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow1.height = window1.getWindowManager().getDefaultDisplay().getHeight() - DensityUtil.dp2px(mContext, 80);
        window1.setAttributes(paramsWindow1);


        createDialog = new Dialog(this, R.style.MaterialDialogSheet);
        createDialog.setContentView(receiveYoungDialogView);
        createDialog.setCancelable(true);
        Window window = createDialog.getWindow();
        WindowManager.LayoutParams paramsWindow = window.getAttributes();
        paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow.height = window.getWindowManager().getDefaultDisplay().getHeight() - DensityUtil.dp2px(mContext, 80);
        window.setAttributes(paramsWindow);
    }


    public Drawable setDialogBack(float cTopLeft, float cTopRight, float cBottomLeft, float cBottomRight, int a, int r, int g, int b) {
        float outRectr[] = new float[]{cTopLeft, cTopLeft, cTopRight, cTopRight, cBottomRight, cBottomRight, cBottomLeft, cBottomLeft};
        RoundRectShape rectShape = new RoundRectShape(outRectr, null, null);
        ShapeDrawable normalDrawable = new ShapeDrawable(rectShape);
        normalDrawable.getPaint().setColor(Color.argb(a, r, g, b));
        return normalDrawable;
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
//                buyDialog.show();
                mPresenter.connectBlue();
                createDialog.show();
            }
        } else {//不支持蓝牙模块
            ToastUtil.shortShow("该设备不支持蓝牙或没有蓝牙模块");

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

}
