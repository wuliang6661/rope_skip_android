package com.tohabit.skip.ui.find.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.KechengBO;
import com.tohabit.skip.pojo.po.VideoBO;
import com.tohabit.skip.widget.WebUtils;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1515:59
 * desc   : 课程详情界面
 * version: 1.0
 */
public class KeChengDetailsActivity extends BaseActivity {


    @BindView(R.id.zhishi_title)
    TextView zhishiTitle;
    @BindView(R.id.shiyongnianling)
    TextView shiyongnianling;
    @BindView(R.id.tizhong)
    TextView tizhong;
    @BindView(R.id.shengao)
    TextView shengao;
    @BindView(R.id.shoucang_img)
    ImageView shoucangImg;
    @BindView(R.id.shijian)
    TextView shijian;
    @BindView(R.id.kecheng_list)
    TextView kechengList;
    @BindView(R.id.zhishi_view)
    View zhishiView;
    @BindView(R.id.my_zhishi)
    RelativeLayout myZhishi;
    @BindView(R.id.kecheng_text)
    TextView kechengText;
    @BindView(R.id.kecheng_view)
    View kechengView;
    @BindView(R.id.my_kecheng)
    RelativeLayout myKecheng;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    //    @BindView(R.id.html_text)
//    TextView htmlText;
//    @BindView(R.id.jianjie_layout)
//    ScrollView jianjieLayout;
    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    @BindView(R.id.web_view)
    WebView webView;

    private KechengBO kechengBO;
    private int id;
    private List<VideoBO> videos;
    private boolean isPlay;
    private OrientationUtils orientationUtils;
    private VideoBO currnVideo;   //当前播放的video

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_kecheng_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("课程详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        id = getIntent().getExtras().getInt("kechengId");
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        videoPlayer.getBackButton().setVisibility(View.GONE);
        inviVideo();
        WebUtils.initWeb(webView);
        getClassDetails(id);
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
                if (currnVideo == null) {
                    return;
                }
                Intent intent = new Intent(KeChengDetailsActivity.this, FullVideoActivity.class);
                intent.putExtra("url", currnVideo.getUrl());
                intent.putExtra("startTime", videoPlayer.getCurrentPositionWhenPlaying());
                startActivityForResult(intent, 1);
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
        showToast(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    @OnClick({R.id.my_zhishi, R.id.my_kecheng})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.my_zhishi:
                setTitleStyle(0);
                break;
            case R.id.my_kecheng:
                setTitleStyle(1);
                break;
        }
    }

