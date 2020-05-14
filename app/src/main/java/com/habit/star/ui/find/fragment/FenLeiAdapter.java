package com.habit.star.ui.find.fragment;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;

import com.habit.star.R;
import com.habit.star.pojo.po.FenLeiBO;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

public class FenLeiAdapter extends LGRecycleViewAdapter<FenLeiBO> {

    private List<FenLeiBO> list;

    private int selectPosition = 0;

    public FenLeiAdapter(List<FenLeiBO> dataList) {
        super(dataList);
        this.list = dataList;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fenlei;
    }


    public void setSelectFenLei(int position) {
        selectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }


    @Override
    public void convert(LGViewHolder holder, FenLeiBO fenLeiBO, int position) {
        AppCompatTextView fenLeiText = (AppCompatTextView) holder.getView(R.id.fenlei_text);
        fenLeiText.setText(fenLeiBO.getName());
        if (selectPosition == position) {
            fenLeiText.setTextColor(Color.parseColor("#ff7ec7f5"));
        } else {
            fenLeiText.setTextColor(Color.parseColor("#888888"));
        }
    }
}
