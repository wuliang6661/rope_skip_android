package com.habit.star.ui.login.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.login.contract.PerfectInformationContract;
import com.habit.star.ui.login.presenter.PerfectInformationPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 创建日期：2020-01-22 10:54
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：PerfectInformationFragment.java
 * 类说明：完善资料
 */
public class PerfectInformationFragment extends BaseFragment<PerfectInformationPresenter> implements PerfectInformationContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_perfect_information)
    ProgressbarLayout progress;
    @BindView(R.id.item_nick_name_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider itemNickNameFragmentPerfectInformation;
    @BindView(R.id.item_sg_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider itemSgFragmentPerfectInformation;
    @BindView(R.id.item_tz_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider itemTzFragmentPerfectInformation;
    @BindView(R.id.item_sex_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider itemSexFragmentPerfectInformation;
    @BindView(R.id.btn_submit_fragment_feed_back)
    AppCompatButton btnSubmitFragmentFeedBack;


    public static PerfectInformationFragment newInstance(Bundle bundle) {
        PerfectInformationFragment fragment = new PerfectInformationFragment();
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
        return R.layout.fragment_perfect_information;
    }

    @Override
    protected String getLogTag() {
        return "PerfectInformationFragment %s";
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

    @OnClick({R.id.item_nick_name_fragment_perfect_information,
            R.id.item_sg_fragment_perfect_information,
            R.id.item_tz_fragment_perfect_information,
            R.id.item_sex_fragment_perfect_information,
            R.id.btn_submit_fragment_feed_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_nick_name_fragment_perfect_information:
                break;
            case R.id.item_sg_fragment_perfect_information:
                break;
            case R.id.item_tz_fragment_perfect_information:
                break;
            case R.id.item_sex_fragment_perfect_information:
                break;
            case R.id.btn_submit_fragment_feed_back:
                showError("领取成功");
                _mActivity.onBackPressedSupport();
                break;
        }
    }
}
