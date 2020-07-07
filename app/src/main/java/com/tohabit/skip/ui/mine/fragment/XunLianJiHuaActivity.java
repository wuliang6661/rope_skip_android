package com.tohabit.skip.ui.mine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.JiHuaBO;
import com.tohabit.skip.ui.young.fragment.VideoExplainActivity;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XunLianJiHuaActivity extends BaseActivity {

    @BindView(R.id.zhishi_text)
    TextView zhishiText;
    @BindView(R.id.zhishi_view)
    View zhishiView;
    @BindView(R.id.weiwancheng)
    RelativeLayout weiwancheng;
    @BindView(R.id.kecheng_text)
    TextView kechengText;
    @BindView(R.id.kecheng_view)
    View kechengView;
    @BindView(R.id.yiwancheng)
    RelativeLayout yiwancheng;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private int selectMenu = 0;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_xunlian_jihua;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("训练计划");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen.size_list_item_divider_test).colorResId(R.color.transparent).build());
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData(selectMenu);
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


    @OnClick({R.id.weiwancheng, R.id.yiwancheng})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.weiwancheng:
                setTitleStyle(0);
                selectMenu = 0;
                getData(0);
                break;
            case R.id.yiwancheng:
                setTitleStyle(1);
                selectMenu = 1;
                getData(1);
                break;
        }
    }

    private void setTitleStyle(int i) {
        if (i == 0) {
            kechengText.setTextColor(Color.parseColor("#AAAAAA"));
            kechengView.setVisibility(View.GONE);
            zhishiText.setTextColor(Color.parseColor("#7EC7F5"));
            zhishiView.setVisibility(View.VISIBLE);
        } else {
            zhishiText.setTextColor(Color.parseColor("#AAAAAA"));
            zhishiView.setVisibility(View.GONE);
            kechengText.setTextColor(Color.parseColor("#7EC7F5"));
            kechengView.setVisibility(View.VISIBLE);
        }
    }

    private void getData(int type) {
        showProgress(null);
        HttpServerImpl.getTrainList(type).subscribe(new HttpResultSubscriber<List<JiHuaBO>>() {
            @Override
            public void onSuccess(List<JiHuaBO> s) {
                stopProgress();
                setAdapter(s, type);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void setAdapter(List<JiHuaBO> jiHuaBOS, int type) {
        LGRecycleViewAdapter<JiHuaBO> adapter = new LGRecycleViewAdapter<JiHuaBO>(jiHuaBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.layout_fragment_improve_plan_list_item;
            }

            @Override
            public void convert(LGViewHolder holder, JiHuaBO jiHuaBO, int position) {
                holder.setText(R.id.title, jiHuaBO.getVideoTitle());
                holder.setText(R.id.tv_content_layout_fragment_improve_plan_list_item, jiHuaBO.getVideoTitle());
                holder.setText(R.id.tv_train_time_layout_fragment_improve_plan_list_item, jiHuaBO.getTrainLength());
//                holder.getView(R.id.tv_state_name_layout_fragment_improve_plan_list_item).setVisibility(View.GONE);
                if (type == 0) {  //未完成
                    holder.setTextColor(R.id.title, "#333333");
                    holder.setTextColor(R.id.item_num, "#333333");
                    setText(holder, "#7EC7F5");
                    holder.getView(R.id.common_date).setVisibility(View.GONE);
                    holder.setText(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "去完成");
                    holder.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "#F97B61");
                } else {    //已完成
                    holder.setTextColor(R.id.title, "#AAAAAA");
                    holder.setTextColor(R.id.item_num, "#AAAAAA");
                    holder.getView(R.id.common_date).setVisibility(View.VISIBLE);
                    holder.setText(R.id.common_date, jiHuaBO.getCompleteDate());
                    setText(holder, "#AAAAAA");
                    holder.setText(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "已完成");
                    holder.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "#AAAAAA");
                }
//                LinearLayout layout = (LinearLayout) holder.getView(R.id.ll_content_layout_fragment_improve_plan_list_item);
//                layout.setBackgroundResource(R.mipmap.jihua_bg);
            }


            private void setText(LGViewHolder holder, String color) {
                holder.setTextColor(R.id.neirong, color);
                holder.setTextColor(R.id.tv_content_layout_fragment_improve_plan_list_item, color);
                holder.setTextColor(R.id.shichang, color);
                holder.setTextColor(R.id.tv_train_time_layout_fragment_improve_plan_list_item, color);
                holder.setTextColor(R.id.tv_train_time_unit_layout_fragment_improve_plan_list_item, color);
                holder.setTextColor(R.id.item_jihua, color);
                holder.setTextColor(R.id.tv_train_plan_layout_fragment_improve_plan_list_item, color);
                holder.setTextColor(R.id.tv_train_plan_unit_layout_fragment_improve_plan_list_item, color);
            }

        };
//        adapter.setOnItemClickListener(R.id.ll_content_layout_fragment_improve_plan_list_item, new LGRecycleViewAdapter.ItemClickListener() {
//            @Override
//            public void onItemClicked(View view, int position) {
//                if (type == 0) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", jiHuaBOS.get(position).getId() + "");
//                    bundle.putString("trainLength", Integer.parseInt(jiHuaBOS.get(position).getTrainLength()) * 60 + "");
//                    Intent intent = new Intent();
//                    intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.ROPE_SKIP_RESULTS);
//                    intent.putExtras(bundle);
//                    intent.setClass(XunLianJiHuaActivity.this, TainMainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        adapter.setOnItemClickListener(R.id.tv_look_layout_fragment_improve_plan_list_item, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", jiHuaBOS.get(position).getImprovePlanId());
                bundle.putInt("xunlianId", jiHuaBOS.get(position).getId());
                gotoActivity(VideoExplainActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(adapter);
    }


}
