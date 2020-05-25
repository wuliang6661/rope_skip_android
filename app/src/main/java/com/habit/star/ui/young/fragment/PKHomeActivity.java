package com.habit.star.ui.young.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;
import com.habit.star.event.model.BlueDataEvent;
import com.habit.star.event.model.BlueEvent;
import com.habit.star.pojo.po.PkChangCiBO;
import com.habit.star.pojo.po.XIaoJiangBO;
import com.habit.star.service.UartService;
import com.habit.star.ui.SearchActivty;
import com.habit.star.ui.XieYiActivity;
import com.habit.star.utils.blue.cmd.BleCmd;
import com.habit.star.utils.blue.cmd.RequstBleCmd;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * pk大厅页面
 */
public class PKHomeActivity extends BaseActivity {

    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.tv_battery_fragment_train_main)
    AppCompatTextView tvBatteryFragmentTrainMain;
    @BindView(R.id.blue_state_img)
    AppCompatImageView ivConnnetState;
    @BindView(R.id.tv_blue_connect_statusfragment_train_main)
    AppCompatTextView tvConnectState;
    @BindView(R.id.my_pk_img)
    ImageView myPkImg;
    @BindView(R.id.leiji_num)
    TextView leijiNum;
    @BindView(R.id.my_pk_name)
    TextView myPkName;
    @BindView(R.id.pk_recycle)
    RecyclerView pkRecycle;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_pk_home;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("PK挑战");
        btnAlbum.setTextColor(Color.parseColor("#7EC7F5"));
        btnAlbum.setText("规则说明");
        btnAlbum.setVisibility(View.VISIBLE);

        pkRecycle.setLayoutManager(new LinearLayoutManager(this));
        pkRecycle.setNestedScrollingEnabled(false);
        getYoungGeneralInfo();
        getPkList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        freshView();
        getDeviceQc();
    }


    private void freshView() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            tvConnectState.setText("已连接");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_home19);
        } else {
            tvConnectState.setText("已断开");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueEvent event) {
        if (event.isConnect == UartService.STATE_CONNECTED) {
            tvConnectState.setText("已连接");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_home19);
        } else if (event.isConnect == UartService.STATE_CONNECTING) {
            tvConnectState.setText("设备连接中...");
            ivConnnetState.setBackgroundResource(R.mipmap.ic_connect_state_disconnect);
        } else if (event.isConnect == UartService.NITIFI_SOURESS) {  //监听已经开始建立
            getDeviceQc();
        } else {
            tvConnectState.setText("已断开");
        }
    }


    /**
     * 获取电量
     */
    private void getDeviceQc() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            UartService.COUNT_OPENTION = 0x11;
            App.blueService.writeCharacteristic1Info(RequstBleCmd.createGetEQCmd().getCmdByte());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BlueDataEvent event) {
        BleCmd.Builder builder = new BleCmd.Builder().setBuilder(event.getData());
        if (UartService.COUNT_OPENTION == 0x11) {  //电量
            getDeviceQcAndType(String.valueOf(builder.getDataBody()[0]), String.valueOf(builder.getDataBody()[1]));
        }
    }

    public void getDeviceQcAndType(String dianliang, String type) {
        tvBatteryFragmentTrainMain.setText(dianliang + "%");
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

    @OnClick(R.id.blue_layout)
    public void onClickBlue(){
        gotoActivity(SearchActivty.class,false);
    }


    /**
     * 查询小将信息
     */
    private void getYoungGeneralInfo() {
        HttpServerImpl.getYoungGeneralInfo().subscribe(new HttpResultSubscriber<XIaoJiangBO>() {
            @Override
            public void onSuccess(XIaoJiangBO s) {
                App.xIaoJiangBO = s;
                if (s != null) {
                    Glide.with(PKHomeActivity.this).load(s.getIcon()).into(myPkImg);
                    myPkName.setText(s.getPkName());
                    leijiNum.setText(s.getChallengeSuccessNum() + "次");
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 查询PK场次信息
     */
    private void getPkList() {
        HttpServerImpl.getPkChallengeList().subscribe(new HttpResultSubscriber<List<PkChangCiBO>>() {
            @Override
            public void onSuccess(List<PkChangCiBO> pkChangCiBOS) {
                setAdapter(pkChangCiBOS);
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
    private void setAdapter(List<PkChangCiBO> pkChangCiBOS) {
        LGRecycleViewAdapter<PkChangCiBO> adapter = new LGRecycleViewAdapter<PkChangCiBO>(pkChangCiBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_pk_home;
            }

            @Override
            public void convert(LGViewHolder holder, PkChangCiBO pkChangCiBO, int position) {
                holder.setText(R.id.pk_name, pkChangCiBO.getTitle());
                holder.setText(R.id.pk_message, pkChangCiBO.getTitle() + "每次挑战所需PK值" + pkChangCiBO.getValue());
            }
        };
        adapter.setOnItemClickListener(R.id.bt_tiaozhan, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
               gotoActivity(PKStartActivity.class,false);
            }
        });
        pkRecycle.setAdapter(adapter);
    }


    @OnClick(R.id.btn_album)
    public void goGuize(){
        Bundle bundle = new Bundle();
        bundle.putInt("type",3);
        gotoActivity(XieYiActivity.class,bundle,false);
    }
}
