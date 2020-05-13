package com.habit.star.ui.mine.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyQrCodeActivity extends BaseActivity {

    @BindView(R.id.user_img)
    CircleImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.qr_img)
    ImageView qrImg;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_my_qrcode;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        setTitleText("我的二维码");
        goBack();
        userName.setText(App.userBO.getNickName());
        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        getQrCode();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    private void getQrCode() {
        showProgress(null);
        HttpServerImpl.getQrCode().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Glide.with(MyQrCodeActivity.this).load(s).into(qrImg);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

}
