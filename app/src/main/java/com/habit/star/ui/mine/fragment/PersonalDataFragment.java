package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.ui.mine.contract.PersonalDataContract;
import com.habit.star.ui.mine.presenter.PersonalDataPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @version V1.0
 * @date: 2020-03-30 21:49
 * @ClassName: PersonalDataFragment.java
 * @Description:个人资料
 * @author: sundongdong
 */
public class PersonalDataFragment extends BaseFragment<PersonalDataPresenter> implements PersonalDataContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_personal_data)
    ProgressbarLayout progress;
    @BindView(R.id.item_tz_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider person_name;
    @BindView(R.id.item_shoujihaoma_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider person_phone;
    @BindView(R.id.btn_submit_fragment_feed_back)
    AppCompatButton btnSubmitFragmentFeedBack;
    @BindView(R.id.iv_head_fragment_personal_data)
    CircleImageView ivHeadFragmentPersonalData;
    @BindView(R.id.tv_title_fragment_personal_data)
    AppCompatTextView tvTitleFragmentPersonalData;
    @BindView(R.id.ll_head_layout_fragment_personal_data)
    LinearLayout llHeadLayoutFragmentPersonalData;


    public static PersonalDataFragment newInstance(Bundle bundle) {
        PersonalDataFragment fragment = new PersonalDataFragment();
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
        return R.layout.fragment_personal_data;
    }

    @Override
    protected String getLogTag() {
        return "PersonalDataFragment %s";
    }

    @Override
    protected void initEventAndData() {
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getUserInfo();
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

    @Override
    public void getUserInfo(UserBO userBO) {
        App.userBO = userBO;
        Glide.with(getActivity()).load(userBO.getImage()).into(ivHeadFragmentPersonalData);
        person_name.setItemContent(userBO.getNickName());
        person_phone.setItemContent(userBO.getPhone());
        tvTitleFragmentPersonalData.setText("ID " + userBO.getUserCode());
    }


    @OnClick({R.id.item_tz_fragment_perfect_information,
            R.id.item_shoujihaoma_fragment_perfect_information,
            R.id.btn_submit_fragment_feed_back,
            R.id.iv_head_fragment_personal_data,
            R.id.tv_title_fragment_personal_data,
            R.id.ll_head_layout_fragment_personal_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_tz_fragment_perfect_information:
                start(ModifyNickNameFragment.newInstance(null));
                break;
            case R.id.item_shoujihaoma_fragment_perfect_information:
                start(ModifyTelephoneFragment.newInstance(null));
                break;
            case R.id.btn_submit_fragment_feed_back:
                break;
            case R.id.iv_head_fragment_personal_data:
                break;
            case R.id.tv_title_fragment_personal_data:
                break;
            case R.id.ll_head_layout_fragment_personal_data:
                break;
        }
    }
}
