package com.habit.star.ui.young.fragment;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;
import com.makeramen.roundedimageview.RoundedImageView;

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

}
