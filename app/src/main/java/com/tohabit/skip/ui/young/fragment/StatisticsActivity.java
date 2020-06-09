package com.tohabit.skip.ui.young.fragment;

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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.DataBaoGaoBO;
import com.tohabit.skip.pojo.po.StatisticsBO;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
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
    BarChart bar2;
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

    private List<StatisticsBO> statisticsBOS;

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
        initChart(chart1);
        initChart(chart2);
        initBar(bar1);
        initBar(bar2);
        getDataStatistic();
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

                getDataStatistic();
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
     * 查询数据统计
     */
    private void getDataStatistic() {
        HttpServerImpl.getDataStatistic().subscribe(new HttpResultSubscriber<List<StatisticsBO>>() {
            @Override
            public void onSuccess(List<StatisticsBO> s) {
                statisticsBOS = s;
                initChartData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 初始化各自折线图柱状图的数据
     */
    private void initChartData(List<StatisticsBO> s) {
        List<Entry> nums = new ArrayList<>();
        List<BarEntry> cishus = new ArrayList<>();
        List<Entry> sudus = new ArrayList<>();
        List<BarEntry> jiasudus = new ArrayList<>();
        float maxskip = 0;
        float maxbresk = 0;
        float maxaverage = 0;
        float maxaccelerate = 0;
        for (int i = 0; i < s.size(); i++) {
            nums.add(new Entry(i, s.get(i).getSkipTime()));
            cishus.add(new BarEntry(i, s.get(i).getBreakNum()));
            sudus.add(new Entry(i, (float) s.get(i).getAverageVelocity()));
            jiasudus.add(new BarEntry(i, (float) s.get(i).getAccelerateVelocity()));
            if (s.get(i).getSkipTime() > maxskip) {
                maxskip = s.get(i).getSkipTime();
            }
            if (s.get(i).getBreakNum() > maxbresk) {
                maxbresk = s.get(i).getBreakNum();
            }
            if (s.get(i).getAverageVelocity() > maxaverage) {
                maxaverage = (float) s.get(i).getAverageVelocity();
            }
            if (s.get(i).getAccelerateVelocity() > maxaccelerate) {
                maxaccelerate = (float) s.get(i).getAccelerateVelocity();
            }
//            float val = (float) Math.random();
//            nums.add(new Entry(i, val));
//            cishus.add(new BarEntry(i, val));
//            sudus.add(new Entry(i, val));
//            jiasudus.add(new BarEntry(i, val));
        }
        chart1.getAxisLeft().setAxisMinimum(0);
        chart2.getAxisLeft().setAxisMinimum(0);
        bar1.getAxisLeft().setAxisMinimum(0);
        bar2.getAxisLeft().setAxisMinimum(0);
        chart1.getAxisLeft().setAxisMaximum(maxskip + 10);
        chart2.getAxisLeft().setAxisMaximum(maxaverage + 10);
        bar1.getAxisLeft().setAxisMaximum(maxbresk + 10);
        bar2.getAxisLeft().setAxisMaximum(maxaccelerate + 10);
        setNumData(nums, chart1, 0);
        setNumData(sudus, chart2, 1);
        setBarData(cishus, bar1);
        setBarData(jiasudus, bar2);
    }


    /**
     * 设置跳绳数量的数据
     */
    private void setNumData(List<Entry> datas, LineChart chart, int type) {
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return statisticsBOS.get((int) value % statisticsBOS.size()).getCreateDate();
            }
        });
        LineDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(datas);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
            chart.animateY(1000);
        } else {
            set1 = new LineDataSet(datas, "");
            set1.setDrawFilled(true);
            set1.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.chart_fill_bg));
            set1.setColor(Color.parseColor("#00000000"));
            set1.setCircleColor(Color.parseColor("#CEC3F9"));
            set1.setCircleHoleColor(Color.parseColor("#CEC3F9"));
            set1.setLineWidth(2f);
            set1.setCircleRadius(5f);
            set1.setDrawValues(true);
            set1.setValueTextColor(Color.parseColor("#7EC7F5"));
            set1.setValueTextSize(10f);
//            if(type == 0){
//                set1.setValueFormatter(new ValueFormatter() {
//                    @Override
//                    public String getFormattedValue(float value) {
//                        return statisticsBOS.get((int) value % statisticsBOS.size()).getSkipTime() + "分钟";
//                    }
//                });
//            }else{
//                set1.setValueFormatter(new ValueFormatter() {
//                    @Override
//                    public String getFormattedValue(float value) {
//                        return statisticsBOS.get((int) value % statisticsBOS.size()).getAverageVelocity() + "个/分钟";
//                    }
//                });
//            }
            LineData data = new LineData(set1);
            chart.setData(data);
        }
    }


    /**
     * 设置柱状图数据
     */
    private void setBarData(List<BarEntry> barData, BarChart chart) {
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return statisticsBOS.get((int) value % statisticsBOS.size()).getCreateDate();
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
            data.setBarWidth(0.9f);
            chart.setData(data);

            chart.invalidate();
            chart.animateY(1000);
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
//        chart.getAxisLeft().setAxisMinimum(10);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        //设置x轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#B9CAE0"));
        xAxis.setTextSize(12f);
//        xAxis.setAxisMinimum(0);
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
//        xAxis.setLabelCount(7, false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置

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
