package com.tohabit.skip.ui.young.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.BaseResult;
import com.tohabit.skip.pojo.po.PkUserBO;
import com.tohabit.skip.ui.train.activity.TainMainActivity;
import com.tohabit.skip.ui.young.websocket.WebSocketUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 开始pk界面
 */
public class PKStartActivity extends BaseActivity {

    @BindView(R.id.tv_battery_fragment_train_main)
    AppCompatTextView tvBatteryFragmentTrainMain;
    @BindView(R.id.blue_state_img)
    AppCompatImageView blueStateImg;
    @BindView(R.id.tv_blue_connect_statusfragment_train_main)
    AppCompatTextView tvBlueConnectStatusfragmentTrainMain;
    @BindView(R.id.blue_layout)
    LinearLayout blueLayout;
    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.duanwei_text)
    ImageView duanweiText;
    @BindView(R.id.duanwei_img)
    ImageView duanweiImg;
    @BindView(R.id.progress_bar)
    LinearLayout progressBar;
    @BindView(R.id.duishou_user_img)
    RoundedImageView duishouUserImg;
    @BindView(R.id.duishou_user_name)
    TextView duishouUserName;
    @BindView(R.id.duishou_duanwei_img1)
    ImageView duishouDuanweiText;
    @BindView(R.id.duishou_duanwei_img)
    ImageView duishouDuanweiImg;
    @BindView(R.id.duishou_duanwei)
    RelativeLayout duishouDuanwei;
    @BindView(R.id.duishou_layout)
    LinearLayout duishouLayout;
    @BindView(R.id.bt_text)
    TextView btText;
    @BindView(R.id.pk_timer)
    TextView pkTimer;
    @BindView(R.id.start_bt)
    RelativeLayout startBt;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.progress_img)
    ImageView progressImg;

    private int pkChangCiId;

    //是否去跳绳
    private boolean isGoTiaosheng = false;

    /**
     * 匹配到的用户信息
     */
    BaseResult<PkUserBO> userBO;

    /**
     * 匹配的场次跳绳时间
     */
    private int maxTime;

    /**
     * 场次标题
     */
    private String title;

    /**
     * 对方是否已准备
     */
    private boolean isZhunBei;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_pk_start;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("PK挑战");

        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        userName.setText(App.xIaoJiangBO.getNickName());
        Glide.with(this).load(App.xIaoJiangBO.getIcon()).into(duanweiImg);
        Glide.with(this).load(App.xIaoJiangBO.getImage()).into(duanweiText);

        pkChangCiId = getIntent().getExtras().getInt("id");
        maxTime = getIntent().getExtras().getInt("maxTime");
        title = getIntent().getExtras().getString("title");

        RotateAnimation animation;
        int magnify = 10000;
        int toDegrees = 360;
        int duration = 1000;
        toDegrees *= magnify;
        duration *= magnify;
        animation = new RotateAnimation(0,toDegrees,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(duration);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        progressImg.startAnimation(animation);

        //开始匹配
        Map<String, Object> params = new HashMap<>();
        params.put("pkChallengeId", pkChangCiId);
        params.put("contentText", 0);
        sendPk(new Gson().toJson(params));
    }

    /**
     * 开始PK
     */
    private void sendPk(String msg) {
        WebSocketUtils utils = WebSocketUtils.getInstance();
        utils.setOnNotifiListener(new WebSocketUtils.onNotifiListener() {
            @Override
            public void onNotifi(String message) {
                try {
                    userBO = new Gson().fromJson(message, new TypeToken<BaseResult<PkUserBO>>() {
                    }.getType());
                    if (userBO.surcess()) {
                        if ("PK进行中".equals(userBO.getMsg())) {
                            handler.sendEmptyMessage(0x33);
                        } else if ("对方已准备".equals(userBO.getMsg())) {
                            showToast(userBO.getMsg());
                        } else {
                            handler.sendEmptyMessage(0x11);
                        }
                    } else {
                        handler.sendEmptyMessage(0x22);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        utils.sendMsg(msg);
    }


    //    @OnClick(R.id.back)
    public void back() {
        if (userBO == null) {
            //取消匹配
            Map<String, Object> params = new HashMap<>();
            params.put("pkChallengeId", pkChangCiId);
            params.put("contentText", 1);
            sendPk(new Gson().toJson(params));
        } else {
            //取消PK
            Map<String, Object> params = new HashMap<>();
            params.put("pkChallengeId", pkChangCiId);
            params.put("contentText", 3);
            sendPk(new Gson().toJson(params));
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    pkUser(userBO.getData());
                    break;
                case 0x22:
                    //取消PK
                    Map<String, Object> params = new HashMap<>();
                    params.put("pkChallengeId", pkChangCiId);
                    params.put("contentText", 3);
                    sendPk(new Gson().toJson(params));
                    showToast(userBO.getMsg());
                    finish();
                    break;
                case 0x33:
                    isGoTiaosheng = true;
                    Bundle bundle = new Bundle();
                    bundle.putString("trainLength", maxTime + "");
                    bundle.putInt("type", 2);
                    bundle.putString("title", title);
                    bundle.putInt("pkChangCiId", pkChangCiId);
                    Intent intent = new Intent();
                    intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.ROPE_SKIP_RESULTS);
                    intent.putExtras(bundle);
                    intent.setClass(PKStartActivity.this, TainMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };


    /**
     * 匹配到人，界面更新
     */
    private void pkUser(PkUserBO userBO) {
        progressBar.setVisibility(View.GONE);
        duishouLayout.setVisibility(View.VISIBLE);
        duishouUserName.setText(userBO.getNickName());
        Glide.with(this).load(userBO.getHeadImage()).into(duishouUserImg);
        Glide.with(this).load(userBO.getIcon()).into(duishouDuanweiImg);
        startBt.setVisibility(View.VISIBLE);
        Glide.with(this).load(userBO.getImage()).into(duishouDuanweiText);
        timer.start();
    }


    /**
     * 开始pk倒计时
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            pkTimer.setText("（" + (millisUntilFinished / 1000) + "S）");
        }

        @Override
        public void onFinish() {
            finish();
        }
    };


    @OnClick(R.id.start_bt)
    public void startPk() {
        //开始PK
        startBt.setEnabled(false);
        btText.setTextColor(Color.parseColor("#ffffff"));
        pkTimer.setTextColor(Color.parseColor("#ffffff"));
        Map<String, Object> params = new HashMap<>();
        params.put("pkChallengeId", pkChangCiId);
        params.put("contentText", 2);
        sendPk(new Gson().toJson(params));
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


    @Override
    protected void onPause() {
        super.onPause();
        WebSocketUtils.getInstance().setOnNotifiListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果不是去跳绳界面，则取消匹配断开连接
        handler.removeCallbacksAndMessages(null);
        if (!isGoTiaosheng) {
            back();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

}
