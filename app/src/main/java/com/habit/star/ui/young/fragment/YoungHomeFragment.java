package com.habit.star.ui.young.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.ui.mine.activity.MineMainActivity;
import com.habit.star.ui.train.activity.TainMainActivity;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * @version V1.0
 * @date: 2020-02-13 21:31
 * @ClassName: YoungHomeFragment.java
 * @Description:小将主页
 * @author: sundongdong
 */
public class YoungHomeFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbarLayoutToolbar;
    @BindView(R.id.iv_user_header_fragment_young_home)
    CircleImageView ivUserHeaderFragmentYoungHome;
    @BindView(R.id.ic_cj_fragment_young_home)
    AppCompatImageView icCjFragmentYoungHome;
    @BindView(R.id.ic_rw_fragment_young_home)
    AppCompatImageView icRwFragmentYoungHome;
    @BindView(R.id.ic_rw2_fragment_young_home)
    AppCompatImageView icRw2FragmentYoungHome;
    @BindView(R.id.iv_close_bottom_fragment_young_home)
    AppCompatImageView ivCloseBottomFragmentYoungHome;
    @BindView(R.id.tv_rw_lable_fragment_young_home)
    AppCompatTextView tvRwLableFragmentYoungHome;
    @BindView(R.id.divider_rw_lable_fragment_young_home)
    View dividerRwLableFragmentYoungHome;
    @BindView(R.id.tv_go_tz_fragment_young_home)
    AppCompatTextView tvGoTzFragmentYoungHome;
    @BindView(R.id.tv_go_read_fragment_young_home)
    AppCompatTextView tvGoReadFragmentYoungHome;
    @BindView(R.id.tv_go_study_fragment_young_home)
    AppCompatTextView tvGoStudyFragmentYoungHome;
    @BindView(R.id.tv_go_finish_fragment_young_home)
    AppCompatTextView tvGoFinishFragmentYoungHome;
    @BindView(R.id.tv_go_cg_fragment_young_home)
    AppCompatTextView tvGoCgFragmentYoungHome;
    @BindView(R.id.rl_zrw_bottom_fragment_young_home)
    RelativeLayout rlZrwBottomFragmentYoungHome;
    @BindView(R.id.ll_icon_js_pk1_fragment_young_home)
    LinearLayout llIconJsPk1FragmentYoungHome;
    @BindView(R.id.ll_icon_js_pk2_fragment_young_home)
    LinearLayout llIconJsPk2FragmentYoungHome;
    @BindView(R.id.ll_icon_js_pk3_fragment_young_home)
    LinearLayout llIconJsPk3FragmentYoungHome;
    @BindView(R.id.ll_icon_js_pk4_fragment_young_home)
    LinearLayout llIconJsPk4FragmentYoungHome;
    @BindView(R.id.ll_icon_js_pk5_fragment_young_home)
    LinearLayout llIconJsPk5FragmentYoungHome;
    @BindView(R.id.gi_view_monkey_fragment_young_home)
    GifImageView giViewMonkeyFragmentYoungHome;


    GifDrawable gifDrawable;
    @BindView(R.id.tv_nlz)
    AppCompatTextView tvNlz;
    @BindView(R.id.ll_nlz)
    LinearLayout llNlz;
    @BindView(R.id.tv_cj)
    AppCompatImageView tvCj;
    @BindView(R.id.ll_cj)
    LinearLayout llCj;

    public static YoungHomeFragment newInstance(Bundle bundle) {
        YoungHomeFragment fragment = new YoungHomeFragment();
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
        return R.layout.fragment_young_home;
    }

    @Override
    protected String getLogTag() {
        return "YoungHomeFragment %s";
    }

    @Override
    protected void initEventAndData() {
        gifDrawable = (GifDrawable) giViewMonkeyFragmentYoungHome.getDrawable();
        gifDrawable.start();
        initDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (gifDrawable != null) {
            gifDrawable.start();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (gifDrawable != null) {
            gifDrawable.stop();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gifDrawable != null) {
            gifDrawable.stop();
            gifDrawable = null;
        }
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
    @OnClick({R.id.ic_cj_fragment_young_home,
            R.id.ic_rw_fragment_young_home,
            R.id.ic_rw2_fragment_young_home,
            R.id.iv_close_bottom_fragment_young_home,
            R.id.tv_go_tz_fragment_young_home,
            R.id.tv_go_read_fragment_young_home,
            R.id.tv_go_study_fragment_young_home,
            R.id.tv_go_finish_fragment_young_home,
            R.id.tv_go_cg_fragment_young_home,
            R.id.ll_nlz, R.id.ll_cj})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ic_cj_fragment_young_home:
//                showError("正在开发中");
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_MY_ACHIVEMENT);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ic_rw_fragment_young_home:
                rlZrwBottomFragmentYoungHome.setVisibility(View.VISIBLE);
                break;
            case R.id.ic_rw2_fragment_young_home:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_MY_ACHIVEMENT);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_close_bottom_fragment_young_home:
                rlZrwBottomFragmentYoungHome.setVisibility(View.GONE);
                break;
            case R.id.tv_go_tz_fragment_young_home:
                showError("正在开发中");
                break;
            case R.id.tv_go_read_fragment_young_home:
                showError("正在开发中");
                break;
            case R.id.tv_go_study_fragment_young_home:
                showError("正在开发中");
                break;
            case R.id.tv_go_finish_fragment_young_home:
                showError("正在开发中");
                break;
            case R.id.tv_go_cg_fragment_young_home:
                showError("正在开发中");
                break;
            case R.id.ll_nlz:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.ENERGY_VALUE);
                intent.setClass(_mActivity, TainMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_cj:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_MY_PK);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
