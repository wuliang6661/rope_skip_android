package com.habit.star.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.habit.star.R;
import com.habit.star.base.BaseActivity;
import com.habit.star.widget.TouchImageView;

import java.util.List;

import butterknife.BindView;

/**
 * 查看大图
 */
public class BigPicutreActivity extends BaseActivity {

    @BindView(R.id.image_pager)
    ViewPager imagePager;

    List<String> imageBOS;

    private int selectPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        imageBOS = getIntent().getStringArrayListExtra("imageBos");
        selectPosition = getIntent().getIntExtra("selectPosition", 0);

        setTitleText("查看图片");
        imagePager.setAdapter(new MyPagerAdapter());
        imagePager.setCurrentItem(selectPosition);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_big_picture;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError(int errorCode) {

    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageBOS.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View groupView = LayoutInflater.from(BigPicutreActivity.this).inflate(R.layout.act_big_img, null);
            TouchImageView imageView = groupView.findViewById(R.id.iv_big_image);
            Glide.with(BigPicutreActivity.this).load(imageBOS.get(position)).into(imageView);
            view.addView(groupView);
            return groupView;
        }

    }

}
