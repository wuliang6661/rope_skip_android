package com.tohabit.skip.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sak.ultilviewlib.adapter.BaseHeaderAdapter;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;

/**
 * Created by engineer on 2017/4/30.
 */

public class TraditionHeaderAdapter extends BaseHeaderAdapter {
    private ProgressBar pull_to_refresh_image1;
    private TextView pull_to_refresh_text;
    private ImageView refreshImg;

    //
    private RotateAnimation mFlipAnimation;

    public TraditionHeaderAdapter(Context context) {
        super(context);
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
    }

    @Override
    public View getHeaderView() {
        View mView = mInflater.inflate(R.layout.tradition_header_refresh_layout, null, false);
        pull_to_refresh_image1 = (ProgressBar) mView.findViewById(R.id.pull_to_refresh_image);
        pull_to_refresh_text = (TextView) mView.findViewById(R.id.pull_to_refresh_text);
        refreshImg = mView.findViewById(R.id.refresh_img);
        return mView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        pull_to_refresh_text.setText("");
//        refreshImg.clearAnimation();
//        refreshImg.startAnimation(mFlipAnimation);
    }

    @Override
    public void releaseViewToRefresh(int deltaY) {
        pull_to_refresh_text.setVisibility(View.GONE);
        pull_to_refresh_text.setText("释放立即刷新");
    }

    @Override
    public void headerRefreshing() {
        pull_to_refresh_image1.setVisibility(View.GONE);
        pull_to_refresh_text.setVisibility(View.GONE);
        refreshImg.setVisibility(View.VISIBLE);
        if (App.xIaoJiangBO.getSex() == 0) {
            refreshImg.setImageResource(R.drawable.mei_tuan_loading);
        } else {
            refreshImg.setImageResource(R.drawable.refresh_nv);
        }
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) refreshImg.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void headerRefreshComplete() {
        pull_to_refresh_image1.setVisibility(View.GONE);
        pull_to_refresh_image1.clearAnimation();
        pull_to_refresh_text.setVisibility(View.VISIBLE);
        pull_to_refresh_text.setText("");
    }
}
