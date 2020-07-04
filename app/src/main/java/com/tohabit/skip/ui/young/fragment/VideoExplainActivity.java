package com.tohabit.skip.ui.young.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.ExplainDetailsBO;
import com.tohabit.skip.ui.find.fragment.FullVideoActivity;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

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

    private ExplainDetailsBO detailsBO;

    private int id;
    private int xunlianId;


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
        id = getIntent().getExtras().getInt("id");
        xunlianId = getIntent().getExtras().getInt("xunlianId");
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
        videoPlayer.getFullscreenButton().setEnabled(true);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsBO == null) {
                    return;
                }
                Intent intent = new Intent(VideoExplainActivity.this, FullVideoActivity.class);
                intent.putExtra("url", detailsBO.getVideoUrl());
                intent.putExtra("startTime", videoPlayer.getCurrentPositionWhenPlaying());
                startActivityForResult(intent, 1);
            }
        });
        videoPlayer.setNeedLockFull(true);
        videoPlayer.setHideKey(false);

        videoPlayer.setGSYVideoProgressListener(new GSYVideoProgressListener() {
            @Override
            public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                String time = TimeUtils.millis2String(currentPosition + 1000,
                        new SimpleDateFormat("mm:ss"));
                downTime.setText(time);
                if (progress >= App.userBO.getPercent() * 100 - 10 && !isWancheng) {
                    completeTrainPlan(xunlianId + "");
                    isWancheng = true;
                }
            }
        });
    }

    boolean isWancheng = false;

    /**
     * 查询
     */
    private void getImprovePlan(int id) {
        HttpServerImpl.getImprovePlan(id).subscribe(new HttpResultSubscriber<ExplainDetailsBO>() {
            @Override
            public void onSuccess(ExplainDetailsBO s) {
                detailsBO = s;
                showUIData();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置界面显示
     */
    private void showUIData() {
        setTitleText(detailsBO.getVideoTitle());
        videoPlayer.setUp(detailsBO.getVideoUrl(), true, "");
        videoPlayer.startPlayLogic();

        long time = videoPlayer.getDuration();
        downTime.setText(TimeUtils.millis2String(time, new SimpleDateFormat("mm`ss``")));
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
        if (videoPlayer != null) {
            getCurPlay().release();
        }
        super.onDestroy();
    }

    private GSYVideoPlayer getCurPlay() {
        if (videoPlayer.getFullWindowPlayer() != null) {
            return videoPlayer.getFullWindowPlayer();
        }
        return videoPlayer;
    }


    @OnClick(R.id.bt_look_jiangjie)
    public void jiangjie() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", detailsBO);
        gotoActivity(ExplainDetailsActivity.class, bundle, false);
    }


    @OnClick({R.id.video_pause, R.id.video_jin, R.id.video_tui})
    public void clickVideoController(View view) {
        switch (view.getId()) {
            case R.id.video_pause:
                videoPlayer.getStartButton().performClick();
                break;
            case R.id.video_jin:
                videoPlayer.seekTo(videoPlayer.getCurrentPositionWhenPlaying() + 1000);
                break;
            case R.id.video_tui:
                videoPlayer.seekTo(videoPlayer.getCurrentPositionWhenPlaying() - 1000);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                long startTime = data.getIntExtra("startTime", 0);
                videoPlayer.setUp(detailsBO.getVideoUrl(), true, "");
                videoPlayer.startPlayLogic();
                videoPlayer.setSeekOnStart(startTime);
                break;
        }
    }


    /**
     * 完成训练计划
     */
    public void completeTrainPlan(String id) {
        HttpServerImpl.completeTrainPlan(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
            }
        });
    }


}
