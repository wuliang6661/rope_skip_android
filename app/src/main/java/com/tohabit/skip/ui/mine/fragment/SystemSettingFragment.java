package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.ui.AboutActivity;
import com.tohabit.skip.ui.mine.contract.SystemSettingContract;
import com.tohabit.skip.ui.mine.presenter.SystemSettingPresenter;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.UpdateUtils;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建日期：2020-01-21 19:54
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：SystemSettingFragment.java
 * 类说明：系统设置
 */
public class SystemSettingFragment extends BaseFragment<SystemSettingPresenter> implements SystemSettingContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.item_question_fragment_system_setting)
    LilayItemClickableWithHeadImageTopDivider itemQuestion;
    @BindView(R.id.item_check_version_fragment_system_setting)
    LilayItemClickableWithHeadImageTopDivider itemCheckVersion;
    @BindView(R.id.item_about_us_fragment_system_setting)
    LilayItemClickableWithHeadImageTopDivider itemAboutUs;
    @BindView(R.id.progress_fragment_system_setting)
    ProgressbarLayout progress;


    public static SystemSettingFragment newInstance(Bundle bundle) {
        SystemSettingFragment fragment = new SystemSettingFragment();
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
        return R.layout.fragment_system_setting;
    }

    @Override
    protected String getLogTag() {
        return "SystemSettingFragment %s";
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
    @OnClick({R.id.item_question_fragment_system_setting,
            R.id.item_check_version_fragment_system_setting,
            R.id.item_about_us_fragment_system_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_question_fragment_system_setting:
                gotoActivity(FrequentlyActivty.class, false);
                break;
            case R.id.item_check_version_fragment_system_setting:
                new UpdateUtils().checkUpdate(getActivity(), new UpdateUtils.onUpdateListener() {
                    @Override
                    public void noUpdate() {
                        showToast("当前已是最新版本！");
                    }
                });
                break;
            case R.id.item_about_us_fragment_system_setting:
                gotoActivity(AboutActivity.class, false);
                break;
        }
    }
}
