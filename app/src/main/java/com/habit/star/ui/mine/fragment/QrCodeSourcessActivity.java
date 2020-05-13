package com.habit.star.ui.mine.fragment;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.UserBO;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class QrCodeSourcessActivity extends BaseActivity {

    @BindView(R.id.iv_head_fragment_personal_data)
    CircleImageView ivHeadFragmentPersonalData;
    @BindView(R.id.tv_title_fragment_personal_data)
    AppCompatTextView tvTitleFragmentPersonalData;
    @BindView(R.id.user_name)
    LilayItemClickableWithHeadImageTopDivider userName;
    @BindView(R.id.user_phone)
    LilayItemClickableWithHeadImageTopDivider userPhone;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;

    UserBO userBO;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_qrcode_sourcess;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("扫码结果");
        userBO = (UserBO) getIntent().getExtras().getSerializable("user");
        Glide.with(this).load(userBO.getImage()).into(ivHeadFragmentPersonalData);
        tvTitleFragmentPersonalData.setText("ID " + userBO.getUserCode());
        userName.setItemContent(userBO.getNickName());
        userPhone.setItemContent(userBO.getPhone());
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


    @OnClick(R.id.btn_commit)
    public void btClick() {
        showProgress(null);
        HttpServerImpl.addFamilyUser(userBO.getId()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                showToast("邀请成功");
                finish();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

}
