package com.tohabit.skip.ui.common;

import android.os.Bundle;
import android.view.View;

import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.presenter.CommonPresenter;
import com.tohabit.skip.presenter.contract.CommonContract;
import com.tohabit.skip.utils.ToastUtil;

import butterknife.BindView;


/**
 * @date:  2020-02-09 16:04
 * @ClassName: CommonFragment.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */

public class CommonFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;


    public static CommonFragment newInstance(Bundle bundle) {
        CommonFragment fragment = new CommonFragment();
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
        return R.layout.fragment_common_view;
    }

    @Override
    protected String getLogTag() {
        return "CommonFragment %s";
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

}
