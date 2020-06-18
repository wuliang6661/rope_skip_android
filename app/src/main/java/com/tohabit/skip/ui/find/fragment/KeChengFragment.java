package com.tohabit.skip.ui.find.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sak.ultilviewlib.UltimateRefreshView;
import com.sak.ultilviewlib.interfaces.OnHeaderRefreshListener;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.FenLeiBO;
import com.tohabit.skip.pojo.po.KechengBO;
import com.tohabit.skip.widget.TraditionHeaderAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1515:08
 * desc   : 教程Fragment
 * version: 1.0
 */
public class KeChengFragment extends BaseFragment {

    @BindView(R.id.fenlei_recycle)
    RecyclerView fenleiRecycle;
    @BindView(R.id.shaixuan_layout)
    LinearLayout shaixuanLayout;
    @BindView(R.id.fenlei_layout)
    LinearLayout fenleiLayout;
    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    UltimateRefreshView mSwipeRefreshLayout;
    Unbinder unbinder;

    BaseRvAdapter<KechengBO, BaseViewHolder> adapter;
    FenLeiAdapter fenLeiAdapter;

    private int isSelectNianLing = 0;
    private int isSelectShengao = 0;
    private int isSelectTizhong = 0;

    private int selectFeiLei = 0;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_list;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
//                shaixuanLayout.setVisibility(View.VISIBLE);
//                mSwipeRefreshLayout.setOnRefreshListener(KeChengFragment.this);
                mSwipeRefreshLayout.setBaseHeaderAdapter(new TraditionHeaderAdapter(getActivity()));
                mSwipeRefreshLayout.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
                    @Override
                    public void onHeaderRefresh(UltimateRefreshView view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onRefresh();
                            }
                        },1000);
                    }
                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                fenleiRecycle.setLayoutManager(manager);
                initAdapter();
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }

    private void initAdapter() {
        adapter = new BaseRvAdapter<KechengBO, BaseViewHolder>(R.layout.item_kecheng, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, KechengBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.kecheng_img);
                Glide.with(getActivity()).load(item.getImage()).into(imageView);
                helper.setText(R.id.zhishi_title, item.getTitle());
//                helper.setText(R.id.tizhong, item.getWeight() + "kg");
//                helper.setText(R.id.shengao, item.getHeight() + "cm");
                helper.setText(R.id.shijian, item.getCourseNum() + "节课");
//                helper.setText(R.id.shiyongnianling, item.getAge() + "岁");
            }
        };
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE).build());
        adapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) mRecyclerView.getParent(), false));
        AppCompatButton mBtnRefresh = adapter.getEmptyView().findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(v -> onRefresh());
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter a, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("kechengId", adapter.getData().get(position).getId());
                gotoActivity(KeChengDetailsActivity.class, bundle, false);
            }
        });
        mRecyclerView.setAdapter(adapter);
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

    public void onRefresh() {
        getKechengClass();
    }

    /**
     * 获取活动分类列表
     */
    private void getKechengClass() {
        HttpServerImpl.getCourseClasss().subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                setFenLeiAdapter(s);
                mSwipeRefreshLayout.onHeaderRefreshComplete();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                mSwipeRefreshLayout.onHeaderRefreshComplete();
            }
        });
    }


    /**
     * 显示分类的列表
     */
    private void setFenLeiAdapter(List<FenLeiBO> s) {
        fenLeiAdapter = new FenLeiAdapter(s);
        fenLeiAdapter.setOnItemClickListener(R.id.fenlei_text, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                selectFeiLei = position;
                getHuoDongList(s.get(position).getId());
                fenLeiAdapter.setSelectFenLei(position);
            }
        });
        fenleiRecycle.setAdapter(fenLeiAdapter);
        fenLeiAdapter.setSelectFenLei(selectFeiLei);
        if (!s.isEmpty()) {
            getHuoDongList(s.get(selectFeiLei).getId());
        }
    }


    /**
     * 根据活动分类查询课程列表
     */
    private void getHuoDongList(int classId) {
//        showProgress(null);
        HttpServerImpl.getCourseInfoList(classId + "", isSelectNianLing, isSelectShengao, isSelectTizhong, null)
                .subscribe(new HttpResultSubscriber<List<KechengBO>>() {
                    @Override
                    public void onSuccess(List<KechengBO> s) {
                        stopProgress();
                        adapter.setNewData(s);
                    }

                    @Override
                    public void onFiled(String message) {
                        stopProgress();
                        showToast(message);
                    }
                });
    }


    @OnClick(R.id.shaixuan_layout)
    public void clickShaiXuan() {
        PopShiXuanWindow popShiXuanWindow = new PopShiXuanWindow(getActivity(), isSelectNianLing, isSelectShengao, isSelectTizhong);
        popShiXuanWindow.setListener((Nianling, Shengao, Tizhong) -> {
            isSelectNianLing = Nianling;
            isSelectShengao = Shengao;
            isSelectTizhong = Tizhong;
            getHuoDongList(fenLeiAdapter.getItem(selectFeiLei).getId());
        });
        popShiXuanWindow.showAsDropDown(fenleiLayout);
    }

}
