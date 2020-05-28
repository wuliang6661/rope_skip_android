package com.habit.star.ui.train.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.TestDetailsBO;
import com.habit.star.ui.train.adapter.ImprovePlanListAdapter;
import com.habit.star.ui.train.bean.ImprovePlanModel;
import com.habit.star.ui.train.contract.TestResultContract;
import com.habit.star.ui.train.presenter.TestResultPresenter;
import com.habit.star.utils.DensityUtil;
import com.habit.star.utils.ToastUtil;
import com.habit.star.widget.CutRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @version V1.0
 * @date: 2020-02-11 17:20
 * @ClassName: TestResultFragment.java
 * @Description:测试结果页面
 * @author: sundongdong
 */
public class TestResultFragment extends BaseFragment<TestResultPresenter> implements TestResultContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.ll_back_fragment_test_result)
    LinearLayout llBack;
    @BindView(R.id.ll_share_fragment_test_result)
    LinearLayout llShare;
    @BindView(R.id.toolbar_layout_toolbar)
    LinearLayout toolbarLayoutToolbar;
    @BindView(R.id.iv_circle1)
    AppCompatImageView ivCircle1;
    @BindView(R.id.ll_circle1)
    LinearLayout llCircle1;
    @BindView(R.id.iv_circle2)
    AppCompatImageView ivCircle2;
    @BindView(R.id.ll_circle2)
    LinearLayout llCircle2;
    @BindView(R.id.iv_circle3)
    AppCompatImageView ivCircle3;
    @BindView(R.id.ll_circle3)
    LinearLayout llCircle3;
    @BindView(R.id.iv_circle4)
    AppCompatImageView ivCircle4;
    @BindView(R.id.ll_circle4)
    LinearLayout llCircle4;
    @BindView(R.id.iv_circle5)
    AppCompatImageView ivCircle5;
    @BindView(R.id.ll_circle5)
    LinearLayout llCircle5;
    @BindView(R.id.iv_circle6)
    AppCompatImageView ivCircle6;
    @BindView(R.id.ll_circle6)
    LinearLayout llCircle6;
    @BindView(R.id.rc_improvement_plan_fragment_test_result)
    RecyclerView rcImprovementPlan;
    @BindView(R.id.tv_this_test_grade_fragment_test_result)
    AppCompatTextView tvThisTestGradeFragmentTestResult;
    @BindView(R.id.iv_user_header_layout_dialog_toast_share_success)
    CircleImageView ivUserHeaderLayoutDialogToastShareSuccess;
    @BindView(R.id.tv_name_layout_dialog_toast_share_success)
    AppCompatTextView tvNameLayoutDialogToastShareSuccess;
    @BindView(R.id.tv_time_layout_dialog_toast_share_success)
    AppCompatTextView tvTimeLayoutDialogToastShareSuccess;
    @BindView(R.id.iv_qr_code_layout_dialog_toast_share_success)
    AppCompatImageView ivQrCodeLayoutDialogToastShareSuccess;
    @BindView(R.id.cr_full_screen_fragment_test_result)
    CutRelativeLayout crFullScreen;

    private String testId;
    private Dialog mBottomSheetDialog;
    private ImprovePlanListAdapter mPlanListAdapter;

    public static TestResultFragment newInstance(Bundle bundle) {
        TestResultFragment fragment = new TestResultFragment();
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
        return R.layout.fragment_test_result;
    }

    @Override
    protected String getLogTag() {
        return "TestResultFragment %s";
    }

    @Override
    protected void initEventAndData() {
        String id = getArguments().getString(RouterConstants.KEY_STRING);
        initDialog();
        initAdapter();
        mPresenter.getList();
        mPresenter.getTestData(id);
    }


    private void initAdapter() {
        rcImprovementPlan.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_test).colorResId(R.color.transparent).build());
        rcImprovementPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlanListAdapter = new ImprovePlanListAdapter(mContext);
        rcImprovementPlan.setAdapter(mPlanListAdapter);
        rcImprovementPlan.addOnItemTouchListener(new OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_look_layout_fragment_improve_plan_list_item:
//                        start(TrainingPlanMainFragment.newInstance(null));
                        break;
                }
            }
        });
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
                start(TestResultShareSuccessFragment.newInstance(null));

            }
        });
        view.findViewById(R.id.ll_pyq_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                start(TestResultShareSuccessFragment.newInstance(null));
            }
        });
        view.findViewById(R.id.ll_save_picture_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                start(TestResultShareSuccessFragment.newInstance(null));
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
    public void setList(TestDetailsBO data) {
        mPlanListAdapter.setNewData(data);
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
    @OnClick({
            R.id.ll_back_fragment_test_result,
            R.id.ll_share_fragment_test_result})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_test_result:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.ll_share_fragment_test_result:
                mBottomSheetDialog.show();
                break;
        }
    }
}
