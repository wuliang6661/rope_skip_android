package com.habit.star.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.interfaces.ButtonClickListener;

public class NormalDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int mType;//默认是推荐更新
    private ButtonClickListener buttonClickListener;
    private String mContent = "";
    private String mLeft = "取消";
    private String mRight = "确定";
    private int mLeftColor = R.color.black_666666;
    private int mRightColor = R.color.black_666666;
    private boolean isShowTitle = false;

    public NormalDialog(Context context, int type, String content, String left, String right, int leftColor, int rightColor, boolean isShowTitle) {
        //更改样式为透明样式
        super(context, R.style.LocatonDialogStyle);
        this.context = context;
        this.mType = type;
        mContent = content;
        mLeft = left;
        mRight = right;
        mLeftColor = leftColor;
        mRightColor = rightColor;
        this.isShowTitle = isShowTitle;
    }

    public NormalDialog(Context context, int type, String content, boolean isShowTitle) {
        //更改样式为透明样式
        super(context, R.style.LocatonDialogStyle);
        this.context = context;
        this.mType = type;
        mContent = content;
        this.isShowTitle = isShowTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //加载dialog的布局
        setContentView(R.layout.dialog_normal);

        initView();
    }

    private void initView() {

        if (isShowTitle) {
            findViewById(R.id.ll_pop_no_title).setVisibility(View.GONE);
            findViewById(R.id.ll_pop_show_title).setVisibility(View.VISIBLE);
            initShowTitleView();
        } else {
            findViewById(R.id.ll_pop_no_title).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_pop_show_title).setVisibility(View.GONE);
            initNoTitleView();
        }
    }

    private void initNoTitleView() {
        TextView tvMsg = findViewById(R.id.tv_msg);
        tvMsg.setText(mContent);
        //取消
        TextView tvCancle = findViewById(R.id.tv_cancle);
        tvCancle.setText(mLeft);
        tvCancle.setTextColor(context.getResources().getColor(mLeftColor));
        //退出
        TextView tvOut = findViewById(R.id.tv_logout);
        tvOut.setText(mRight);
        tvOut.setTextColor(context.getResources().getColor(mRightColor));

        tvCancle.setOnClickListener(this);
        tvOut.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    private void initShowTitleView() {
        TextView tvMsgContent = findViewById(R.id.tv_msg_content);
        tvMsgContent.setText(mContent);
        //left
        TextView tvLeft = findViewById(R.id.tv_left);
        tvLeft.setText(mLeft);
        tvLeft.setTextColor(context.getResources().getColor(mLeftColor));
        //right
        TextView tvRight = findViewById(R.id.tv_right);
        tvRight.setText(mRight);
        tvRight.setTextColor(context.getResources().getColor(mRightColor));

        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    public void setOnButtonClickListener(ButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle://关闭按钮
                if (buttonClickListener != null) {
                    buttonClickListener.closeDialog();
                }
                break;
            case R.id.tv_logout://确定
                if (buttonClickListener != null) {
                    buttonClickListener.sure(mType);
                }
                break;
            case R.id.tv_left:
                if (buttonClickListener != null) {
                    buttonClickListener.closeDialog();
                }
                break;
            case R.id.tv_right:
                if (buttonClickListener != null) {
                    buttonClickListener.sure(mType);
                }
                break;
            default:
                break;
        }
    }

}
