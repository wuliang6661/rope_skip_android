package com.tohabit.skip.ui.find.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tohabit.skip.R;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.ui.find.bean.FindModel;

import java.util.ArrayList;


/**
 * @date:  2020-02-26 23:33
 * @ClassName: FindListAdapter.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public class FindListAdapter extends BaseRvAdapter<FindModel, BaseViewHolder> {

    public FindListAdapter(Context context) {
        super(R.layout.layout_fragment_find_list_item, new ArrayList<FindModel>());
    }

    @Override
    protected void convert(BaseViewHolder helper, FindModel item) {
        helper.setText(R.id.tv_title_layout_fragment_find_list_item, item.title)
                .setText(R.id.tv_start_time_layout_fragment_find_list_item, item.startTime)
                .setText(R.id.tv_end_time_layout_fragment_find_list_item, item.endTime);

        RequestOptions options = new RequestOptions().error(R.mipmap.ic_default_img).bitmapTransform(new RoundedCorners(10));
        Glide.with(mContext)
                .load(item.img)
                .apply(options)
                .thumbnail(0.3f)
                .into((AppCompatImageView)helper.getView(R.id.iv_img_layout_fragment_find_list_item));
    }
}
