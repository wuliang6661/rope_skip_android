package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.contract.FamilyMemberDetailContract;
import com.habit.star.ui.mine.presenter.FamilyMemberDetailPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/*
 * 创建日期：2020-01-22 09:49
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：FamilyMemberDetailFragment.java
 * 类说明：成员详情
 */
public class FamilyMemberDetailFragment extends BaseFragment<FamilyMemberDetailPresenter> implements FamilyMemberDetailContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_family_member_detail)
    ProgressbarLayout progress;
    @BindView(R.id.tv_name_fragment_family_member_detail)
    AppCompatTextView tvNameFragmentFamilyMemberDetail;
    @BindView(R.id.iv_sex_fragment_family_member_detail)
    AppCompatImageView ivSexFragmentFamilyMemberDetail;
    @BindView(R.id.iv_head_fragment_family_member_detail)
    CircleImageView ivHeadFragmentFamilyMemberDetail;
    @BindView(R.id.tv_sheng_gao_fragment_family_member_detail)
    AppCompatTextView tvShengGaoFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ti_zhong_fragment_family_member_detail)
    AppCompatTextView tvTiZhongFragmentFamilyMemberDetail;
    @BindView(R.id.tv_nian_ling_fragment_family_member_detail)
    AppCompatTextView tvNianLingFragmentFamilyMemberDetail;
    @BindView(R.id.tv_tiao_sheng_data_fragment_family_member_detail)
    AppCompatTextView tvTiaoShengDataFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts1_last_fragment_family_member_detail)
    AppCompatTextView tvTs1LastFragmentFamilyMemberDetail;
    @BindView(R.id.ll_pre_day_fragment_family_member_detail)
    LinearLayout llPreDayFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts1_next_fragment_family_member_detail)
    AppCompatTextView tvTs1NextFragmentFamilyMemberDetail;
    @BindView(R.id.ll_next_day_fragment_family_member_detail)
    LinearLayout llNextDayFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_total_fragment_family_member_detail)
    AppCompatTextView tvTsTotalFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_total_time_fragment_family_member_detail)
    AppCompatTextView tvTsTotalTimeFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_nian_ling_fragment_family_member_detail)
    AppCompatTextView tvTsNianLingFragmentFamilyMemberDetail;
    @BindView(R.id.tv_tiao_sheng_data2_fragment_family_member_detail)
    AppCompatTextView tvTiaoShengData2FragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_last_fragment_family_member_detail)
    AppCompatTextView tvTsLastFragmentFamilyMemberDetail;
    @BindView(R.id.ll_pre_day2_fragment_family_member_detail)
    LinearLayout llPreDay2FragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_next_fragment_family_member_detail)
    AppCompatTextView tvTsNextFragmentFamilyMemberDetail;
    @BindView(R.id.ll_next_day2_fragment_family_member_detail)
    LinearLayout llNextDay2FragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_total2_fragment_family_member_detail)
    AppCompatTextView tvTsTotal2FragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_total_time2_fragment_family_member_detail)
    AppCompatTextView tvTsTotalTime2FragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_nian_ling2_fragment_family_member_detail)
    AppCompatTextView tvTsNianLing2FragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts1_day_fragment_family_member_detail)
    AppCompatTextView tvTs1DayFragmentFamilyMemberDetail;
    @BindView(R.id.tv_ts_day_fragment_family_member_detail)
    AppCompatTextView tvTsDayFragmentFamilyMemberDetail;


    private int year1 = 2019;
    private int month1 = 7;
    private int day1 = 2;
    private int year2 = 2019;
    private int month2 = 7;
    private int day2 = 2;

    public static FamilyMemberDetailFragment newInstance(Bundle bundle) {
        FamilyMemberDetailFragment fragment = new FamilyMemberDetailFragment();
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
        return R.layout.fragment_family_member_detail;
    }

    @Override
    protected String getLogTag() {
        return "FamilyMemberDetailFragment %s";
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
        freshView();
    }


    private void freshView() {
        tvTs1DayFragmentFamilyMemberDetail.setText(month1 + "月" + day1 + "日");
        tvTsDayFragmentFamilyMemberDetail.setText(month2 + "月" + day2 + "日");
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
    @OnClick({R.id.tv_ts1_last_fragment_family_member_detail,
            R.id.tv_ts1_next_fragment_family_member_detail,
            R.id.tv_ts_last_fragment_family_member_detail,
            R.id.tv_ts_next_fragment_family_member_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ts1_last_fragment_family_member_detail:
                if (day1 > 1) {
                    day1--;
                } else if (month1 == 1) {
                    year1--;
                    month1 = 12;
                    day1 = 31;
                } else {
                    if (month1 == 2 || month1 == 4 || month1 == 6 || month1 == 8 || month1 == 9 || month1 == 11) {
                        month1--;
                        day1 = 31;
                    } else if (month1 == 3) {
                        month1--;
                        day1 = 28;//先不考虑闰月
                    } else {
                        month1--;
                        day1 = 30;
                    }
                }
                freshView();
                break;
            case R.id.tv_ts1_next_fragment_family_member_detail:
                if (month1 == 1 || month1 == 3 || month1 == 5 || month1 == 7 || month1 == 8 || month1 == 10) {
                    if (day1 == 31) {
                        month1++;
                        day1 = 1;
                    } else {
                        day1++;
                    }
                } else if (month1 == 2) {
                    if (day1 == 28) {
                        month1++;
                        day1 = 1;
                    } else {
                        day1++;
                    }
                } else if (month1 == 12) {
                    year1++;
                    month1 = 1;
                    day1 = 1;
                } else {
                    if (day1 == 30) {
                        month1++;
                        day1 = 1;
                    } else {
                        day1++;
                    }
                }
                freshView();
                break;
            case R.id.tv_ts_last_fragment_family_member_detail:
                if (day2 > 1) {
                    day2--;
                } else if (month2 == 1) {
                    year2--;
                    month2 = 12;
                    day2 = 31;
                } else {
                    if (month2 == 2 || month2 == 4 || month2 == 6 || month2 == 8 || month2 == 9 || month2 == 11) {
                        month2--;
                        day2 = 31;
                    } else if (month2 == 3) {
                        month2--;
                        day2 = 28;//先不考虑闰月
                    } else {
                        month2--;
                        day2 = 30;
                    }
                }
                freshView();
                break;
            case R.id.tv_ts_next_fragment_family_member_detail:
                if (month2 == 1 || month2 == 3 || month2 == 5 || month2 == 7 || month2 == 8 || month2 == 10) {
                    if (day2 == 31) {
                        month2++;
                        day2 = 1;
                    } else {
                        day2++;
                    }
                } else if (month2 == 2) {
                    if (day2 == 28) {
                        month2++;
                        day2 = 1;
                    } else {
                        day2++;
                    }
                } else if (month2 == 12) {
                    year2++;
                    month2 = 1;
                    day2 = 1;
                } else {
                    if (day2 == 30) {
                        month2++;
                        day2 = 1;
                    } else {
                        day2++;
                    }
                }
                freshView();
                break;
        }
    }


}
