package com.habit.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habit.commonlibrary.R;
import com.habit.commonlibrary.utils.DensityUtil;

/**
 * Created by sundongdong on 2017/2/22.
 */

public class ToolbarWithBackRightProgress extends Toolbar {
    Toolbar rootTB;
    ImageButton backIB;
    LinearLayout backIBLl;
    TextView leftTV;
    AppCompatImageView leftIcon;
    TextView titleTV;
    Button rightBtn;
    ImageButton rightIb;
    CircleProgressBar progressBar;
    FrameLayout rightFrame;
    TextView bottomDividerTv;

    String titleStr = "";
    String leftStr = "";
    int titleColor = -1;
    int titleTextSize = -1;
    int titleBackground = -1;
    int leftTVColor = -1;
    int rightBtnTextColor = -1;
    int leftTVTextSize = -1;
    int bottomDividerColor = -1;
    int bottomDividerVisibility = -1;
    int rightBtnTextSize = -1;
    int toolbarBackground = -1;
    int backIBImageSource = -1;
    int leftIBImageSource = -1;
    int rightIBImageSource = -1;
    int rightIBPadding = -1;
    int rightIBMarginRight = -1;
    String backBtnStr = "";
    int backBtnTextSize = -1;
    String rightBtnText = "";
    int cpbProgressColor = -1;
    int cpbVisibility = -1;
    int rightFrameVisibility = -1;
    int leftTVVisibility = -1;
    boolean isRightBtnHide = false;

    public ToolbarWithBackRightProgress(Context context) {
        super(context);
        init(context);
    }

