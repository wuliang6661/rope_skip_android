package com.tohabit.skip.ui.find.fragment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseActivity;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1810:12
 * desc   : 视频全屏播放
 * version: 1.0
 */
public class FullVideoActivity extends BaseActivity {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;

    String url;
    long startTime;

    private OrientationUtils orientationUtils;


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_full_video;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        url = getIntent().getStringExtra("url");
        startTime = getIntent().getIntExtra("startTime", 0);

        inviVideo();
    }


    /**
     * 初始化视频设置
     */
    private void inviVideo() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        videoPlayer.getFullscreenButton().setEnabled(true);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("startTime", videoPlayer.getCurrentPositionWhenPlaying());
                setResult(1, intent);
                finish();
            }
        });
        videoPlayer.getBackButton().setEnabled(true);
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("startTime", videoPlayer.getCurrentPositionWhenPlaying());
                setResult(1, intent);
                finish();
            }
        });
        videoPlayer.setUp(url, false, "");
        videoPlayer.startPlayLogic();
        videoPlayer.setSeekOnStart(startTime);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent();
                intent.putExtra("startTime", videoPlayer.getCurrentPositionWhenPlaying());
                setResult(1, intent);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
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
            if (orientationUtils != null)
                orientationUtils.releaseListener();
        }
        super.onDestroy();
    }

    private GSYVideoPlayer getCurPlay() {
        if (videoPlayer.getFullWindowPlayer() != null) {
            return videoPlayer.getFullWindowPlayer();
        }
        return videoPlayer;
    }

}
