package com.habit.star.ui.find.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.OnePingLunBO;
import com.habit.star.pojo.po.QuestionsBO;
import com.habit.star.ui.BigPicutreActivity;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问答详情页面
 */
public class QuestionDetailsActivity extends BaseActivity {

    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.edit_pinglun)
    TextView editPinglun;
    @BindView(R.id.wenda_title)
    TextView wendaTitle;
    @BindView(R.id.wenda_person_message)
    TextView wendaPersonMessage;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.pinglun_num)
    TextView pinglunNum;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private int id;

    private QuestionsBO questionsBO;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_question_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("百问百答");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE)
                .margin(SizeUtils.dp2px(15)).build());

        recycleView.setNestedScrollingEnabled(false);
        recycleView.setLayoutManager(manager);

        initWeb();
        id = getIntent().getExtras().getInt("Id");
        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        getQuestionAnswerInfo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getOneCommentList();
    }

    private void initWeb() {
        WebSettings webSettings = webView.getSettings();
        /*与js交互*/
        webSettings.setJavaScriptEnabled(true);
        /*自适应屏幕*/
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);
//        /*细节操作*/
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持js弹窗
//        webSettings.setBlockNetworkImage(false);
//        webSettings.setDomStorageEnabled(true);
//        //设置支持自动加载图片
//        if (Build.VERSION.SDK_INT >= 19) {
//            webSettings.setLoadsImagesAutomatically(true);
//        } else {
//            webSettings.setLoadsImagesAutomatically(false);
//        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
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

    /**
     * 查询问答详情
     */
    private void getQuestionAnswerInfo() {
        HttpServerImpl.getQuestionAnswerInfo(id).subscribe(new HttpResultSubscriber<QuestionsBO>() {
            @Override
            public void onSuccess(QuestionsBO s) {
                questionsBO = s;
                showData();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void showData() {
        wendaTitle.setText(questionsBO.getTitle());
        wendaPersonMessage.setText("发布人  " + questionsBO.getUserName() + "     发布时间   " + questionsBO.getFriendDate());
        webView.loadDataWithBaseURL(null, questionsBO.getContent().replaceAll("\\\\", ""),
                "text/html", "utf-8", null);
    }


    @OnClick(R.id.edit_pinglun)
    public void addCommon() {
        Intent intent = new Intent(this, AddPingLunActivity.class);
        intent.putExtra("isFirst", true);
        intent.putExtra("objectId", id);
        startActivity(intent);
    }


    /**
     * 查询所有一级评论
     */
    private void getOneCommentList() {
        showProgress(null);
        HttpServerImpl.getOneCommentList(id + "").subscribe(new HttpResultSubscriber<List<OnePingLunBO>>() {
            @Override
            public void onSuccess(List<OnePingLunBO> s) {
                stopProgress();
                pinglunNum.setText(s.size() + "条评论");
                setPingLunAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 设置评论适配器
     */
    private void setPingLunAdapter(List<OnePingLunBO> list) {
        LGRecycleViewAdapter<OnePingLunBO> adapter = new LGRecycleViewAdapter<OnePingLunBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_one_pinglun;
            }

            @Override
            public void convert(LGViewHolder holder, OnePingLunBO onePingLunBO, int position) {
                holder.setImageUrl(QuestionDetailsActivity.this, R.id.user_img, onePingLunBO.getHeadImage());
                holder.setText(R.id.user_name, onePingLunBO.getUserName());
                holder.setText(R.id.pinglun_date, onePingLunBO.getFriendDate());
                holder.setText(R.id.pinglun_message, onePingLunBO.getContent());
                if (onePingLunBO.getImageList() == null || onePingLunBO.getImageList().isEmpty()) {
                    holder.getView(R.id.image_layout).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.image_layout).setVisibility(View.VISIBLE);
                    holder.setImageUrl(QuestionDetailsActivity.this, R.id.pinglun_img1, onePingLunBO.getImageList().get(0));
                    if (onePingLunBO.getImageList().size() >= 2) {
                        holder.getView(R.id.pinglun_img2).setVisibility(View.VISIBLE);
                        holder.setImageUrl(QuestionDetailsActivity.this, R.id.pinglun_img2, onePingLunBO.getImageList().get(1));
                    } else {
                        holder.getView(R.id.pinglun_img2).setVisibility(View.GONE);
                    }
                    if (onePingLunBO.getImageList().size() >= 3) {
                        holder.getView(R.id.pinglun_img3).setVisibility(View.VISIBLE);
                        holder.setImageUrl(QuestionDetailsActivity.this, R.id.pinglun_img3, onePingLunBO.getImageList().get(2));
                    } else {
                        holder.getView(R.id.pinglun_img3).setVisibility(View.GONE);
                    }
                }
                if (onePingLunBO.getTwoComment() == null) {
                    holder.getView(R.id.pinglun_layout).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.pinglun_layout).setVisibility(View.VISIBLE);
                    TextView txContent = (TextView) holder.getView(R.id.two_content);
                    String message = onePingLunBO.getTwoComment().getUserName() + "：" + onePingLunBO.getTwoComment().getContent();
                    SpannableString spannableString = new SpannableString(message);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7EC7F5")), 0, onePingLunBO.getTwoComment().getUserName().length(),
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    txContent.setText(spannableString);
                    holder.setText(R.id.two_num, "查看全部" + onePingLunBO.getCommentNum() + "条评论");
                }
            }
        };
        adapter.setOnItemClickListener(R.id.pinglun_layout, (view, position) -> {
            Intent intent = new Intent(QuestionDetailsActivity.this, TwoPingLunActivity.class);
            intent.putExtra("commentNum", adapter.getItem(position).getCommentNum());
            intent.putExtra("classId", id);
            intent.putExtra("parentId", adapter.getItem(position).getId());
            startActivity(intent);
        });
        adapter.setOnItemClickListener(R.id.pinglun, (view, position) -> {
            Intent intent = new Intent(QuestionDetailsActivity.this, AddPingLunActivity.class);
            intent.putExtra("isFirst", false);
            intent.putExtra("objectId", id);
            intent.putExtra("parentId", adapter.getItem(position).getId());
            startActivity(intent);
        });
        adapter.setOnItemClickListener(R.id.pinglun_img1, (view, position) -> {
            Intent intent = new Intent(QuestionDetailsActivity.this,BigPicutreActivity.class);
            intent.putStringArrayListExtra("imageBos", (ArrayList<String>) adapter.getItem(position).getImageList());
            intent.putExtra("selectPosition",0);
            startActivity(intent);
        });
        adapter.setOnItemClickListener(R.id.pinglun_img2, (view, position) -> {
            Intent intent = new Intent(QuestionDetailsActivity.this,BigPicutreActivity.class);
            intent.putStringArrayListExtra("imageBos", (ArrayList<String>) adapter.getItem(position).getImageList());
            intent.putExtra("selectPosition",1);
            startActivity(intent);
        });
        adapter.setOnItemClickListener(R.id.pinglun_img3, (view, position) -> {
            Intent intent = new Intent(QuestionDetailsActivity.this,BigPicutreActivity.class);
            intent.putStringArrayListExtra("imageBos", (ArrayList<String>) adapter.getItem(position).getImageList());
            intent.putExtra("selectPosition",2);
            startActivity(intent);
        });
        recycleView.setAdapter(adapter);
    }

}
