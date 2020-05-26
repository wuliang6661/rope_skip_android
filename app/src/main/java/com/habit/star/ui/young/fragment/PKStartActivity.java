package com.habit.star.ui.young.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.BaseResult;
import com.habit.star.pojo.po.PkUserBO;
import com.habit.star.ui.train.activity.TainMainActivity;
import com.habit.star.ui.young.websocket.WebSocketUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

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
    TextView duanweiText;
    @BindView(R.id.duanwei_img)
    ImageView duanweiImg;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.duishou_user_img)
    RoundedImageView duishouUserImg;
    @BindView(R.id.duishou_user_name)
    TextView duishouUserName;
    @BindView(R.id.duishou_duanwei_text)
    TextView duishouDuanweiText;
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

    private int pkChangCiId;

    //是否去跳绳
    private boolean isGoTiaosheng = false;

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
        duanweiText.setText(App.xIaoJiangBO.getPkName());

        pkChangCiId = getIntent().getExtras().getInt("id");

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
                    BaseResult<PkUserBO> userBO = new Gson().fromJson(message, new TypeToken<BaseResult<PkUserBO>>() {}.getType());
                    if(userBO.surcess()){
                        pkUser(userBO.getData());
                    }else{
                        showToast(userBO.getMsg());
                        finish();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        utils.sendMsg(msg);
    }


    /**
     * 匹配到人，界面更新
     */
    private void pkUser(PkUserBO userBO){
       progressBar.setVisibility(View.GONE);
       duishouLayout.setVisibility(View.VISIBLE);
       duishouUserName.setText(userBO.getNickName());
       Glide.with(this).load(userBO.getHeadImage()).into(duishouUserImg);
       Glide.with(this).load(userBO.getIcon()).into(duishouDuanweiImg);
       startBt.setVisibility(View.VISIBLE);
//       duishouDuanweiText.setText(userBO.get);
        timer.start();
    }


    /**
     * 开始pk倒计时
     */
    private CountDownTimer timer = new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            pkTimer.setText("（" + (millisUntilFinished/1000) + "S）");
        }

        @Override
        public void onFinish() {
            isGoTiaosheng = true;
            Bundle bundle = new Bundle();
            bundle.putInt("id", 0);
            Intent intent = new Intent();
            intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.ROPE_SKIP_RESULTS);
            intent.putExtras(bundle);
            intent.setClass(PKStartActivity.this, TainMainActivity.class);
            startActivity(intent);
        }
    };



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
    protected void onDestroy() {
        super.onDestroy();
        //如果不是去跳绳界面，则取消匹配断开连接
        if (!isGoTiaosheng) {
            Map<String, Object> params = new HashMap<>();
            params.put("pkChallengeId", pkChangCiId);
            params.put("contentText", 1);
            sendPk(new Gson().toJson(params));
        }
        if(timer != null){
            timer.cancel();
        }
    }
}
