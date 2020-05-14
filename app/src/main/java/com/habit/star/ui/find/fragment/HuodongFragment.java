package com.habit.star.ui.find.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseFragment;
import com.habit.star.common.adapter.BaseRvAdapter;
import com.habit.star.pojo.po.FenLeiBO;
import com.habit.star.pojo.po.HuodongBO;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 活动
 */
public class HuodongFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progress_fragment_fragment_find_list)
    ProgressbarLayout progress;
    @BindView(R.id.fenlei_recycle)
    RecyclerView fenleiRecycle;

    BaseRvAdapter<HuodongBO, BaseViewHolder> adapter;
    Unbinder unbinder;

    private FenLeiAdapter fenLeiAdapter;

    public static HuodongFragment newInstance(Bundle bundle) {
        HuodongFragment fragment = new HuodongFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }


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
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fenleiRecycle.setLayoutManager(manager);
        initAdapter();
        onRefresh();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initAdapter() {
        adapter = new BaseRvAdapter<HuodongBO, BaseViewHolder>(R.layout.item_huodong, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, HuodongBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.huodong_img);
                Glide.with(getActivity()).load(item.getImage()).into(imageView);
                helper.setText(R.id.huodong_title, item.getTitle());
                helper.setText(R.id.huodong_time, "报名时间： " + item.getTimeBucket());
            }
        };
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
               Bundle bundle = new Bundle();
               bundle.putSerializable("huodong",adapter.getData().get(position));
               bundle.putInt("huodongStatus",fenLeiAdapter.getSelectPosition());
               gotoActivity(HuodongDetailsActivity.class,bundle,false);
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

    @Override
    public void onRefresh() {
        getHuoDongClass();
    }


    /**
     * 获取活动分类列表
     */
    private void getHuoDongClass() {
        HttpServerImpl.getActivityClasss().subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
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
                getHuoDongList(s.get(position).getType());
                fenLeiAdapter.setSelectFenLei(position);
            }
        });
        fenleiRecycle.setAdapter(fenLeiAdapter);
        if (!s.isEmpty()) {
            getHuoDongList(s.get(0).getType());
        }
    }


    /**
     * 根据活动分类查询活动列表
     */
    private void getHuoDongList(int type) {
        showProgress(null);
        HttpServerImpl.getActivityList(type).subscribe(new HttpResultSubscriber<List<HuodongBO>>() {
            @Override
            public void onSuccess(List<HuodongBO> s) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
