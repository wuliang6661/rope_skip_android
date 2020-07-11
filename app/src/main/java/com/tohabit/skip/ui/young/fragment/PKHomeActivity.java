package com.tohabit.skip.ui.young.fragment;

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
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.event.model.BlueDataEvent;
import com.tohabit.skip.event.model.BlueEvent;
import com.tohabit.skip.pojo.po.PkChangCiBO;
import com.tohabit.skip.pojo.po.XIaoJiangBO;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.SearchActivty;
import com.tohabit.skip.ui.XieYiActivity;
import com.tohabit.skip.ui.young.websocket.WebSocketUtils;
import com.tohabit.skip.utils.SyncHistoryUtils;
import com.tohabit.skip.utils.blue.cmd.BleCmd;
import com.tohabit.skip.utils.blue.cmd.RequstBleCmd;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

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
        connectWebSocket();
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
    public void onClickBlue() {
        gotoActivity(SearchActivty.class, false);
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
                if (pkChangCiBOS.get(position).getValue() > Integer.parseInt(App.xIaoJiangBO.getPkValue())) {
                    showToast("您的PK值不足!");
                    return;
                }
                if (App.isConnect()) {
                    if (SyncHistoryUtils.isSync) {
                        showToast("数据同步中，请稍后");
                        return;
                    }
                    if (WebSocketUtils.getInstance().getState()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", pkChangCiBOS.get(position));
//                        bundle.putInt("id", pkChangCiBOS.get(position).getId());
//                        bundle.putInt("maxTime", pkChangCiBOS.get(position).getMaxTime());
//                        bundle.putString("title", pkChangCiBOS.get(position).getTitle());
//                        bundle.putInt("mode", pkChangCiBOS.get(position).getMode());
//                        bundle.putInt("maxNum", pkChangCiBOS.get(position).getMaxNum());
//                        bundle.putInt("timeOut", pkChangCiBOS.get(position).getTimeOut());
                        gotoActivity(PKStartActivity.class, bundle, false);
                    } else {
                        showToast("游戏服务器正在连接！");
                    }
                } else {
                    showToast("请先连接跳绳设备！");
                }
            }
        });
        pkRecycle.setAdapter(adapter);
    }


    @OnClick(R.id.btn_album)
    public void goGuize() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 3);
        gotoActivity(XieYiActivity.class, bundle, false);
    }

    /**
     * 开始连接websocket
     */
    private void connectWebSocket() {
        WebSocketUtils utils = WebSocketUtils.getInstance();
        utils.setOnNotifiListener(new WebSocketUtils.onNotifiListener() {
            @Override
            public void onNotifi(String message) {

            }
        });
        utils.connect();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSocketUtils.getInstance().closeConnect();
    }
}
