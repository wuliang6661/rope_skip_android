package com.tohabit.skip.ui.find.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
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
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.FenLeiBO;
import com.tohabit.skip.pojo.po.QuestionsBO;
import com.tohabit.skip.widget.TraditionHeaderAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 百问百答
 */
public class QuestionsFragment extends BaseFragment {

    @BindView(R.id.fenlei_recycle)
    RecyclerView fenleiRecycle;
    @BindView(R.id.bianji_layout)
    LinearLayout bianjiLayout;
    @BindView(R.id.fenlei_layout)
    LinearLayout fenleiLayout;
    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    UltimateRefreshView mSwipeRefreshLayout;

    BaseRvAdapter<QuestionsBO, BaseViewHolder> adapter;
    FenLeiAdapter fenLeiAdapter;
    private int selectFeiLei = 0;
    List<FenLeiBO> fenleis;
    List<QuestionsBO> questionsBOS = new ArrayList<>();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fra_questions;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setBaseHeaderAdapter(new TraditionHeaderAdapter(getActivity()));
        mSwipeRefreshLayout.setBaseFooterAdapter(new InitFooterAdapter(getActivity()));
        mSwipeRefreshLayout.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(UltimateRefreshView view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onRefresh();
                    }
                }, 1000);
            }
        });
        mSwipeRefreshLayout.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(UltimateRefreshView view) {
                if (fenleis != null && !fenleis.isEmpty()) {
                    pageNum++;
                    getHuoDongList(fenleis.get(selectFeiLei).getId());
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fenleiRecycle.setLayoutManager(manager);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new BaseRvAdapter<QuestionsBO, BaseViewHolder>(R.layout.item_baiwenbaida, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, QuestionsBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.wenti_img);
                Glide.with(getActivity()).load(item.getImage()).into(imageView);
                RoundedImageView userImg = helper.getView(R.id.user_img);
                Glide.with(getActivity()).load(item.getHeadImage()).into(userImg);
                helper.setText(R.id.user_name, item.getUserName());
                helper.setText(R.id.user_time, item.getCreateDate());
                helper.setText(R.id.wenti_name, item.getTitle());
//                helper.setText(R.id.wenti_message,item.getContent());
                helper.setText(R.id.huifu_num, item.getCommentNum() + "回复");
            }
        };
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE)
                .margin(SizeUtils.dp2px(15)).build());
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
                gotoActivity(QuestionDetailsActivity.class, bundle, false);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        onRefresh();
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
        HttpServerImpl.getQuestionAnswerClasss().subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                fenleis = s;
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


    private int pageNum = 1;

    /**
     * 根据活动分类查询课程列表
     */
    private void getHuoDongList(int classId) {
//        showProgress(null);
        HttpServerImpl.getQuestionAnswerInfoList(classId, pageNum)
                .subscribe(new HttpResultSubscriber<List<QuestionsBO>>() {
                    @Override
                    public void onSuccess(List<QuestionsBO> s) {
                        stopProgress();
                        if (pageNum == 1) {
                            questionsBOS = s;
                            adapter.setNewData(questionsBOS);
                        } else {
                            if (s.isEmpty()) {
                                pageNum--;
                            }
                            questionsBOS.addAll(s);
                            adapter.addData(s);
                        }
                        mSwipeRefreshLayout.onFooterRefreshComplete();
                    }

                    @Override
                    public void onFiled(String message) {
                        stopProgress();
                        showToast(message);
                        mSwipeRefreshLayout.onFooterRefreshComplete();
                    }
                });
    }

    public void onRefresh() {
        pageNum = 1;
        getKechengClass();
    }


    @OnClick({R.id.sousuo_layout, R.id.bianji_layout})
    public void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.sousuo_layout:
                gotoActivity(SearchQuestionsActivity.class, false);
                break;
            case R.id.bianji_layout:
                gotoActivity(QuestionAddActivity.class, false);
                break;
        }
    }
}
