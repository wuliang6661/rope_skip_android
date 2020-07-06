package com.tohabit.skip.ui.train.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tohabit.skip.R;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.NengLiangVO;

import java.util.ArrayList;


/**
 * @date:  2020-02-13 02:14
 * @ClassName: EnergyRewardListAdapter.java
 * @Description:EnergyRewardListAdapter
 * @author: sundongdong
 * @version V1.0
 */
public class EnergyRewardListAdapter extends BaseRvAdapter<NengLiangVO, BaseViewHolder> {

    public EnergyRewardListAdapter(Context context) {
        super(R.layout.layout_fragment_energy_reward_list_item, new ArrayList<NengLiangVO>());
    }

    @Override
    protected void convert(BaseViewHolder helper, NengLiangVO item) {
        helper.setText(R.id.tv_name_layout_fragment_energy_reward_list_item, item.getTitle())
                .setText(R.id.tv_value_layout_fragment_energy_reward_list_item, item.getEnergyValue())
                .setText(R.id.tv_time_layout_fragment_energy_reward_list_item, item.getCreateDate());
    }
}
