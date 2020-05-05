package com.habit.commonlibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habit.commonlibrary.R;

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
