package com.habit.star.ui.young.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;

/**
 * 训练讲解页面
 */
public class VideoExplainActivity extends BaseActivity {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    @BindView(R.id.bt_look_jiangjie)
    ImageView btLookJiangjie;
    @BindView(R.id.down_time)
    TextView downTime;
    @BindView(R.id.tui_img)
    ImageView tuiImg;
    @BindView(R.id.video_tui)
    RelativeLayout videoTui;
    @BindView(R.id.pause_img)
    ImageView pauseImg;
    @BindView(R.id.video_pause)
    RelativeLayout videoPause;
    @BindView(R.id.jin_img)
    ImageView jinImg;
    @BindView(R.id.video_jin)
    RelativeLayout videoJin;


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_video_explain;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
//        String title = getIntent().getExtras().getString("title");
        setTitleText("高度训练");
        int id = getIntent().getExtras().getInt("id");
        inviVideo();
        getImprovePlan(id);
    }


    /**
     * 初始化视频设置
     */
    private void inviVideo() {
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(false);
        videoPlayer.getFullscreenButton().setEnabled(false);
        videoPlayer.setIsTouchWigetFull(false);
        videoPlayer.setHideKey(false);
    }


    /**
     * 查询
     */
    private void getImprovePlan(int id) {
        HttpServerImpl.getImprovePlan(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {

            }
        });
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
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

}
