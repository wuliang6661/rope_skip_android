package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.bean.MessageModel;
import com.habit.star.ui.mine.contract.MessageListContract;
import com.habit.star.ui.train.adapter.ImprovePlanListAdapter;
import com.habit.star.ui.train.presenter.MessageListPresenter;
import com.habit.star.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @date: 2020-04-23 22:26
 * @ClassName: MessageListFragment.java
 * @Description:
 * @author: sundongdong
 */
public class MessageListFragment extends BaseFragment<MessageListPresenter> implements MessageListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_layout_swipe_to_refresh)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_layout_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progress_fragment_fragment_train_plan_list)
    ProgressbarLayout progress;
    ImprovePlanListAdapter mListAdapter;
    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    Unbinder unbinder;
    private String mModeType;

    public static MessageListFragment newInstance(Bundle bundle) {
        MessageListFragment fragment = new MessageListFragment();
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
        return R.layout.fragment_message_list;
    }

    @Override
    protected String getLogTag() {
        return "MessageListFragment %s";
    }

    @Override
    protected void initEventAndData() {
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });

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

//        mSwipeRefreshLayout.setRefreshing(true);
//        onRefresh();
    }

    @Override
    public void onRefresh() {
        mPresenter.getList(mModeType);
    }

    private void initAdapter() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_test).colorResId(R.color.transparent).build());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mListAdapter = new ImprovePlanListAdapter(mContext);
//        mListAdapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) mRecyclerView.getParent(), false));
//        AppCompatButton mBtnRefresh = (AppCompatButton) mListAdapter.getEmptyView().findViewById(R.id.btn_refresh);
//        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRefresh();
//            }
//        });
//        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void setList(List<MessageModel> orderList) {
        if (mListAdapter == null) {
            return;
        }

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

//        mListAdapter.setNewData(orderList);
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
