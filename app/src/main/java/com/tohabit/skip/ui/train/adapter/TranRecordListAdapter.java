package com.tohabit.skip.ui.train.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tohabit.skip.R;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.TestBO;

import java.util.ArrayList;


/**
 * @version V1.0
 * @date: 2020-02-11 11:00
 * @ClassName: TranRecordListAdapter.java
 * @Description:训练记录
 * @author: sundongdong
 */
public class TranRecordListAdapter extends BaseRvAdapter<TestBO, BaseViewHolder> {

    public TranRecordListAdapter(Context context) {
        super(R.layout.layout_fragment_train_record_list_item, new ArrayList<TestBO>());
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBO item) {
        helper.addOnClickListener(R.id.tv_look_layout_fragment_train_record_list_item)
                .setText(R.id.tv_time_layout_fragment_train_record_list_item, item.getUpdateDate())
                .setText(R.id.tv_jb_layout_fragment_train_record_list_item, item.getSkipGrade())
                .setText(R.id.tv_pay_time_layout_fragment_train_record_list_item, item.getSkipTime() + "");
    }
}