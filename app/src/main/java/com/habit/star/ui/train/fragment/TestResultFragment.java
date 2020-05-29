package com.habit.star.ui.train.fragment;

import android.app.Dialog;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.TestDetailsBO;
import com.habit.star.ui.train.adapter.ImprovePlanListAdapter;
import com.habit.star.ui.train.contract.TestResultContract;
import com.habit.star.ui.train.presenter.TestResultPresenter;
import com.habit.star.ui.young.fragment.VideoExplainActivity;
import com.habit.star.utils.DensityUtil;
import com.habit.star.utils.ToastUtil;
import com.habit.star.utils.Utils;
import com.habit.star.widget.CutRelativeLayout;
import com.habit.star.widget.RadarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    @BindView(R.id.rc_improvement_plan_fragment_test_result)
    RecyclerView rcImprovementPlan;
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
    @BindView(R.id.radar_view)
    RadarView radarView;
    @BindView(R.id.time_text)
    AppCompatTextView timeText;
    @BindView(R.id.skip_num)
    AppCompatTextView skipNum;
    @BindView(R.id.break_num)
    AppCompatTextView breakNum;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    @BindView(R.id.nv_img)
    AppCompatImageView nvImg;
    @BindView(R.id.nan_img)
    AppCompatImageView nanImg;
    @BindView(R.id.zuoshou_line)
    AppCompatImageView zuoshouLine;
    @BindView(R.id.youshou_line)
    AppCompatImageView youshouLine;
    @BindView(R.id.zuojiao_line)
    AppCompatImageView zuojiaoLine;
    @BindView(R.id.youjiao_line)
    AppCompatImageView youjiaoLine;
    @BindView(R.id.zuoshou_yuan)
    AppCompatImageView zuoshouYuan;
    @BindView(R.id.youshou_yuan)
    AppCompatImageView youshouYuan;
    @BindView(R.id.zuojiao_yuan)
    AppCompatImageView zuojiaoYuan;
    @BindView(R.id.youjiao_yuan)
    AppCompatImageView youjiaoYuan;
    @BindView(R.id.zuoshou_text)
    AppCompatTextView zuoshouText;
    @BindView(R.id.youshou_text)
    AppCompatTextView youshouText;
    @BindView(R.id.zuojiao_text)
    AppCompatTextView zuojiaoText;
    @BindView(R.id.youjiao_text)
    AppCompatTextView youjiaoText;
    Unbinder unbinder;

    private Dialog mBottomSheetDialog;
    private ImprovePlanListAdapter mPlanListAdapter;
    private String testId;

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
        testId = getArguments().getString(RouterConstants.KEY_STRING);
        initDialog();
        initAdapter();
        initBar(barChart);
        mPresenter.getTestData(testId);
    }


    private void initAdapter() {
        rcImprovementPlan.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_test).colorResId(R.color.transparent).build());
        rcImprovementPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcImprovementPlan.setNestedScrollingEnabled(false);
        mPlanListAdapter = new ImprovePlanListAdapter(mContext);
        rcImprovementPlan.setAdapter(mPlanListAdapter);
        rcImprovementPlan.addOnItemTouchListener(new OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_look_layout_fragment_improve_plan_list_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", ((TestDetailsBO.PlanListBean) adapter.getItem(position)).getId());
                        gotoActivity(VideoExplainActivity.class, bundle, false);
                        break;
                    case R.id.tv_state_name_layout_fragment_improve_plan_list_item:
                        if (((TestDetailsBO.PlanListBean) adapter.getItem(position)).getStatus() == 0) {
                            addImprovePlan(((TestDetailsBO.PlanListBean) adapter.getItem(position)).getId());
                        }
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
        breakNum.setText(data.getBreakNum() + "");
        skipNum.setText(data.getSkipNum() + "");
        timeText.setText(Utils.timeToString(data.getSkipTime()));
        setBarData(data, barChart);
        showQustion(data);
        List<Double> radarData = new ArrayList<>();
        radarData.add((double) data.getActionScore());
        radarData.add((double) data.getCoordinateScore());
        radarData.add((double) data.getStableScore());
        radarData.add((double) data.getRhythmScore());
        radarData.add((double) data.getEnduranceScore());
        radarView.setData(radarData);
        mPlanListAdapter.setNewData(data.getPlanList());
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


    /**
     * 显示问题分析
     */
    private void showQustion(TestDetailsBO data) {
        int sex = App.xIaoJiangBO.getSex();
        if (sex == 0) {  //男
            nanImg.setVisibility(View.VISIBLE);
            nvImg.setVisibility(View.GONE);
        } else {   //女
            nanImg.setVisibility(View.GONE);
            nvImg.setVisibility(View.VISIBLE);
        }
        if (data.getAnalysisList() == null) {
            return;
        }
        for (TestDetailsBO.AnalysisListBean item : data.getAnalysisList()) {
            switch (item.getFlag()) {
                case 1:
                    zuoshouLine.setVisibility(View.VISIBLE);
                    zuoshouYuan.setVisibility(View.VISIBLE);
                    zuoshouText.setVisibility(View.VISIBLE);
                    zuoshouText.setText(item.getQuestionAnalysis());
                    break;
                case 2:
                    youshouLine.setVisibility(View.VISIBLE);
                    youshouYuan.setVisibility(View.VISIBLE);
                    youshouText.setVisibility(View.VISIBLE);
                    youshouText.setText(item.getQuestionAnalysis());
                    break;
                case 3:
                    zuojiaoLine.setVisibility(View.VISIBLE);
                    zuojiaoYuan.setVisibility(View.VISIBLE);
                    zuojiaoText.setVisibility(View.VISIBLE);
                    zuojiaoText.setText(item.getQuestionAnalysis());
                    break;
                case 4:
                    youshouLine.setVisibility(View.VISIBLE);
                    youjiaoYuan.setVisibility(View.VISIBLE);
                    youjiaoText.setVisibility(View.VISIBLE);
                    youjiaoText.setText(item.getQuestionAnalysis());
                    break;
            }
        }
    }


    /**
     * 初始化柱状图视图
     */
    private void initBar(BarChart mChart) {
        mChart.getDescription().setEnabled(false);
        // enable touch gestures
        mChart.setTouchEnabled(false);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setTextColor(Color.parseColor("#B9CAE0"));
        xAxis.setTextSize(12f);
        xAxis.setLabelCount(2);
//        xAxis.setGranularity(1f);
        //获取到图形左边的Y轴
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setAxisMinimum(10);
        leftAxis.setEnabled(false);
        //获取到图形右边的Y轴，并设置为不显示
        mChart.getAxisRight().setEnabled(false);
        //图例设置
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);

    }


    /**
     * 设置柱状图数据
     */
    private void setBarData(TestDetailsBO detailsBO, BarChart chart) {
        List<BarEntry> barData = new ArrayList<>();
        barData.add(new BarEntry(0, (float) detailsBO.getAverageVelocity()));   //平均速度
        barData.add(new BarEntry(1, (float) detailsBO.getAccelerateVelocity()));  //加速度
        double maxNum = detailsBO.getAverageVelocity() > detailsBO.getAccelerateVelocity() ?
                detailsBO.getAverageVelocity() : detailsBO.getAccelerateVelocity();
        chart.getAxisLeft().setAxisMaximum((float) (maxNum + 10));
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                switch ((int) value) {
                    case 0:
                        return "平均速度\n" + detailsBO.getAverageVelocity() + "个/分钟";
                    case 1:
                        return "加速度\n" + detailsBO.getAccelerateVelocity() + "个/分钟";
                }
                return value + "";
            }
        });
        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(barData);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
            chart.animateY(1000);
        } else {
            set1 = new BarDataSet(barData, "");
            set1.setDrawIcons(false);
            set1.setDrawValues(true);
            set1.setValueTextColor(Color.parseColor("#7EC7F5"));
            set1.setValueTextSize(14f);
            set1.setGradientColor(Color.parseColor("#CFECFC"), Color.parseColor("#ECD1FC"));
            BarData data = new BarData(set1);
            data.setBarWidth(0.4f);
            chart.setData(data);

            chart.invalidate();
            chart.animateY(1000);
        }
    }


    /**
     * 添加训练计划
     */
    private void addImprovePlan(int id) {
        HttpServerImpl.addImprovePlan(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("添加成功!");
                mPresenter.getTestData(testId);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
