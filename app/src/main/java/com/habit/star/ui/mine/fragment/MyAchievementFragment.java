package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.habit.commonlibrary.widget.HackyViewPager;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.common.adapter.FragmentVPAdapter;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.ui.mine.contract.MyAchievementContract;
import com.habit.star.ui.mine.presenter.MyAchievementPresenter;
import com.habit.star.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/*
 * 创建日期：2020-01-22 14:47
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyAchievementFragment.java
 * 类说明：我的成就
 */
public class MyAchievementFragment extends BaseFragment<MyAchievementPresenter> implements MyAchievementContract.View {

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

    public static MyAchievementFragment newInstance(Bundle bundle) {
        MyAchievementFragment fragment = new MyAchievementFragment();
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
        return R.layout.fragment_my_achivement;
    }

    @Override
    protected String getLogTag() {
        return "MyAchievementFragment %s";
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
        Bundle bundle = new Bundle();
        fragmentAdapter.addFragment(MyMedalAchievementFragment.newInstance(bundle), "勋章成就");
        fragmentAdapter.addFragment(MyHonorCertificateFragment.newInstance(null), "荣誉证书");
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
