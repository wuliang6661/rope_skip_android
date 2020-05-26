package com.habit.star.ui.young.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseActivity;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    LineChart lineChart;

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
        getDataMsg(id);
        setAdapter();
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
        HttpServerImpl.getDataReport(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("1");
        data.add("2");
        data.add("3");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(data) {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item_data_details_left;
                } else {
                    return R.layout.item_data_details_right;
                }
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {

            }

            @Override
            public int getItemViewType(int position) {
                return position % 2;
            }
        };
        taskRecycle.setAdapter(adapter);
    }
}
