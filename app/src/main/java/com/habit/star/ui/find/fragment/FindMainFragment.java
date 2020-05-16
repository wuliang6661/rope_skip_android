package com.habit.star.ui.find.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.habit.commonlibrary.widget.HackyViewPager;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.common.adapter.FragmentVPAdapter;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date:  2020-02-26 23:05
 * @ClassName: FindMainFragment.java
 * @Description:发现主页
 * @author: sundongdong
 * @version V1.0
 */
public class FindMainFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.progress_fragment_my_achivement)
    ProgressbarLayout progress;
    @BindView(R.id.tabs_fragment_my_achivement)
    TabLayout tabLayout;
    @BindView(R.id.appbar_fragment_my_achivement)
    AppBarLayout appbar;
    @BindView(R.id.viewpager_fragment_my_achivement)
    HackyViewPager viewPager;

    private FragmentVPAdapter fragmentAdapter;

    public static FindMainFragment newInstance(Bundle bundle) {
        FindMainFragment fragment = new FindMainFragment();
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
        return R.layout.fragment_find_main;
    }

    @Override
    protected String getLogTag() {
        return "FindMainFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        fragmentAdapter = new FragmentVPAdapter(getChildFragmentManager(), new ArrayList<Fragment>(), new ArrayList<String>());
        fragmentAdapter.addFragment(HuodongFragment.newInstance(new Bundle()), "精选活动");
        fragmentAdapter.addFragment(new KeChengFragment(), "教程");
        fragmentAdapter.addFragment(new ZhiShiFragment(), "跳绳知识");
        fragmentAdapter.addFragment(new QuestionsFragment(), "百问百答");
        fragmentAdapter.addFragment(new ExChangeFragment(), "趣味商城");
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setLocked(true);
        viewPager.setOffscreenPageLimit(fragmentAdapter.getCount()-1);//设置缓存所有
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
