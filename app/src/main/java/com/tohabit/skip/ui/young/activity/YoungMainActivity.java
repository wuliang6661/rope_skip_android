package com.tohabit.skip.ui.young.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.ui.mine.contract.MineMainContract;
import com.tohabit.skip.ui.mine.presenter.MineMainPresenter;

import butterknife.BindView;



/**
 * @date:  2020-02-10 16:54
 * @ClassName: YoungMainActivity.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */

public class YoungMainActivity extends BaseActivity<MineMainPresenter> implements MineMainContract.View {


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
//        Intent intent = getIntent();
//        showFragment = intent.getIntExtra(RouterConstants.ARG_MODE, -1);
//        switch (showFragment) {
//            default:
////                loadRootFragment(R.id.frame_container_activity_mine_main, PerfectInformationFragment.newInstance(null));
//                break;
//        }
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
