package com.tohabit.skip.ui.train.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tohabit.skip.R;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.TestBO;
import com.tohabit.skip.utils.StringUtils;

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
                .setText(R.id.tv_time_layout_fragment_train_record_list_item, item.getSkipDate())
                .setText(R.id.tv_jb_layout_fragment_train_record_list_item, item.getSkipNum() + "");
        String munite = getMunite(item.getSkipTime());
        if (StringUtils.isEmpty(munite)) {
            helper.getView(R.id.fen_text).setVisibility(View.GONE);
            helper.getView(R.id.fen_unit).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.fen_text).setVisibility(View.VISIBLE);
            helper.getView(R.id.fen_unit).setVisibility(View.VISIBLE);
            helper.setText(R.id.fen_text, munite);
        }
        String seconds = getSecond(item.getSkipTime());
        if (StringUtils.isEmpty(seconds)) {
            helper.getView(R.id.tv_pay_time_layout_fragment_train_record_list_item).setVisibility(View.GONE);
            helper.getView(R.id.tv_pay_time_table_layout_fragment_train_record_list_item).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_pay_time_layout_fragment_train_record_list_item).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_pay_time_table_layout_fragment_train_record_list_item).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_pay_time_layout_fragment_train_record_list_item, seconds);
        }
    }


    private String getMunite(int seconds) {
        if (seconds < 60) {
            return "";
        } else if (seconds < 3600) {
            int munite = seconds / 60;
            return numToString(munite);
        } else {
            int munite = seconds % 3600 / 60;
            return numToString(munite);
        }
    }


    private String getSecond(int seconds) {
        if (seconds < 60) {
            return numToString(seconds).equals("00") ? "" : numToString(seconds);
        } else if (seconds < 3600) {
            int second = seconds % 60;
            return numToString(second).equals("00") ? "" : numToString(second);
        } else {
            int second = seconds % 3600 % 60;
            return numToString(second).equals("00") ? "" : numToString(second);
        }
    }

    public static String numToString(int count) {
        return count < 10 ? "0" + count : count + "";
    }
}
