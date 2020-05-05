package com.habit.star.ui.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.habit.commonlibrary.utils.ImageLoader;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.star.R;
import com.habit.star.common.adapter.BaseRvAdapter;
import com.habit.star.ui.mine.bean.FamilyMemberModel;

import java.util.ArrayList;

/*
 * 创建日期：2020-01-22 09:06
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：FamilyMemberListAdapter.java
 * 类说明：家庭成员列表
 */
public class FamilyMemberListAdapter extends BaseRvAdapter<FamilyMemberModel, BaseViewHolder> {

    public FamilyMemberListAdapter(Context context) {
        super(R.layout.layout_fragment_family_member_list_item, new ArrayList<FamilyMemberModel>());
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMemberModel item) {
        helper.addOnClickListener(R.id.ll_layout_fragment_family_member_list_item)
                .setText(R.id.tv_title_layout_fragment_family_member_list_item, item.name);
        ImageLoader.load(mContext, helper.getView(R.id.iv_img_fragment_family_member_list_item), item.img);

    }
}
