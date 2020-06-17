package com.tohabit.skip.ui.find.fragment;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.ZhiShiBO;
import com.tohabit.skip.widget.WebUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 知识详情界面
 */
public class ZhiShiDetailsActivity extends BaseActivity {

    @BindView(R.id.zhishi_title)
    TextView zhishiTitle;
    @BindView(R.id.zhishi_person)
    TextView zhishiPerson;
    @BindView(R.id.shoucang_img)
    ImageView shoucangImg;
    @BindView(R.id.web_view)
    WebView webView;
//    @BindView(R.id.html_text)
//    TextView htmlText;

    private int id;
    private ZhiShiBO zhiShiBO;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_zhishi_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("跳绳知识");

        id = getIntent().getExtras().getInt("Id");
        WebUtils.initWeb(webView);
        getClassDetails(id);
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
     * 获取知识详情
     */
    private void getClassDetails(int id) {
        HttpServerImpl.getKnowledge(id).subscribe(new HttpResultSubscriber<ZhiShiBO>() {
            @Override
            public void onSuccess(ZhiShiBO kecheng) {
                zhiShiBO = kecheng;
                showData();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void showData() {
        zhishiTitle.setText(zhiShiBO.getTitle());
        zhishiPerson.setText("发布人   " + zhiShiBO.getReleaseName() + "      发布时间   " + zhiShiBO.getReleaseDate());
        if ("1".equals(zhiShiBO.getIsCollect())) {  //已收藏
            shoucangImg.setImageResource(R.mipmap.yishoucang);
        } else {
            shoucangImg.setImageResource(R.mipmap.shoucang);
        }
//        htmlText.setText(Html.fromHtml(zhiShiBO.getContent(), new ImageGetterUtils.MyImageGetter(this, htmlText), null));
        webView.loadDataWithBaseURL(null, zhiShiBO.getContent(), "text/html", "utf-8", null);

    }


    /**
     * 收藏课程
     */
    @OnClick(R.id.shoucang_img)
    public void shoucang() {
        if ("1".equals(zhiShiBO.getIsCollect())) {  //已收藏
            HttpServerImpl.cancelCollect(zhiShiBO.getId(), 1).subscribe(new HttpResultSubscriber<String>() {
                @Override
                public void onSuccess(String s) {
                    getClassDetails(id);
                }

                @Override
                public void onFiled(String message) {
                    showToast(message);
                }
            });
        } else {
            HttpServerImpl.addCollect(zhiShiBO.getId(), 1).subscribe(new HttpResultSubscriber<String>() {
                @Override
                public void onSuccess(String s) {
                    getClassDetails(id);
                }

                @Override
                public void onFiled(String message) {
                    showToast(message);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
