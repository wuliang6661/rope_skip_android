package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.ui.mine.contract.HelpCenterContract;
import com.habit.star.ui.mine.presenter.HelpCenterPresenter;
import com.habit.star.utils.SystemUtil;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 创建日期：2020-01-21 19:07
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：HelpCenterFragment.java
 * 类说明：帮助中心
 */
public class HelpCenterFragment extends BaseFragment<HelpCenterPresenter> implements HelpCenterContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_help_center)
    ProgressbarLayout progress;
    @BindView(R.id.item_xytk_fragment_help_center)
    LilayItemClickableWithHeadImageTopDivider mItemXytk;
    @BindView(R.id.item_kfrx_fragment_help_center)
    LilayItemClickableWithHeadImageTopDivider mItemKfrx;
    @BindView(R.id.item_yjfk_fragment_help_center)
    LilayItemClickableWithHeadImageTopDivider mItemYjfk;


    public static HelpCenterFragment newInstance(Bundle bundle) {
        HelpCenterFragment fragment = new HelpCenterFragment();
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
        return R.layout.fragment_help_center;
    }

    @Override
    protected String getLogTag() {
        return "HelpCenterFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }


    private void initDialog() {

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
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    @SingleClick
    @OnClick({R.id.item_xytk_fragment_help_center, R.id.item_kfrx_fragment_help_center, R.id.item_yjfk_fragment_help_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_xytk_fragment_help_center:
                break;
            case R.id.item_kfrx_fragment_help_center:
                SystemUtil.startPhoneDial(_mActivity,"40000885");
                break;
            case R.id.item_yjfk_fragment_help_center:
                start(FeedbackFragment.newInstance(null));
                break;
        }
    }
}
