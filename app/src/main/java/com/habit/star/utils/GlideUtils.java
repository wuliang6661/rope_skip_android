package com.habit.star.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.habit.star.R;

public class GlideUtils {

    private String TAG = GlideUtils.class.getSimpleName();
    RequestOptions options;

    private GlideUtils(){
        options = new RequestOptions();
        options.skipMemoryCache(false);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.priority(Priority.HIGH);
        options.error(R.mipmap.ic_default_img);
        //设置占位符,默认
        options.placeholder(R.mipmap.ic_default_img);
        //设置错误符,默认
        options.error(R.mipmap.ic_default_img);
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideUtils INSTANCE = new GlideUtils();
    }

    public static GlideUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    public void loadImg(Context context, String url, ImageView imageView) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(url).into(imageView);
        } else {
            LogUtil.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void loadDefaultErrorImg(Context context, String url, ImageView imageView) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(url).apply(options).into(imageView);
        } else {
            LogUtil.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 加載圓形图片
     * @param view
     * @param url
     */
    public static void loadRoundImage(ImageView view, String url) {

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
                //圆形
                .circleCrop();
        Glide.with(view.getContext())
                .load(url).apply(options).into(view);
    }

    public void loadAsGif(Context context, String url, ImageView imageView) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).asGif().load(url).into(imageView);
        } else {
            LogUtil.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void loadImg(Context context, String url, ImageView imageView, RequestOptions options) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(url).apply(options).into(imageView);
        } else {
            LogUtil.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void loadImg(Context context, int res, ImageView imageView, RequestOptions options) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(res).apply(options).into(imageView);
        } else {
            LogUtil.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context).load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        int width = resource.getIntrinsicWidth();
                        int height = resource.getIntrinsicHeight();
                        Log.i("TAG", "图片的宽=" + width + " 图片的高 = " + height);
                        //获取imageView的宽
                        int imageViewWidth = imageView.getWidth();
                        //计算缩放比例
                        float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);
                        //计算图片等比例放大后的高
                        int imageViewHeight = (int) (height * sy);
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.height = imageViewHeight;
                        params.width = imageViewWidth;
                        imageView.setLayoutParams(params);
                        return false;
                    }
                }).into(imageView);
    }

    //以图片宽度为基准
    public void loadImgWidth(Context context,String url, final ImageView imageView, final int width){
        Glide.with(context)
                .asBitmap()
                .apply(options)
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth=resource.getWidth();
                        int imageHeight=resource.getHeight();
                        int height = width * imageHeight / imageWidth;
                        ViewGroup.LayoutParams params=imageView.getLayoutParams();
                        params.height=height;
                        params.width=width;
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    //以图片高度为基准
    public void loadImgHeight(Context context,String url, final ImageView imageView, final int height){
        Glide.with(context)
                .asBitmap()
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth=resource.getWidth();
                        int imageHeight=resource.getHeight();
                        int width = height * imageHeight / imageWidth;
                        ViewGroup.LayoutParams params=imageView.getLayoutParams();
                        params.height=height;
                        params.width=width;
                        imageView.setImageBitmap(resource);
                    }
                });
    }


    public void loadImgRound(Context context,String url, final ImageView imageView){
        //自带圆角方法，显示圆形
        options.circleCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
