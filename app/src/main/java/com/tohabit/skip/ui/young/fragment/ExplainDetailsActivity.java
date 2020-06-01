package com.tohabit.skip.ui.young.fragment;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.ExplainDetailsBO;
import com.tohabit.skip.utils.ImageGetterUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2816:25
 * desc   : 视频讲解详情
 * version: 1.0
 */
public class ExplainDetailsActivity extends BaseActivity {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    @BindView(R.id.video_title)
    TextView videoTitle;
    @BindView(R.id.xunlian_shichang)
    TextView xunlianShichang;
    @BindView(R.id.html_content)
    TextView htmlContent;

    private ExplainDetailsBO detailsBO;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_explain_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("训练讲解");
        inviVideo();
        detailsBO = (ExplainDetailsBO) getIntent().getExtras().getSerializable("details");

        videoTitle.setText(detailsBO.getVideoTitle());
        xunlianShichang.setText("训练时长       " + detailsBO.getTrainLength() + "分钟");
        htmlContent.setText(Html.fromHtml(detailsBO.getTrainContent(), new ImageGetterUtils.MyImageGetter(this, htmlContent), null));

        videoPlayer.setUp(detailsBO.getVideoUrl(), true, "");
        videoPlayer.startPlayLogic();
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
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);
        videoPlayer.setNeedLockFull(true);
        videoPlayer.setHideKey(false);
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

}
