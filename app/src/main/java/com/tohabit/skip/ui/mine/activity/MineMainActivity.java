package com.tohabit.skip.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.ui.login.fragment.PerfectInformationFragment;
import com.tohabit.skip.ui.mine.contract.MineMainContract;
import com.tohabit.skip.ui.mine.fragment.FamilyMemberFragment;
import com.tohabit.skip.ui.mine.fragment.HelpCenterFragment;
import com.tohabit.skip.ui.mine.fragment.MessageListFragment;
import com.tohabit.skip.ui.mine.fragment.MyAchievementFragment;
import com.tohabit.skip.ui.mine.fragment.MyAddressListFragment;
import com.tohabit.skip.ui.mine.fragment.MyPkFragment;
import com.tohabit.skip.ui.mine.fragment.PersonalDataFragment;
import com.tohabit.skip.ui.mine.fragment.SystemSettingFragment;
import com.tohabit.skip.ui.mine.presenter.MineMainPresenter;
import com.tohabit.skip.ui.train.fragment.RopeSkipSettingFragment;

import butterknife.BindView;

/**
 * 创建日期：2018/5/31 14:37
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MineMainActivity.java
 * 类说明：我的模块
 */
//@Router(RouterConstants.MINE)
public class MineMainActivity extends BaseActivity<MineMainPresenter> implements MineMainContract.View {
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
        return "MineMainActivity %s";
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        showFragment = intent.getIntExtra(RouterConstants.ARG_MODE, -1);
        switch (showFragment) {
            case RouterConstants.SHOW_ENTER_HELP_CENTER:
                loadRootFragment(R.id.frame_container_activity_mine_main, HelpCenterFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_SYSTEM_SETTING:
                loadRootFragment(R.id.frame_container_activity_mine_main, SystemSettingFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_RECEIVING_ADDRESS:
//                loadRootFragment(R.id.frame_container_activity_mine_main, MyAddressListFragment.newInstance(null));
                gotoActivity(MyAddressListFragment.class,true);
                break;
            case RouterConstants.SHOW_FAMILY_MEMBER:
                loadRootFragment(R.id.frame_container_activity_mine_main, FamilyMemberFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_PERFECT_INFORMATION:
                loadRootFragment(R.id.frame_container_activity_mine_main, PerfectInformationFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_MY_ACHIVEMENT:
                loadRootFragment(R.id.frame_container_activity_mine_main, MyAchievementFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_MY_PK:
                loadRootFragment(R.id.frame_container_activity_mine_main, MyPkFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_PERSONAL_DATA:
                loadRootFragment(R.id.frame_container_activity_mine_main, PersonalDataFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_MESSAGE_LIST:
                loadRootFragment(R.id.frame_container_activity_mine_main, MessageListFragment.newInstance(null));
                break;
            case RouterConstants.SHOW_MUSIC_SETTING:
                loadRootFragment(R.id.frame_container_activity_mine_main, RopeSkipSettingFragment.newInstance(null));
                break;
//            default:
////                loadRootFragment(R.id.frame_container_activity_mine_main, PerfectInformationFragment.newInstance(null));
//                break;
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
