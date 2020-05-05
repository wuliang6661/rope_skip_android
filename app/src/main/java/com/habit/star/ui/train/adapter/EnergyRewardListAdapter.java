package com.habit.star.ui.train.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.habit.star.R;
import com.habit.star.common.adapter.BaseRvAdapter;
import com.habit.star.ui.train.bean.EnergyRewardModel;
import com.habit.star.ui.train.bean.ImprovePlanModel;

import java.util.ArrayList;


/**
 * @date:  2020-02-13 02:14
 * @ClassName: EnergyRewardListAdapter.java
 * @Description:EnergyRewardListAdapter
 * @author: sundongdong
 * @version V1.0
 */
public class EnergyRewardListAdapter extends BaseRvAdapter<EnergyRewardModel, BaseViewHolder> {

    public EnergyRewardListAdapter(Context context) {
        super(R.layout.layout_fragment_energy_reward_list_item, new ArrayList<EnergyRewardModel>());
    }

    @Override
    protected void convert(BaseViewHolder helper, EnergyRewardModel item) {
        helper.setText(R.id.tv_name_layout_fragment_energy_reward_list_item, item.title)
                .setText(R.id.tv_value_layout_fragment_energy_reward_list_item, item.value)
                .setText(R.id.tv_time_layout_fragment_energy_reward_list_item, item.time);
    }
}
