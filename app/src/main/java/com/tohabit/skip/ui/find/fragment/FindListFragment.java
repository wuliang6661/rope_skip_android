package com.tohabit.skip.ui.find.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.ui.find.adapter.FindListAdapter;
import com.tohabit.skip.ui.find.bean.FindModel;
import com.tohabit.skip.ui.find.contract.FindListContract;
import com.tohabit.skip.ui.find.presenter.FindListPresenter;
import com.tohabit.skip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @version V1.0
 * @date: 2020-02-26 23:06
 * @ClassName: FindListFragment.java
 * @Description:发现列表
 * @author: sundongdong
 */
public class FindListFragment extends BaseFragment<FindListPresenter> implements FindListContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progress_fragment_fragment_find_list)
    ProgressbarLayout progress;
//    @BindView(R.id.tv_proccess_fragment_find_list)
//    AppCompatTextView tvProccessFragmentFindList;
//    @BindView(R.id.tv_already_report_fragment_find_list)
//    AppCompatTextView tvAlreadyReportFragmentFindList;
//    @BindView(R.id.tv_finish_fragment_find_list)
//    AppCompatTextView tvFinishFragmentFindList;
//    @BindView(R.id.ll_toolbar_layout_toolbar)
//    LinearLayout llToolbarLayoutToolbar;

    private String mModeType;
    private int state = 0;
    FindListAdapter mListAdapter;

    public static FindListFragment newInstance(Bundle bundle) {
        FindListFragment fragment = new FindListFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_list;
    }

    @Override
    protected String getLogTag() {
        return "FindListFragmet %s";
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        mModeType = bundle.getString(RouterConstants.ARG_MODE, "");
        initAdapter();
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (mListAdapter == null
//                        || mListAdapter.getData() == null
//                        || mListAdapter.getData().size() <= position) {
//                    return;
//                }

            }
        });

        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mPresenter.getList(mModeType);
    }

    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE).build());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListAdapter = new FindListAdapter(mContext);
        mListAdapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) mRecyclerView.getParent(), false));
        AppCompatButton mBtnRefresh = (AppCompatButton) mListAdapter.getEmptyView().findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void setList(List<FindModel> orderList) {
        if (mListAdapter == null) {
            return;
        }

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mListAdapter.setNewData(orderList);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

//    @SingleClick
//    @OnClick({R.id.tv_proccess_fragment_find_list,
//            R.id.tv_already_report_fragment_find_list,
//            R.id.tv_finish_fragment_find_list})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_proccess_fragment_find_list:
//                state = 0;
//                statasChange();
//                break;
//            case R.id.tv_already_report_fragment_find_list:
//                state = 1;
//                statasChange();
//                break;
//            case R.id.tv_finish_fragment_find_list:
//                state = 2;
//                statasChange();
//                break;
//        }
//    }
    private void statasChange(){
        switch (state){
            case 0:
//                tvProccessFragmentFindList.setTextColor(getResources().getColor(R.color.color_7EC7F5));
//                tvAlreadyReportFragmentFindList.setTextColor(getResources().getColor(R.color.color_888888));
//                tvFinishFragmentFindList.setTextColor(getResources().getColor(R.color.color_888888));
//                onRefresh();
//                break;
//            case 1:
//                tvProccessFragmentFindList.setTextColor(getResources().getColor(R.color.color_888888));
//                tvAlreadyReportFragmentFindList.setTextColor(getResources().getColor(R.color.color_7EC7F5));
//                tvFinishFragmentFindList.setTextColor(getResources().getColor(R.color.color_888888));
//                onRefresh();
//                break;
//            case 2:
//                tvProccessFragmentFindList.setTextColor(getResources().getColor(R.color.color_888888));
//                tvAlreadyReportFragmentFindList.setTextColor(getResources().getColor(R.color.color_888888));
//                tvFinishFragmentFindList.setTextColor(getResources().getColor(R.color.color_7EC7F5));
//                onRefresh();
                break;
        }

    }
}
