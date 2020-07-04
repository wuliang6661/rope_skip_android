package com.tohabit.skip.ui.mine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sak.ultilviewlib.UltimateRefreshView;
import com.sak.ultilviewlib.adapter.InitFooterAdapter;
import com.sak.ultilviewlib.interfaces.OnFooterRefreshListener;
import com.sak.ultilviewlib.interfaces.OnHeaderRefreshListener;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.ShouCangBO;
import com.tohabit.skip.ui.find.fragment.KeChengDetailsActivity;
import com.tohabit.skip.ui.find.fragment.ZhiShiDetailsActivity;
import com.tohabit.skip.widget.TraditionHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1415:06
 * desc   :  我的收藏
 * version: 1.0
 */
public class MyShouCangActivity extends BaseActivity {


    @BindView(R.id.zhishi_text)
    TextView zhishiText;
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
    @BindView(R.id.refresh_view)
    UltimateRefreshView refreshView;

    private int pageNum = 1;
    private int selectType = 1;
    private List<ShouCangBO> shouCangBOS;

    BaseRvAdapter<ShouCangBO, BaseViewHolder> zhishiAdapter;
    BaseRvAdapter<ShouCangBO, BaseViewHolder> kechengAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_my_shoucang;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("我的收藏");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        refreshView.setBaseHeaderAdapter(new TraditionHeaderAdapter(this));
        refreshView.setBaseFooterAdapter(new InitFooterAdapter(this));
        refreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(UltimateRefreshView view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        getData();
                    }
                }, 1000);
            }
        });
        refreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(UltimateRefreshView view) {
                pageNum++;
                getData();
            }
        });
        setZhishiAdapter();
        getData();
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


    @OnClick({R.id.my_zhishi, R.id.my_kecheng})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.my_zhishi:
                setTitleStyle(0);
                selectType = 1;
                setZhishiAdapter();
                getData();
                break;
            case R.id.my_kecheng:
                setTitleStyle(1);
                selectType = 0;
                setKechengAdapter();
                getData();
                break;
        }
    }

    private void setTitleStyle(int i) {
        if (i == 0) {
            kechengText.setTextColor(Color.parseColor("#AAAAAA"));
            kechengView.setVisibility(View.GONE);
            zhishiText.setTextColor(Color.parseColor("#7EC7F5"));
            zhishiView.setVisibility(View.VISIBLE);
        } else {
            zhishiText.setTextColor(Color.parseColor("#AAAAAA"));
            zhishiView.setVisibility(View.GONE);
            kechengText.setTextColor(Color.parseColor("#7EC7F5"));
            kechengView.setVisibility(View.VISIBLE);
        }
    }


    private void getData() {
        HttpServerImpl.getCollectList(selectType, pageNum).subscribe(new HttpResultSubscriber<List<ShouCangBO>>() {
            @Override
            public void onSuccess(List<ShouCangBO> s) {
                refreshView.onFooterRefreshComplete();
                refreshView.onHeaderRefreshComplete();

                if (pageNum == 1) {
                    shouCangBOS = s;
                    if (selectType == 1) {
                        zhishiAdapter.setNewData(shouCangBOS);
                    } else {
                        kechengAdapter.setNewData(shouCangBOS);
                    }
                } else {
                    if (s.isEmpty()) {
                        pageNum--;
                    }
                    shouCangBOS.addAll(s);
                    if (selectType == 1) {
                        zhishiAdapter.addData(s);
                    } else {
                        kechengAdapter.addData(s);
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                refreshView.onFooterRefreshComplete();
                refreshView.onHeaderRefreshComplete();
                showToast(message);
            }
        });
    }


    private void setZhishiAdapter() {
        zhishiAdapter = new BaseRvAdapter<ShouCangBO, BaseViewHolder>(R.layout.item_zhishi, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, ShouCangBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.zhishi_img);
                Glide.with(MyShouCangActivity.this).load(item.getImage()).into(imageView);
                helper.setText(R.id.zhishi_title, item.getTitle());
                helper.setText(R.id.tizhong, item.getWeight() + "kg");
                helper.setText(R.id.shengao, item.getHeight() + "cm");
                helper.getView(R.id.kecheng_img).setVisibility(View.GONE);
                helper.getView(R.id.shijian).setVisibility(View.GONE);
                helper.getView(R.id.shiyongnianling).setVisibility(View.VISIBLE);
                helper.setText(R.id.shiyongnianling, item.getAge() + "岁");
                helper.setText(R.id.beizhu_text, "作者  " + item.getReleaseName() + "  " + item.getReleaseDate());
            }
        };
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE).build());
        zhishiAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) recycleView.getParent(), false));
        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter a, View view, int position) {
                LogUtils.e("ddddddddd");
                Bundle bundle = new Bundle();
                bundle.putInt("Id", zhishiAdapter.getData().get(position).getId());
                gotoActivity(ZhiShiDetailsActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(zhishiAdapter);
    }


    private void setKechengAdapter() {
        kechengAdapter = new BaseRvAdapter<ShouCangBO, BaseViewHolder>(R.layout.item_kecheng, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, ShouCangBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.kecheng_img);
                Glide.with(MyShouCangActivity.this).load(item.getImage()).into(imageView);
                helper.setText(R.id.zhishi_title, item.getTitle());
                helper.setText(R.id.shijian, item.getCourseNum() + "节课");
            }
        };
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE).build());
        kechengAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) recycleView.getParent(), false));
        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter a, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("kechengId", kechengAdapter.getData().get(position).getId());
                gotoActivity(KeChengDetailsActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(kechengAdapter);
    }

}
