package com.habit.star.ui.find.fragment;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habit.star.R;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.HuodongBO;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 活动详情
 */
public class HuodongDetailsActivity extends BaseActivity {

    @BindView(R.id.bt_commit)
    TextView btCommit;
    @BindView(R.id.huodong_img)
    ImageView huodongImg;
    @BindView(R.id.huodong_title)
    TextView huodongTitle;
    @BindView(R.id.huodong_time)
    TextView huodongTime;
    @BindView(R.id.huodong_message)
    TextView huodongMessage;

    private HuodongBO huodongBO;

    private int huodongStatus = 0;  //默认进行中

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_huodong_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("精选活动");
        huodongBO = (HuodongBO) getIntent().getExtras().getSerializable("huodong");
        huodongStatus = getIntent().getExtras().getInt("huodongStatus");
        Glide.with(this).load(huodongBO.getImage()).into(huodongImg);
        huodongTitle.setText(huodongBO.getTitle());
        huodongTime.setText("报名时间： " + huodongBO.getTimeBucket());
        huodongMessage.setText(Html.fromHtml(huodongBO.getContent()));
        if (huodongStatus == 0) {
            btCommit.setEnabled(true);
            btCommit.setText("马上报名");
        } else if (huodongStatus == 1) {
            btCommit.setEnabled(false);
            btCommit.setText("活动已报名");
        } else {
            btCommit.setEnabled(false);
            btCommit.setText("活动已结束");
        }
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


    @OnClick(R.id.bt_commit)
    public void commit() {

    }

}
