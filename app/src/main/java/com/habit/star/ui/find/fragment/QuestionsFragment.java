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
import com.blankj.utilcode.util.SizeUtils;
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
import com.habit.star.pojo.po.QuestionsBO;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 百问百答
 */
public class QuestionsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.fenlei_recycle)
    RecyclerView fenleiRecycle;
    @BindView(R.id.bianji_layout)
    LinearLayout bianjiLayout;
    @BindView(R.id.fenlei_layout)
    LinearLayout fenleiLayout;
    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    BaseRvAdapter<QuestionsBO, BaseViewHolder> adapter;

    FenLeiAdapter fenLeiAdapter;

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
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
                helper.setText(R.id.user_time,item.getCreateDate());
                helper.setText(R.id.wenti_name,item.getTitle());
                helper.setText(R.id.wenti_message,item.getContent());
                helper.setText(R.id.huifu_num,item.getCommentNum() + "回复");
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
//        showProgress(null);
        HttpServerImpl.getQuestionAnswerInfoList(classId)
                .subscribe(new HttpResultSubscriber<List<QuestionsBO>>() {
                    @Override
                    public void onSuccess(List<QuestionsBO> s) {
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


    @OnClick({R.id.sousuo_layout,R.id.bianji_layout})
    public void clickMenu(View view){
        switch (view.getId()){
            case R.id.sousuo_layout:
                gotoActivity(SearchQuestionsActivity.class,false);
                break;
            case R.id.bianji_layout:
                gotoActivity(QuestionAddActivity.class,false);
                break;
        }
    }
}
