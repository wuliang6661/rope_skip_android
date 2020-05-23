package com.habit.star.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 所有协议的界面
 */
public class XieYiActivity extends BaseActivity {

    @BindView(R.id.xieyi_text)
    TextView xieyiText;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_xieyi;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        /**
         * 0 用户服务协议   1 隐私政策  2  服务条款及隐私政策说明   3 pk 规则说明
         */
        int type = getIntent().getExtras().getInt("type");
        switch (type) {
            case 0:
                setTitleText("用户服务协议");
                xieyiText.setText(getString(R.string.yonghuxieyi));
                break;
            case 1:
                setTitleText("隐私政策");
                xieyiText.setText(getString(R.string.yinsi));
                break;
            case 2:
                setTitleText("协议和条款");
                xieyiText.setText(getString(R.string.xieyiandtiaokuan));
                break;
            case 3:
                setTitleText("规则说明");
                xieyiText.setText(getString(R.string.pk_shuoming));
                break;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
