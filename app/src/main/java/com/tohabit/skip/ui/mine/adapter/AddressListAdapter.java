package com.tohabit.skip.ui.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tohabit.skip.R;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.AddressBO;

import java.util.ArrayList;

/*
 * 创建日期：2020-01-22 09:06
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：AddressListAdapter.java
 * 类说明：收货地址列表
 */
public class AddressListAdapter extends BaseRvAdapter<AddressBO, BaseViewHolder> {

    public AddressListAdapter(Context context) {
        super(R.layout.layout_fragment_address_list_item, new ArrayList<AddressBO>());
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBO item) {
        helper.addOnClickListener(R.id.ll_select_layout_fragment_address_list_item)
                .addOnClickListener(R.id.ll_bianji_layout_fragment_address_list_item)
                .addOnClickListener(R.id.ll_delete_layout_fragment_address_list_item)
                .addOnClickListener(R.id.address_layout)
                .setText(R.id.tv_title_layout_fragment_address_list_item, item.getName())
                .setText(R.id.tv_tel_layout_fragment_address_list_item, item.getPhone())
                .setText(R.id.tv_address_name_layout_fragment_address_list_item, item.getAddress());
        if (item.getIsDefault() == 1){
//            helper.setBackgroundRes(R.id.iv_select_layout_fragment_address_list_item,R.mipmap.ic_address_pre);
            helper.setVisible(R.id.tv_mr_layout_fragment_address_list_item,true);
            helper.setVisible(R.id.iv_select_layout_fragment_address_list_item,true);
        }else {
//            helper.setBackgroundRes(R.id.iv_select_layout_fragment_address_list_item,R.mipmap.ic_address_nor);
            helper.setVisible(R.id.tv_mr_layout_fragment_address_list_item,false);
            helper.setVisible(R.id.iv_select_layout_fragment_address_list_item,false);
        }
    }

//    public void setItemSelected(int position){
//        for (int i = 0; i < mData.size(); i++) {
//            mData.get(i).isSelected = false;
//        }
//        mData.get(position).isSelected = true;
//        notifyDataSetChanged();
//    }
}
