package com.habit.star.ui.mine.fragment;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;

import butterknife.BindView;

public class TakeRopeSkipActivty extends BaseActivity {

    @BindView(R.id.setting)
    LinearLayout setting;
    @BindView(R.id.tv_dianliang)
    AppCompatTextView tvDianliang;
    @BindView(R.id.blue_state_img)
    AppCompatImageView blueStateImg;
    @BindView(R.id.blue_state_text)
    AppCompatTextView blueStateText;
    @BindView(R.id.blue_layout)
    LinearLayout blueLayout;
    @BindView(R.id.iv_fresh_fragment_train_main)
    AppCompatImageView ivFreshFragmentTrainMain;
    @BindView(R.id.time_text)
    TextView timeText;
    @BindView(R.id.take_num)
    AppCompatTextView takeNum;
    @BindView(R.id.bt_start)
    TextView btStart;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_take_ropeskip;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        takeNum.setTypeface(App.getInstance().tf);
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
