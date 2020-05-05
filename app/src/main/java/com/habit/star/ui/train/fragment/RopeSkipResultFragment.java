package com.habit.star.ui.train.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.train.bean.RopeSkipResultModel;
import com.habit.star.ui.train.contract.RopeSkipResultContract;
import com.habit.star.ui.train.presenter.RopeSkipResultPresenter;
import com.habit.star.utils.DensityUtil;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @version V1.0
 * @date: 2020-02-13 13:03
 * @ClassName: RopeSkipResultFragment.java
 * @Description:
 * @author: sundongdong
 */
public class RopeSkipResultFragment extends BaseFragment<RopeSkipResultPresenter> implements RopeSkipResultContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.ll_back_fragment_energy_detail)
    LinearLayout llBackFragmentEnergyDetail;
    @BindView(R.id.toolbar_layout_toolbar)
    LinearLayout toolbarLayoutToolbar;
    @BindView(R.id.iv_jz_fragment_rope_result)
    AppCompatImageView ivJzFragmentRopeResult;
    @BindView(R.id.btn_save_fragment_rope_skip_setting)
    AppCompatButton btnSaveFragmentRopeSkipSetting;
    @BindView(R.id.ll_again_fragment_rope_result)
    LinearLayout llAgainFragmentRopeResult;
    @BindView(R.id.ll_share_fragment_rope_result)
    LinearLayout llShareFragmentRopeResult;
    @BindView(R.id.iv_user_header_layout_dialog_toast_share_success)
    CircleImageView ivUserHeaderLayoutDialogToastShareSuccess;
    @BindView(R.id.tv_name_layout_dialog_toast_share_success)
    AppCompatTextView tvNameLayoutDialogToastShareSuccess;
    @BindView(R.id.tv_time_layout_dialog_toast_share_success)
    AppCompatTextView tvTimeLayoutDialogToastShareSuccess;
    @BindView(R.id.iv_qr_code_layout_dialog_toast_share_success)
    AppCompatImageView ivQrCodeLayoutDialogToastShareSuccess;
    @BindView(R.id.ll_layout_dialog_toast_share_success)
    LinearLayout llLayoutDialogToastShareSuccess;
    @BindView(R.id.tv_time_fragment_rope_result)
    AppCompatTextView tvTimeFragmentRopeResult;
    @BindView(R.id.tv_number_fragment_rope_result)
    AppCompatTextView tvNumberFragmentRopeResult;
    @BindView(R.id.tv_break_number_fragment_rope_result)
    AppCompatTextView tvBreakNumberFragmentRopeResult;
    @BindView(R.id.tv_average_number_fragment_rope_result)
    AppCompatTextView tvAverageNumberFragmentRopeResult;
    @BindView(R.id.tv_access_fragment_rope_result)
    AppCompatTextView tvAccessFragmentRopeResult;
    @BindView(R.id.tv_height_fragment_rope_result)
    AppCompatTextView tvHeightFragmentRopeResult;


    private Dialog mBottomSheetDialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            llLayoutDialogToastShareSuccess.setVisibility(View.GONE);
        }
    };

    public static RopeSkipResultFragment newInstance(Bundle bundle) {
        RopeSkipResultFragment fragment = new RopeSkipResultFragment();
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
        return R.layout.fragment_rope_result;
    }

    @Override
    protected String getLogTag() {
        return "RopeSkipResultFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        mPresenter.getData();
    }

    @Override
    public void getData(RopeSkipResultModel data) {
        tvTimeFragmentRopeResult.setText(data.time);
        tvNumberFragmentRopeResult.setText(data.ropeNumber);
        tvBreakNumberFragmentRopeResult.setText(data.breakNumber);
        tvAverageNumberFragmentRopeResult.setText(data.averageVelocity);
        tvAccessFragmentRopeResult.setText(data.acceleration);
        tvHeightFragmentRopeResult.setText(data.ropeHeight);
    }

    private void initDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_test_invitation, null);
        view.findViewById(R.id.tv_cancer_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
            }
        });
        view.findViewById(R.id.ll_weixin_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                llLayoutDialogToastShareSuccess.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        view.findViewById(R.id.ll_pyq_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                llLayoutDialogToastShareSuccess.setVisibility(View.VISIBLE);
                llLayoutDialogToastShareSuccess.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        view.findViewById(R.id.ll_save_picture_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                llLayoutDialogToastShareSuccess.setVisibility(View.VISIBLE);
                llLayoutDialogToastShareSuccess.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);

        Window window = mBottomSheetDialog.getWindow();
        WindowManager.LayoutParams paramsWindow = window.getAttributes();
        paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow.height = DensityUtil.dp2px(mContext, 160);//android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        paramsWindow.gravity = Gravity.BOTTOM;
        window.setAttributes(paramsWindow);
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
    @OnClick({R.id.ll_back_fragment_energy_detail,
            R.id.ll_again_fragment_rope_result,
            R.id.btn_save_fragment_rope_skip_setting,
            R.id.ll_share_fragment_rope_result})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_energy_detail:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.btn_save_fragment_rope_skip_setting:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.ll_again_fragment_rope_result:
                mPresenter.tryAgainRope();
                break;
            case R.id.ll_share_fragment_rope_result:
                mBottomSheetDialog.show();
                break;
        }
    }
}
