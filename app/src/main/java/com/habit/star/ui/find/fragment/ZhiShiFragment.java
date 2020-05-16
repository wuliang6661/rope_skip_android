package com.habit.star.ui.find.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseFragment;
import com.habit.star.common.adapter.BaseRvAdapter;
import com.habit.star.pojo.po.FenLeiBO;
import com.habit.star.pojo.po.ZhiShiBO;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 知识Fragment
 */
public class ZhiShiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.fenlei_recycle)
    RecyclerView fenleiRecycle;
    @BindView(R.id.shaixuan_layout)
    LinearLayout shaixuanLayout;
    @BindView(R.id.fenlei_layout)
    LinearLayout fenleiLayout;
    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;


    BaseRvAdapter<ZhiShiBO, BaseViewHolder> adapter;

    FenLeiAdapter fenLeiAdapter;

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
        shaixuanLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fenleiRecycle.setLayoutManager(manager);
        initAdapter();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        onRefresh();
    }


    private void initAdapter() {
        adapter = new BaseRvAdapter<ZhiShiBO, BaseViewHolder>(R.layout.item_zhishi, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, ZhiShiBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.zhishi_img);
                Glide.with(getActivity()).load(item.getImage()).into(imageView);
                helper.setText(R.id.zhishi_title, item.getTitle());
                helper.setText(R.id.tizhong, item.getWeight() + "kg");
                helper.setText(R.id.shengao, item.getHeight() + "cm");
                helper.setText(R.id.shijian, item.getAge());
                helper.setText(R.id.beizhu_text, "作者  " + item.getReleaseName() + "  " + item.getReleaseDate());
            }
        };
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE).build());
        adapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) mRecyclerView.getParent(), false));
        AppCompatButton mBtnRefresh = (AppCompatButton) adapter.getEmptyView().findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter a, View view, int position) {
                LogUtils.e("ddddddddd");
                Bundle bundle = new Bundle();
                bundle.putInt("Id", adapter.getData().get(position).getId());
                gotoActivity(ZhiShiDetailsActivity.class, bundle, false);
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

    }

    @Override
    public void showError(int errorCode) {

    }


    /**
     * 获取活动分类列表
     */
    private void getKechengClass() {
        HttpServerImpl.getKnowledgeClasss().subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                setFenLeiAdapter(s);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                mSwipeRefreshLayout.setRefreshing(false);
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
                getHuoDongList(s.get(position).getId());
                fenLeiAdapter.setSelectFenLei(position);
            }
        });
        fenleiRecycle.setAdapter(fenLeiAdapter);
        if (!s.isEmpty()) {
            getHuoDongList(s.get(0).getId());
        }
    }


    /**
     * 根据活动分类查询课程列表
     */
    private void getHuoDongList(int classId) {
        showProgress(null);
        HttpServerImpl.getSelectKnowledgeInfoList(classId + "", 0, 0, 0, null)
                .subscribe(new HttpResultSubscriber<List<ZhiShiBO>>() {
                    @Override
                    public void onSuccess(List<ZhiShiBO> s) {
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

    @Override
    public void onRefresh() {
        getKechengClass();
    }
}
