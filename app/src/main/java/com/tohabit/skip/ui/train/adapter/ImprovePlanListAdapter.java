package com.tohabit.skip.ui.train.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tohabit.skip.R;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.TestDetailsBO;

import java.util.ArrayList;


/**
 * @version V1.0
 * @date: 2020-02-11 22:40
 * @ClassName: ImprovePlanListAdapter.java
 * @Description:改良方案
 * @author: sundongdong
 */
public class ImprovePlanListAdapter extends BaseRvAdapter<TestDetailsBO.PlanListBean, BaseViewHolder> {

    public ImprovePlanListAdapter(Context context) {
        super(R.layout.layout_fragment_improve_plan_list_item, new ArrayList<TestDetailsBO.PlanListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, TestDetailsBO.PlanListBean item) {
        helper.addOnClickListener(R.id.tv_look_layout_fragment_improve_plan_list_item)
                .addOnClickListener(R.id.tv_state_name_layout_fragment_improve_plan_list_item)
                .setText(R.id.tv_content_layout_fragment_improve_plan_list_item, item.getVideoTitle())
                .setText(R.id.tv_train_time_layout_fragment_improve_plan_list_item, item.getTrainLength() + "");
        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "添加计划");
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, Color.parseColor("#F5B68F"));
                break;
            case 1:
                helper.setText(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "进行中");
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, Color.parseColor("#7EC7F5"));
                break;
            case 2:
                helper.setText(R.id.tv_state_name_layout_fragment_improve_plan_list_item, "已完成");
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, Color.parseColor("#AAAAAA"));
                break;
            case 3:
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, mContext.getResources().getColor(R.color.color_AAAAAA));
                break;
        }
    }
}
