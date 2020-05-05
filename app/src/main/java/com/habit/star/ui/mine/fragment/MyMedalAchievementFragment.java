package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.ui.mine.contract.MyMedalAchievementContract;
import com.habit.star.ui.mine.presenter.MyMedalAchievementPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;

/*
 * 创建日期：2020-01-22 15:08
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyMedalAchievementFragment.java
 * 类说明：勋章成就
 */
public class MyMedalAchievementFragment extends BaseFragment<MyMedalAchievementPresenter> implements MyMedalAchievementContract.View {

    public static MyMedalAchievementFragment newInstance(Bundle bundle) {
        MyMedalAchievementFragment fragment = new MyMedalAchievementFragment();
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
        return R.layout.fragment_my_medal_achievement_list;
    }

    @Override
    protected String getLogTag() {
        return "MyMedalAchievementFragment %s";
    }

    @Override
    protected void initEventAndData() {
    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

}
