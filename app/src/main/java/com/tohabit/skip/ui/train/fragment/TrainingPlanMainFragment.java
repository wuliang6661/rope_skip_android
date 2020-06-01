package com.tohabit.skip.ui.train.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.tohabit.commonlibrary.widget.HackyViewPager;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.common.adapter.FragmentVPAdapter;
import com.tohabit.skip.presenter.CommonPresenter;
import com.tohabit.skip.presenter.contract.CommonContract;
import com.tohabit.skip.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date:  2020-02-12 16:19
 * @ClassName: TrainingPlanMainFragment.java
 * @Description:训练计划主页
 * @author: sundongdong
 * @version V1.0
 */
public class TrainingPlanMainFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_my_achivement)
    ProgressbarLayout progress;
    @BindView(R.id.tabs_fragment_my_achivement)
    TabLayout tabLayout;
    @BindView(R.id.appbar_fragment_my_achivement)
    AppBarLayout appbar;
    @BindView(R.id.viewpager_fragment_my_achivement)
    HackyViewPager viewPager;

    private FragmentVPAdapter fragmentAdapter;

    public static TrainingPlanMainFragment newInstance(Bundle bundle) {
        TrainingPlanMainFragment fragment = new TrainingPlanMainFragment();
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
        return R.layout.fragment_train_plan_main;
    }

    @Override
    protected String getLogTag() {
        return "TrainingPlanMainFragment %s";
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
        fragmentAdapter = new FragmentVPAdapter(getChildFragmentManager(), new ArrayList<Fragment>(), new ArrayList<String>());
        Bundle bundle1 = new Bundle();
        bundle1.putString(RouterConstants.ARG_MODE,"1");
        Bundle bundle2 = new Bundle();
        bundle2.putString(RouterConstants.ARG_MODE,"2");
        fragmentAdapter.addFragment(TrainPlanListFragment.newInstance(bundle1), "今日未完成计划");
        fragmentAdapter.addFragment(TrainPlanListFragment.newInstance(bundle2), "今日已完成计划");
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setLocked(true);
    }
    @Override
    public boolean onBackPressedSupport() {
        _mActivity.onBackPressedSupport();
        return true;
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
