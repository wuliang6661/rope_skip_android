package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.contract.MyPkContract;
import com.habit.star.ui.mine.presenter.MyPkPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;

/*
 * 创建日期：2019-10-21 21:30
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyPkFragment.java
 * 类说明：我的Pk值
 */
public class MyPkFragment extends BaseFragment<MyPkPresenter> implements MyPkContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;


    public static MyPkFragment newInstance(Bundle bundle) {
        MyPkFragment fragment = new MyPkFragment();
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
        return R.layout.fragment_my_pk;
    }

    @Override
    protected String getLogTag() {
        return "MyPkFragment %s";
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
