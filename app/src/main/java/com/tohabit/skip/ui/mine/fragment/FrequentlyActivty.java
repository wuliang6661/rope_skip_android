package com.tohabit.skip.ui.mine.fragment;

import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sak.ultilviewlib.UltimateRefreshView;
import com.sak.ultilviewlib.interfaces.OnHeaderRefreshListener;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.WenDaBO;
import com.tohabit.skip.widget.TraditionHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1413:23
 * desc   :  常见问题界面
 * version: 1.0
 */
public class FrequentlyActivty extends BaseActivity {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    UltimateRefreshView mSwipeRefreshLayout;

    BaseRvAdapter mListAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        toolbar.setTitle("常见问题");
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initAdapter();

        mSwipeRefreshLayout.setBaseHeaderAdapter(new TraditionHeaderAdapter(this));
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
        onRefresh();
    }

    public void onRefresh() {
        getData();
    }

    private void initAdapter() {
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE)
                .margin(SizeUtils.dp2px(15)).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter = new BaseRvAdapter<WenDaBO, BaseViewHolder>(R.layout.item_question, new ArrayList<>()) {

            @Override
            protected void convert(BaseViewHolder helper, WenDaBO item) {
                helper.setText(R.id.question_title, "Q：" + item.getQuestion());
                helper.setText(R.id.question_message, "A：" + item.getAnswer());
            }
        };
        mListAdapter.setEmptyView(this.getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) recyclerView.getParent(), false));
        AppCompatButton mBtnRefresh = (AppCompatButton) mListAdapter.getEmptyView().findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        recyclerView.setAdapter(mListAdapter);
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

    private void getData() {
        HttpServerImpl.getQuestionList().subscribe(new HttpResultSubscriber<List<WenDaBO>>() {
            @Override
            public void onSuccess(List<WenDaBO> s) {
                mSwipeRefreshLayout.onHeaderRefreshComplete();
                mListAdapter.setNewData(s);
            }

            @Override
            public void onFiled(String message) {
                mSwipeRefreshLayout.onHeaderRefreshComplete();
                showToast(message);
            }
        });
    }
}
