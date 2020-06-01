package com.tohabit.skip.ui.find.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.ShopDetailsBO;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情页面
 */
public class ShopDetailsActivity extends BaseActivity {

    @BindView(R.id.all_nengliang)
    TextView allNengliang;
    @BindView(R.id.duihuan_button)
    TextView duihuanButton;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_miaoshu)
    TextView shopMiaoshu;
    @BindView(R.id.shop_price)
    TextView shopPrice;
    @BindView(R.id.shop_nengliang)
    TextView shopNengliang;
    @BindView(R.id.web_view)
    WebView webView;

    private int id;
    private ShopDetailsBO detailsBO;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_shop_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("商品详情");

        initWeb();
        id = getIntent().getExtras().getInt("Id");
        getShopDetails();
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
     * 查询商品详情
     */
    private void getShopDetails() {
        HttpServerImpl.getGood(id).subscribe(new HttpResultSubscriber<ShopDetailsBO>() {
            @Override
            public void onSuccess(ShopDetailsBO s) {
                detailsBO = s;
                showData();
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
    private void showData() {
        setBanner();
        allNengliang.setText(detailsBO.getVailEnergy() + "");
        shopName.setText(detailsBO.getName());
        shopMiaoshu.setText("近30天已兑 " + detailsBO.getExchangeNum() + "个");
        shopPrice.setText(detailsBO.getPrice() + "");
        shopNengliang.setText(detailsBO.getExchangeEnergy() + "");
        webView.loadDataWithBaseURL(null, detailsBO.getContent().replaceAll("\\\\", ""),
                "text/html", "utf-8", null);
    }

    @OnClick(R.id.duihuan_button)
    public void clickDuiHuan() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", detailsBO);
        gotoActivity(ExChangeShopActivity.class, bundle, false);
    }


    private void setBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(detailsBO.getImageList());
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setOnBannerListener(position -> {
//            if (StringUtils.isEmpty(bannerBOS.get(position).getForwardUrl())) {
//                return;
//            }
//            Bundle bundle = new Bundle();
//            bundle.putString("url", bannerBOS.get(position).getForwardUrl());
//            gotoActivity(WebActivity.class, bundle, false);
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }

//        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }
}