    public ToolbarWithBackRightProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        titleColor = getResources().getColor(R.color.white);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarWithBackRightProgress);
        titleStr = a.getString(R.styleable.ToolbarWithBackRightProgress_tl_brp_title);
        leftStr = a.getString(R.styleable.ToolbarWithBackRightProgress_tl_brp_left_text);
        titleColor = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_title_text_color, -1);
        bottomDividerColor = a.getColor(R.styleable.ToolbarWithBackRightProgress_tl_brp_bottom_divider_color, -1);
        bottomDividerVisibility = a.getInt(R.styleable.ToolbarWithBackRightProgress_tl_brp_bottom_divider_visibility, -1);
        titleTextSize = a.getDimensionPixelSize(R.styleable.ToolbarWithBackRightProgress_tl_brp_title_text_size, -1);
        titleBackground = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_title_background, -1);
        leftTVColor = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_left_text_color, -1);
        leftTVTextSize = (int) a.getDimension(R.styleable.ToolbarWithBackRightProgress_tl_brp_left_text_size, -1);
        rightBtnTextSize = (int) a.getDimension(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_btn_text_size, -1);
        toolbarBackground = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_background, -1);
        backIBImageSource = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_back_ib_drawable, -1);
        leftIBImageSource = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_left_ib_drawable, -1);
        rightIBImageSource = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_ib_drawable, -1);
        rightIBPadding = a.getDimensionPixelSize(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_ib_padding, -1);
        rightIBMarginRight = a.getDimensionPixelSize(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_ib_margin_right, -1);
        backBtnStr = a.getString(R.styleable.ToolbarWithBackRightProgress_tl_brp_back_btn_text);
        backBtnTextSize = a.getDimensionPixelSize(R.styleable.ToolbarWithBackRightProgress_tl_brp_back_btn_text_size, -1);
        rightBtnText = a.getString(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_btn_text);
        rightBtnTextColor = a.getColor(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_text_color, -1);
        cpbProgressColor = a.getResourceId(R.styleable.ToolbarWithBackRightProgress_tl_brp_progress_color, -1);
        cpbVisibility = a.getInt(R.styleable.ToolbarWithBackRightProgress_tl_brp_progress_visibility, -1);
        leftTVVisibility = a.getInt(R.styleable.ToolbarWithBackRightProgress_tl_brp_left_tv_visibility, -1);
        rightFrameVisibility = a.getInt(R.styleable.ToolbarWithBackRightProgress_tl_brp_right_frame_visibility, -1);
        a.recycle();

        init(context);
    }

    public ToolbarWithBackRightProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_toolbar_back_righttv, this);
        rootTB = (Toolbar) findViewById(R.id.toolbar_layout_toolbar_back_righttv);
        rightFrame = (FrameLayout) findViewById(R.id.frame_right_layout_toolbar_back_righttv);
        backIB = (ImageButton) findViewById(R.id.ib_back_layout_toolbar_back_righttv);
        backIBLl = (LinearLayout) findViewById(R.id.ll_back_layout_toolbar_back_righttv);
        leftTV = (TextView) findViewById(R.id.tv_left_layout_toolbar_back_righttv);
        leftIcon = (AppCompatImageView) findViewById(R.id.ib_left_icon_layout_toolbar_back_righttv);
        titleTV = (TextView) findViewById(R.id.toolbar_title_layout_toolbar_back_righttv);
        rightBtn = (Button) findViewById(R.id.btn_right_layout_toolbar_back_righttv);
        rightIb = (ImageButton) findViewById(R.id.ib_right_layout_toolbar_back_righttv);
        progressBar = (CircleProgressBar) findViewById(R.id.cpb_progress_bar_layout_toolbar_back_righttv);
        bottomDividerTv = (TextView) findViewById(R.id.tv_bottom_divider_layout_toolbar_back_righttv);

        if (titleColor != -1) {
            titleTV.setTextColor(getResources().getColor(titleColor));
        }

        if (bottomDividerColor != -1) {
            bottomDividerTv.setBackgroundColor(bottomDividerColor);
        }

        if (titleTextSize != -1) {
            titleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        }

        if (rightIBPadding != -1) {
            int padding = DensityUtil.dp2px(getContext(), rightIBPadding);
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            params.setMargins(rightIBPadding, rightIBPadding, rightIBPadding, rightIBPadding);
            rightIb.setPadding(rightIBPadding, rightIBPadding, rightIBPadding, rightIBPadding);
//            rightIb.setLayoutParams(params);
        }
        if (rightIBMarginRight != -1) {
            int padding = DensityUtil.dp2px(getContext(), rightIBMarginRight);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, rightIBMarginRight, 0);
            rightIb.setLayoutParams(params);
        }

        if (titleBackground != -1) {
            titleTV.setBackgroundResource(titleBackground);
        }

        if (!TextUtils.isEmpty(titleStr)) {
            titleTV.setText(titleStr);
        }

        if (!TextUtils.isEmpty(leftStr)) {
            leftTV.setText(leftStr);
        }

        if (leftTVColor != -1) {
            leftTV.setTextColor(getResources().getColor(leftTVColor));
        }

        if (leftTVTextSize != -1) {
            leftTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTVTextSize);
        }

        if (rightBtnTextSize != -1) {
            rightBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightBtnTextSize);
        }

        if (toolbarBackground != -1) {
            rootTB.setBackgroundResource(toolbarBackground);
        }

        if (backIBImageSource != -1) {
            backIB.setImageResource(backIBImageSource);
        } else {
            backIB.setVisibility(View.GONE);
            backIBLl.setVisibility(View.GONE);
        }
        if (leftIBImageSource != -1) {
            leftIcon.setImageResource(leftIBImageSource);
            leftIcon.setVisibility(VISIBLE);
        } else {
            leftIcon.setVisibility(View.GONE);
        }

        if (rightIBImageSource != -1) {
            rightIb.setImageResource(rightIBImageSource);
            rightIb.setVisibility(View.VISIBLE);
        } else {
            rightIb.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(rightBtnText)) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText(rightBtnText);
        } else {
            rightBtn.setVisibility(View.GONE);
        }


        if (rightBtnTextColor != -1) {
            rightBtn.setTextColor(rightBtnTextColor);
        }

        if (bottomDividerVisibility != -1) {
            if (bottomDividerVisibility == 1) {
                bottomDividerTv.setVisibility(View.VISIBLE);
            } else if (bottomDividerVisibility == 2) {
                bottomDividerTv.setVisibility(View.INVISIBLE);
            } else if (bottomDividerVisibility == 3) {
                bottomDividerTv.setVisibility(View.GONE);
            }
        }

        if (cpbVisibility != -1) {
            if (cpbVisibility == 1) {
                progressBar.setVisibility(View.VISIBLE);
                rightBtn.setVisibility(View.INVISIBLE);
            } else if (cpbVisibility == 2) {
                progressBar.setVisibility(View.INVISIBLE);
                rightBtn.setVisibility(View.VISIBLE);
            } else if (cpbVisibility == 3) {
                progressBar.setVisibility(View.GONE);
                rightBtn.setVisibility(View.VISIBLE);
            }
        }

        if (rightFrameVisibility != -1) {
            if (rightFrameVisibility == 1) {
                rightFrame.setVisibility(View.VISIBLE);
            } else if (rightFrameVisibility == 2) {
                rightFrame.setVisibility(View.INVISIBLE);
            } else if (rightFrameVisibility == 3) {
                rightFrame.setVisibility(View.GONE);
            }
        }

        if (leftTVVisibility != -1) {
            if (leftTVVisibility == 1) {
                leftTV.setVisibility(View.VISIBLE);
            } else if (leftTVVisibility == 2) {
                leftTV.setVisibility(View.INVISIBLE);
            } else if (leftTVVisibility == 3) {
                leftTV.setVisibility(View.GONE);
            }
        }

        if (cpbProgressColor != -1) {
            progressBar.setColorSchemeResources(cpbProgressColor);
        }
    }

    public Toolbar getRootTB() {
        return rootTB;
    }

    public ImageButton getBackIB() {
        return backIB;
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public TextView getLeftTV() {
        return leftTV;
    }

    public Button getRightBtn() {
        return rightBtn;
    }

    public CircleProgressBar getProgressBar() {
        return progressBar;
    }

    public void showRightBtn() {
        isRightBtnHide = false;
        rightBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void hideRightBtn() {
        isRightBtnHide = true;
        rightBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void showLeftIB() {
        backIB.setVisibility(View.VISIBLE);
        backIBLl.setVisibility(View.VISIBLE);
    }

    public void hideLeftIB() {
        backIB.setVisibility(View.INVISIBLE);
        backIBLl.setVisibility(View.INVISIBLE);
    }

    public void showProgress() {
        rightBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        if (!isRightBtnHide) {
            rightBtn.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    public ToolbarWithBackRightProgress setTitle(String title) {
        titleTV.setText(title);
        return this;
    }

    public void setleftTextColor(int color) {
        leftTV.setTextColor(getResources().getColor(color));
    }

    public ToolbarWithBackRightProgress setBackIBImageResource(int resId) {
        backIB.setBackgroundResource(resId);
        return this;
    }

    public void setRightBtnBackgroudResouce(int rightIBImage) {
        rightIb.setImageResource(rightIBImage);
    }

    public ToolbarWithBackRightProgress setRightBtnText(String text) {
        rightBtn.setText(text);
        return this;
    }

    public ToolbarWithBackRightProgress setLeftText(String text) {
        leftTV.setText(text);
        return this;
    }

    public ToolbarWithBackRightProgress setRightBtnClick(OnClickListener listener) {
        rightBtn.setOnClickListener(listener);
        return this;
    }

    public ToolbarWithBackRightProgress setRightImageBtnClick(OnClickListener listener) {
        rightIb.setOnClickListener(listener);
        return this;
    }

    public void setRightFrameVisibility(int visibility) {
        rightFrame.setVisibility(visibility);
    }

    public ToolbarWithBackRightProgress setProgressColor(int colorResId) {
        progressBar.setColorSchemeResources(colorResId);
        return this;
    }

    public ToolbarWithBackRightProgress setBackIBClick(OnClickListener listener) {
        backIB.setOnClickListener(listener);
        leftTV.setOnClickListener(listener);
        leftIcon.setOnClickListener(listener);
        backIBLl.setOnClickListener(listener);
        return this;
    }

}
