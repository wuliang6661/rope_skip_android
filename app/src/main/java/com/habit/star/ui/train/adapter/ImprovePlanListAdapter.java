package com.habit.star.ui.train.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.habit.star.R;
import com.habit.star.common.adapter.BaseRvAdapter;
import com.habit.star.pojo.po.TestDetailsBO;
import com.habit.star.ui.train.bean.ImprovePlanModel;
import com.habit.star.ui.train.bean.TranRecordModel;

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
                .setText(R.id.tv_state_name_layout_fragment_improve_plan_list_item, item.stateName)
                .setText(R.id.tv_content_layout_fragment_improve_plan_list_item, item.content)
                .setText(R.id.tv_train_time_unit_layout_fragment_improve_plan_list_item, item.trainTimeUnit)
                .setText(R.id.tv_train_time_layout_fragment_improve_plan_list_item, item.trainTime)
                .setText(R.id.tv_train_plan_layout_fragment_improve_plan_list_item, item.planTime)
                .setText(R.id.tv_train_plan_unit_layout_fragment_improve_plan_list_item, item.planTimeUnit)
                .setText(R.id.tv_energy_layout_fragment_improve_plan_list_item, item.energy);
        switch (item.getStatus()) {
            case 0:
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, mContext.getResources().getColor(R.color.color_F97B61));
                break;
            case 1:
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, mContext.getResources().getColor(R.color.color_F5B68F));
                break;
            case 2:
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, mContext.getResources().getColor(R.color.color_D1EDFF));
                break;
            case 3:
                helper.setTextColor(R.id.tv_state_name_layout_fragment_improve_plan_list_item, mContext.getResources().getColor(R.color.color_AAAAAA));
                break;
        }
    }
}
