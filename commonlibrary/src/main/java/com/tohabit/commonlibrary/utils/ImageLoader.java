package com.tohabit.commonlibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by codeest on 2017/2/24.
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";

    /**
     * 简单加载图片
     *
     * @param context
     * @param iv
     * @param url
     */
    public static void load(Context context, ImageView iv, String url) {
        Glide.with(context)
                .load(url)
                .thumbnail(0.3f)
                .into(iv);

    }

}
