package com.tohabit.skip.ui.train.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.TrainBO;
import com.tohabit.skip.ui.train.contract.RopeSkipResultContract;
import com.tohabit.skip.ui.train.presenter.RopeSkipResultPresenter;
import com.tohabit.skip.utils.DensityUtil;
import com.tohabit.skip.utils.ScreenShotUtils;
import com.tohabit.skip.utils.ShareUtils;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.Utils;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;


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
    @BindView(R.id.no_share_layout)
    LinearLayout noShareLayout;
    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.shape_date)
    TextView shapeDate;
    @BindView(R.id.user_qr_code)
    ImageView userQrCode;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;


    private Dialog mBottomSheetDialog;

    private String id;

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
        id = getArguments().getString("id");
        mPresenter.getTrain(id);

        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        userName.setText(App.userBO.getNickName());
        shapeDate.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        getQrCode();
    }

    @Override
    public void getData(TrainBO data) {
        if(data == null){
            return;
        }
        String time = Utils.timeToString(data.getSkipTime());
        tvTimeFragmentRopeResult.setText(time);
        tvNumberFragmentRopeResult.setText(data.getSkipNum() + "");
        tvBreakNumberFragmentRopeResult.setText(data.getBreakNum() + "");
        tvAverageNumberFragmentRopeResult.setText(data.getAverageVelocity() + "");
        tvAccessFragmentRopeResult.setText(data.getAccelerateVelocity() + "");
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
                shareImage(0);
            }
        });
        view.findViewById(R.id.ll_pyq_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                shareImage(1);
            }
        });
        view.findViewById(R.id.ll_save_picture_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                saveImgs();
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
                getActivity().finish();
                break;
            case R.id.ll_again_fragment_rope_result:
                mPresenter.tryAgainRope();
                break;
            case R.id.ll_share_fragment_rope_result:
                mBottomSheetDialog.show();
                break;
        }
    }


    private void getQrCode() {
        HttpServerImpl.getQrCode().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Glide.with(getActivity()).load(s).into(userQrCode);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 保存图片
     */
    private void saveImgs() {
        toolbarLayoutToolbar.setVisibility(View.GONE);
        noShareLayout.setVisibility(View.GONE);
        btnSaveFragmentRopeSkipSetting.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
        showProgress(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopProgress();
                boolean isSave = ScreenShotUtils.shotScreen(getActivity());
                if (isSave) {
                    showToast("保存成功！");
                } else {
                    showToast("保存失败！");
                }
                toolbarLayoutToolbar.setVisibility(View.VISIBLE);
                noShareLayout.setVisibility(View.VISIBLE);
                btnSaveFragmentRopeSkipSetting.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
        }, 500);
    }


    /**
     * 分享图片
     */
    private void shareImage(int flags) {
        toolbarLayoutToolbar.setVisibility(View.GONE);
        noShareLayout.setVisibility(View.GONE);
        btnSaveFragmentRopeSkipSetting.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ScreenShotUtils.getBitMap(getActivity());
                ShareUtils.shareImage(flags, bitmap);
                toolbarLayoutToolbar.setVisibility(View.VISIBLE);
                noShareLayout.setVisibility(View.VISIBLE);
                btnSaveFragmentRopeSkipSetting.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
        }, 500);
    }


}
