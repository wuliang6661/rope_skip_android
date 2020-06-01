package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.FamilyUserBO;
import com.tohabit.skip.pojo.po.FamilyUserDetailsBO;
import com.tohabit.skip.ui.mine.contract.FamilyMemberDetailContract;
import com.tohabit.skip.ui.mine.presenter.FamilyMemberDetailPresenter;
import com.tohabit.skip.utils.ToastUtil;

import java.util.List;

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


    FamilyUserBO userBO;
    private List<FamilyUserDetailsBO.SkipDataListBean> skipDataListBeans;
    int selectDate = 0;

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            userBO = (FamilyUserBO) bundle.getSerializable(RouterConstants.ARG_BEAN);
            mPresenter.getUser(userBO.getId());
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
    @OnClick({R.id.tv_ts1_last_fragment_family_member_detail,
            R.id.tv_ts1_next_fragment_family_member_detail,
            R.id.tv_ts_last_fragment_family_member_detail,
            R.id.tv_ts_next_fragment_family_member_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ts1_last_fragment_family_member_detail:
                if (selectDate == 0) {
                    showError("前面已经没有了！");
                    return;
                }
                selectDate--;
                setData();
                break;
            case R.id.tv_ts1_next_fragment_family_member_detail:
                if (selectDate >= skipDataListBeans.size() - 1) {
                    showError("后面已经没有了！");
                    return;
                }
                selectDate++;
                setData();
                break;
            case R.id.tv_ts_last_fragment_family_member_detail:
                break;
            case R.id.tv_ts_next_fragment_family_member_detail:
                break;
        }
    }


    @Override
    public void getUserDetails(FamilyUserDetailsBO detailsBO) {
        tvNameFragmentFamilyMemberDetail.setText(detailsBO.getNickName());
        if (detailsBO.getSex() == 0) {
            ivSexFragmentFamilyMemberDetail.setBackgroundResource(R.mipmap.ic_male_tag);
        } else {
            ivSexFragmentFamilyMemberDetail.setBackgroundResource(R.mipmap.ic_nvxing);
        }
        Glide.with(getActivity()).load(detailsBO.getImage()).into(ivHeadFragmentFamilyMemberDetail);
        tvShengGaoFragmentFamilyMemberDetail.setText(detailsBO.getHeight() + "");
        tvTiZhongFragmentFamilyMemberDetail.setText(detailsBO.getWeight() + "");
        tvNianLingFragmentFamilyMemberDetail.setText(detailsBO.getAge() + "");
        skipDataListBeans = detailsBO.getSkipDataList();
        selectDate = skipDataListBeans.size() - 1;
        setData();
    }


    private void setData() {
        tvTsTotalFragmentFamilyMemberDetail.setText(skipDataListBeans.get(selectDate).getSkipNum() + "");
        tvTsTotalTimeFragmentFamilyMemberDetail.setText(skipDataListBeans.get(selectDate).getSkipTime() + "");
        tvTsNianLingFragmentFamilyMemberDetail.setText(skipDataListBeans.get(selectDate).getBreakNum() + "");
        tvTs1DayFragmentFamilyMemberDetail.setText(skipDataListBeans.get(selectDate).getTestDate());
    }
}