    private void setTitleStyle(int i) {
        if (i == 0) {
            kechengText.setTextColor(Color.parseColor("#AAAAAA"));
            kechengView.setVisibility(View.GONE);
            kechengList.setTextColor(Color.parseColor("#7EC7F5"));
            zhishiView.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.VISIBLE);
//            jianjieLayout.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
        } else {
            kechengList.setTextColor(Color.parseColor("#AAAAAA"));
            zhishiView.setVisibility(View.GONE);
            kechengText.setTextColor(Color.parseColor("#7EC7F5"));
            kechengView.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
//            jianjieLayout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 获取课程详情
     */
    private void getClassDetails(int id) {
        HttpServerImpl.getCourseInfo(id).subscribe(new HttpResultSubscriber<KechengBO>() {
            @Override
            public void onSuccess(KechengBO kecheng) {
                kechengBO = kecheng;
                showData();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void showData() {
//        Glide.with(this).load(item.getImage()).into(imageView);
        zhishiTitle.setText(kechengBO.getTitle());
        tizhong.setText(kechengBO.getWeight() + "kg");
        shengao.setText(kechengBO.getHeight() + "cm");
        shijian.setText(kechengBO.getCourseNum() + "节课");
        shiyongnianling.setText(kechengBO.getAge() + "岁");
        if ("1".equals(kechengBO.getIsCollect())) {  //已收藏
            shoucangImg.setImageResource(R.mipmap.yishoucang);
        } else {
            shoucangImg.setImageResource(R.mipmap.shoucang);
        }
//        htmlText.setText(Html.fromHtml(kechengBO.getIntroduction(), new ImageGetterUtils.MyImageGetter(this, htmlText), null));
        webView.loadDataWithBaseURL(null, kechengBO.getIntroduction(), "text/html", "utf-8", null);
        getVideoList();
    }


    /**
     * 获取课程视频列表
     */
    private void getVideoList() {
        HttpServerImpl.getCourseVideoList(id).subscribe(new HttpResultSubscriber<List<VideoBO>>() {
            @Override
            public void onSuccess(List<VideoBO> s) {
                videos = s;
                if (!s.isEmpty() && currnVideo == null) {
                    video(s.get(0));
                    addDataLearn(s.get(0).getId() + "");
                }
                showVideoAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置视频开始播放
     */
    private void video(VideoBO videoBO) {
        this.currnVideo = videoBO;
        //增加封面
        ImageView imageView = new ImageView(KeChengDetailsActivity.this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(KeChengDetailsActivity.this).load(videoBO.getImage()).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        videoPlayer.setUp(videoBO.getUrl(), true, "");
        videoPlayer.startPlayLogic();
    }


    /**
     * 显示视频列表
     */
    private void showVideoAdapter() {
        LGRecycleViewAdapter<VideoBO> adapter = new LGRecycleViewAdapter<VideoBO>(videos) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_kecheng_video;
            }

            @Override
            public void convert(LGViewHolder holder, VideoBO videoBO, int position) {
                holder.setImageUrl(KeChengDetailsActivity.this, R.id.video_img, videoBO.getImage());
                holder.setText(R.id.video_title, videoBO.getTitle());
                holder.setText(R.id.video_time, videoBO.getVideoLength());
                TextView videoStatus = (TextView) holder.getView(R.id.video_status);
                if (videoBO.getIsLearn() == 0) {  //未学习
                    holder.setText(R.id.video_status, "未学习");
                    videoStatus.setTextColor(Color.parseColor("#888888"));
                } else {
                    holder.setText(R.id.video_status, "已学习");
                    videoStatus.setTextColor(Color.parseColor("#7EC7F5"));
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                video(adapter.getItem(position));
                addDataLearn(adapter.getItem(position).getId() + "");
            }
        });
        recycleView.setAdapter(adapter);
    }


    /**
     * 添加学习记录
     */
    private void addDataLearn(String objectId) {
        HttpServerImpl.addDataLearn(objectId).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getVideoList();
            }

            @Override
            public void onFiled(String message) {

            }
        });
    }


    /**
     * 收藏课程
     */
    @OnClick(R.id.shoucang_img)
    public void shoucang() {
        showProgress(null);
        if ("1".equals(kechengBO.getIsCollect())) {  //已收藏
            HttpServerImpl.cancelCollect(kechengBO.getId(), 0).subscribe(new HttpResultSubscriber<String>() {
                @Override
                public void onSuccess(String s) {
                    stopProgress();
                    shoucangImg.setImageResource(R.mipmap.shoucang);
                    kechengBO.setIsCollect("0");
                }

                @Override
                public void onFiled(String message) {
                    stopProgress();
                    showToast(message);
                }
            });
        } else {
            HttpServerImpl.addCollect(kechengBO.getId(), 0).subscribe(new HttpResultSubscriber<String>() {
                @Override
                public void onSuccess(String s) {
                    stopProgress();
                    shoucangImg.setImageResource(R.mipmap.yishoucang);
                    kechengBO.setIsCollect("1");
                }

                @Override
                public void onFiled(String message) {
                    stopProgress();
                    showToast(message);
                }
            });
        }
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
        if (orientationUtils != null)
            orientationUtils.releaseListener();
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
                videoPlayer.setUp(currnVideo.getUrl(), true, "");
                videoPlayer.startPlayLogic();
                videoPlayer.setSeekOnStart(startTime);
                break;
        }
    }

}
