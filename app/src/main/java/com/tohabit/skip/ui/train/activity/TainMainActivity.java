package com.tohabit.skip.ui.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.ui.mine.presenter.MineMainPresenter;
import com.tohabit.skip.ui.mine.contract.MineMainContract;
import com.tohabit.skip.ui.train.fragment.BaseMsgInputFragment;
import com.tohabit.skip.ui.train.fragment.EnergyValueFragment;
import com.tohabit.skip.ui.train.fragment.TestResultFragment;
import com.tohabit.skip.ui.train.fragment.TrainPlanFragment;
import com.tohabit.skip.ui.train.fragment.TrainingPlanMainFragment;

import butterknife.BindView;


/**
 * @version V1.0
 * @date: 2020-02-10 16:54
 * @ClassName: TainMainActivity.java
 * @Description:
 * @author: sundongdong
 */

public class TainMainActivity extends BaseActivity<MineMainPresenter> implements MineMainContract.View {
    @BindView(R.id.frame_container_activity_mine_main)
    FrameLayout containerFrame;

    int showFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_main;
    }

    @Override
    protected String getLogTag() {
        return "YoungMainActivity %s";
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        showFragment = intent.getIntExtra(RouterConstants.ARG_MODE, -1);
        switch (showFragment) {
            case RouterConstants.BASE_MSG_INPUT:
                loadRootFragment(R.id.frame_container_activity_mine_main, BaseMsgInputFragment.newInstance(null));
                break;
            case RouterConstants.TEST_RESULT:
                loadRootFragment(R.id.frame_container_activity_mine_main, TestResultFragment.newInstance(intent.getBundleExtra(RouterConstants.ARG_BUNDLE)));
                break;
            case RouterConstants.ENERGY_VALUE:
                loadRootFragment(R.id.frame_container_activity_mine_main, EnergyValueFragment.newInstance(null));
                break;
            case RouterConstants.ROPE_SKIP_RESULTS:
                loadRootFragment(R.id.frame_container_activity_mine_main, TrainPlanFragment.newInstance(intent.getExtras()));
                break;
            case RouterConstants.ROPE_PLAN_MAIN:
                loadRootFragment(R.id.frame_container_activity_mine_main, TrainingPlanMainFragment.newInstance(null));
                break;
            default:
//                loadRootFragment(R.id.frame_container_activity_mine_main, PerfectInformationFragment.newInstance(null));
                break;
        }
    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        showMsg(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
