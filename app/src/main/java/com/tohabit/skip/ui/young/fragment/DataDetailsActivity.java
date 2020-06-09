package com.tohabit.skip.ui.young.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.BaoGaoDetailsBO;
import com.tohabit.skip.utils.ScreenShotUtils;
import com.tohabit.skip.utils.ShareUtils;
import com.tohabit.skip.widget.ShapeDialog;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2615:27
 * desc   : 数据报告详情界面
 * version: 1.0
 */
public class DataDetailsActivity extends BaseActivity {

    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_pk_icon)
    RoundedImageView userPkIcon;
    @BindView(R.id.pk_vaule)
    TextView pkVaule;
    @BindView(R.id.wancheng_num)
    TextView wanchengNum;
    @BindView(R.id.leiji_num)
    TextView leijiNum;
    @BindView(R.id.tiaozhan_num)
    TextView tiaozhanNum;
    @BindView(R.id.bt_share_pk)
    TextView btSharePk;
    @BindView(R.id.task_recycle)
    RecyclerView taskRecycle;
    @BindView(R.id.line_chart)
    LineChart chart;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.shape_date)
    TextView shapeDate;
    @BindView(R.id.user_qr_code)
    ImageView userQrCode;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    private BaoGaoDetailsBO detailsBO;

    ShapeDialog shapeDialog;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_data_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("数据详情");

        taskRecycle.setLayoutManager(new LinearLayoutManager(this));
        taskRecycle.setNestedScrollingEnabled(false);

        int id = getIntent().getExtras().getInt("id");

        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        Glide.with(this).load(App.xIaoJiangBO.getIcon()).into(userPkIcon);
        initChart();
        getDataMsg(id);

        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        userName.setText(App.userBO.getNickName());
        shapeDate.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        getQrCode();
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

    /**
     * 获取数据报告详情
     */
    private void getDataMsg(int id) {
        HttpServerImpl.getDataReport(id).subscribe(new HttpResultSubscriber<BaoGaoDetailsBO>() {
            @Override
            public void onSuccess(BaoGaoDetailsBO s) {
                detailsBO = s;
                setData();
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 分享
     */
    @OnClick(R.id.bt_share_pk)
    public void sharePK() {
        shapeDialog = ShapeDialog.getInatance(this);
        shapeDialog.setListener(new ShapeDialog.onSelectListener() {
            @Override
            public void clickWeiXin() {
                shareImage(0);
            }

            @Override
            public void clickPYQ() {
                shareImage(1);
            }

            @Override
            public void saveImg() {
                saveImgs();
            }
        });
        shapeDialog.show();
    }


    /**
     * 设置界面显示
     */
    private void setData() {
        pkVaule.setText(detailsBO.getNewPkValue() + "");
        wanchengNum.setText(detailsBO.getCompleteTaskNum() + "个");
        leijiNum.setText(detailsBO.getSkipNum() + "个");
        tiaozhanNum.setText(detailsBO.getPkChallengeNum() + "次");

        setNumData();
    }


    /**
     * 初始化折现图布局
     */
    private void initChart() {
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
     * 设置跳绳数量的数据
     */
    private void setNumData() {
        List<BaoGaoDetailsBO.SkipListBean> skipListBeans = detailsBO.getSkipList();
        List<Entry> entries = new ArrayList<>();
        int maxNum = 0;
        for (int i = 0; i < skipListBeans.size(); i++) {
            entries.add(new Entry(i, skipListBeans.get(i).getSkipNum()));
            if (skipListBeans.get(i).getSkipNum() > maxNum) {
                maxNum = skipListBeans.get(i).getSkipNum();
            }
        }
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(maxNum + 10);
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return skipListBeans.get((int) value % skipListBeans.size()).getSkipDate();
            }
        });
        LineDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
            chart.animateY(1000);
        } else {
            set1 = new LineDataSet(entries, "");
            set1.setDrawFilled(true);
            set1.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.chart_fill_bg));
            set1.setColor(Color.parseColor("#ECD1FC"));
            set1.setCircleColor(Color.parseColor("#CEC3F9"));
            set1.setCircleHoleColor(Color.parseColor("#CEC3F9"));
            set1.setLineWidth(2f);
            set1.setCircleRadius(5f);
            set1.setDrawValues(true);
            set1.setValueTextColor(Color.parseColor("#7EC7F5"));
            set1.setValueTextSize(10f);
            LineData data = new LineData(set1);
            chart.setData(data);
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        LGRecycleViewAdapter<BaoGaoDetailsBO.TaskListBean> adapter =
                new LGRecycleViewAdapter<BaoGaoDetailsBO.TaskListBean>(detailsBO.getTaskList()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        if (viewType == 0) {
                            return R.layout.item_data_details_left;
                        } else {
                            return R.layout.item_data_details_right;
                        }
                    }

                    @Override
                    public void convert(LGViewHolder holder, BaoGaoDetailsBO.TaskListBean s, int position) {
                        holder.setText(R.id.task_num, s.getCompleteNum() + "");
                        switch (s.getTaskType()) {
                            case 1:
                                holder.setText(R.id.task_content, "本周完成PK任务");
                                break;
                            case 2:
                                holder.setText(R.id.task_content, "本周完成幸运兑换");
                                break;
                            case 3:
                                holder.setText(R.id.task_content, "本周完成课程学习");
                                break;
                            case 4:
                                holder.setText(R.id.task_content, "本周完成知识学习");
                                break;
                            case 5:
                                holder.setText(R.id.task_content, "本周完成参与问答");
                                break;
                        }
                    }

                    @Override
                    public int getItemViewType(int position) {
                        return position % 2;
                    }
                };
        taskRecycle.setAdapter(adapter);
    }

    private void getQrCode() {
        HttpServerImpl.getQrCode().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Glide.with(DataDetailsActivity.this).load(s).into(userQrCode);
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
        titleLayout.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
        showProgress(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopProgress();
                Bitmap bitmap = ScreenShotUtils.getScrollViewBitmap(scrollView);
                boolean isSave = ScreenShotUtils.saveChangBit(DataDetailsActivity.this, bitmap);
                if (isSave) {
                    showToast("保存成功！");
                } else {
                    showToast("保存失败！");
                }
                titleLayout.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
        }, 500);
    }


    /**
     * 分享图片
     */
    private void shareImage(int flags) {
        titleLayout.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ScreenShotUtils.getScrollViewBitmap(scrollView);
                ShareUtils.shareImage(flags, bitmap);
                titleLayout.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
        }, 500);
    }

}
