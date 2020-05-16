package com.habit.star.ui.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.habit.commonlibrary.utils.ImageLoader;
import com.habit.star.R;
import com.habit.star.app.Constants;

import java.util.ArrayList;

/**
 *  * 创建日期：
 *  * @author sundongdong6
 *  * @version 1.0 
 *  * @since  
 *  * 文件名称：  ImageGridAdapter.class
 *  * 类说明： 图片列表适配器
 *  
 */
public class ImageGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ArrayList<String> imageCy = null;
    private int imageNumber = 99;

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public ImageGridAdapter(ArrayList<String> mDatas, ArrayList<String> imageCy, Context context) {
        super(R.layout.layout_image_grid_fragment_common, mDatas);
        if (this.mData != null) {
            this.mData.add("");
        }
        this.mContext = context;
        this.imageCy = imageCy;
    }

    public void setImagesCy(ArrayList<String> imCy) {
        imageCy = imCy;
    }

    public ArrayList<String> getImagesCy() {
        return imageCy;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.addOnClickListener(R.id.frame_layout_image_grid_fragment_task_order_feedback)
//                .addOnLongClickListener(R.id.frame_layout_image_grid_fragment_task_order_feedback);
        if (imageCy != null && imageCy.size() < imageNumber) {
            if (helper.getLayoutPosition() == (mData.size() - 1)) {
                helper.setImageResource(R.id.iv_layout_image_grid_fragment_task_order_feedback, R.mipmap.ic_menu_add_picture);
            } else {
                ImageLoader.load(mContext, helper.getView(R.id.iv_layout_image_grid_fragment_task_order_feedback),
                        item);
            }
        } else {
            ImageLoader.load(mContext, helper.getView(R.id.iv_layout_image_grid_fragment_task_order_feedback),
                    item);
        }
    }
}
