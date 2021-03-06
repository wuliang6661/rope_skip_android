package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.ui.XieYiActivity;
import com.tohabit.skip.ui.mine.contract.HelpCenterContract;
import com.tohabit.skip.ui.mine.presenter.HelpCenterPresenter;
import com.tohabit.skip.utils.SystemUtil;
import com.tohabit.skip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.yingsizhengce)
    LilayItemClickableWithHeadImageTopDivider yingsizhengce;


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
    @OnClick({R.id.item_xytk_fragment_help_center, R.id.item_kfrx_fragment_help_center,
            R.id.item_yjfk_fragment_help_center,R.id.yingsizhengce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_xytk_fragment_help_center:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                gotoActivity(XieYiActivity.class, bundle, false);
                break;
            case R.id.yingsizhengce:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 1);
                gotoActivity(XieYiActivity.class, bundle1, false);
                break;
            case R.id.item_kfrx_fragment_help_center:
                getKefu();
                break;
            case R.id.item_yjfk_fragment_help_center:
                start(FeedbackFragment.newInstance(null));
                break;
        }
    }



    private void getKefu(){
        HttpServerImpl.getTelephone().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                SystemUtil.startPhoneDial(_mActivity, s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }



}
