package com.habit.star.ui.young.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.DataBaoGaoBO;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity {

    @BindView(R.id.data_tongji)
    RelativeLayout dataTongji;
    @BindView(R.id.data_baogao)
    RelativeLayout dataBaogao;
    @BindView(R.id.chart1)
    LineChart chart1;
    @BindView(R.id.bar1)
    BarChart bar1;
    @BindView(R.id.chart2)
    LineChart chart2;
    @BindView(R.id.bar2)
    LineChart bar2;
    @BindView(R.id.tongji_layout)
    NestedScrollView tongjiLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.tongji_text)
    TextView tongjiText;
    @BindView(R.id.tongji_view)
    View tongjiView;
    @BindView(R.id.baogao_text)
    TextView baogaoText;
    @BindView(R.id.baogao_view)
    View baogaoView;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_statistic_data;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();

        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError(int errorCode) {

    }


    @OnClick({R.id.data_tongji, R.id.data_baogao})
    public void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.data_tongji:
                tongjiLayout.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
                tongjiText.setTextColor(Color.parseColor("#7EC7F5"));
                tongjiView.setVisibility(View.VISIBLE);
                baogaoText.setTextColor(Color.parseColor("#AAAAAA"));
                baogaoView.setVisibility(View.GONE);
                break;
            case R.id.data_baogao:
                tongjiLayout.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);

                tongjiText.setTextColor(Color.parseColor("#AAAAAA"));
                tongjiView.setVisibility(View.GONE);
                baogaoText.setTextColor(Color.parseColor("#7EC7F5"));
                baogaoView.setVisibility(View.VISIBLE);
                getBaoGaoData();
                break;
        }
    }


    /**
     * 初始化折现图布局
     */
    private void initChart(LineChart chart) {
        // no description text
        chart.getDescription().setEnabled(false);
        // enable touch gestures
        chart.setTouchEnabled(false);
        chart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        // set an alternative background color
        chart.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
//            setData(new ArrayList<>());
        chart.animateY(1000);
        //设置样式
        YAxis rightAxis = chart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //设置图表左边的Y轴禁用
        chart.getAxisLeft().setEnabled(false);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        //设置x轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#6F6F6F"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(7);
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setLabelCount(7, false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置

    }


    /**
     * 初始化柱状图视图
     */
    private void initBar(BarChart mChart) {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        //        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
        //自定义坐标轴适配器，配置在X轴，xAxis.setValueFormatter(xAxisFormatter);
        ValueFormatter xAxisFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value);
            }
        };
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);


        //获取到图形左边的Y轴
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        //获取到图形右边的Y轴，并设置为不显示
        mChart.getAxisRight().setEnabled(false);
        //图例设置
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);
//        setBarChartData();

    }


    /**
     * 获取数据报告列表
     */
    private void getBaoGaoData() {
        HttpServerImpl.getDataReportList().subscribe(new HttpResultSubscriber<List<DataBaoGaoBO>>() {
            @Override
            public void onSuccess(List<DataBaoGaoBO> s) {
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 显示适配器
     */
    private void setAdapter(List<DataBaoGaoBO> s) {
        LGRecycleViewAdapter<DataBaoGaoBO> adapter = new LGRecycleViewAdapter<DataBaoGaoBO>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_baogao;
            }

            @Override
            public void convert(LGViewHolder holder, DataBaoGaoBO dataBaoGaoBO, int position) {
                holder.setText(R.id.baogao_title, dataBaoGaoBO.getTitle());
                holder.setImageUrl(StatisticsActivity.this, R.id.user_img, dataBaoGaoBO.getImage());
                holder.setText(R.id.baogao_message, dataBaoGaoBO.getContent());
                holder.setText(R.id.baogao_time, dataBaoGaoBO.getCreateDate());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", s.get(position).getId());
            gotoActivity(DataDetailsActivity.class, bundle, false);
        });
        recycleView.setAdapter(adapter);
    }

}
