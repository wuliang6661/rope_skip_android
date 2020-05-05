package com.habit.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.habit.commonlibrary.R;
import com.habit.commonlibrary.utils.DensityUtil;

public class CustomAppCompatEditText extends AppCompatEditText implements OnFocusChangeListener,
        TextWatcher {

    private String leftFixedText = "";
    private int leftFixedTextColor = -1;
    private float leftFixedTextSize = -1.0f;
    private TextPaint leftTextPaint = null;

    private Drawable rightDrawable;
    private Drawable leftDrawable;
    private boolean isFocus;
    private int leftScaleWidth = -1;
    private int leftScaleHeight = -1;
    private int rightScaleWidth = -1;
    private int rightScaleHeight = -1;
    private int leftFixTextPaddingleft=-1;
    private final int DEFAULT_LEFT_FIX_TEXT_PADDINGLEFT = 10;
    private final int DEFAULT_LEFT_FIX_TEXT_WITH = 80;

    public CustomAppCompatEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    public CustomAppCompatEditText(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.editTextStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomAppCompatEditText);

        leftDrawable = typedArray.getDrawable(R.styleable.CustomAppCompatEditText_leftDrawable);
        rightDrawable = typedArray.getDrawable(R.styleable.CustomAppCompatEditText_rightDrawable);
        leftFixedText = typedArray.getString(R.styleable.CustomAppCompatEditText_leftFixText);
        leftFixedTextColor = typedArray.getResourceId(R.styleable.CustomAppCompatEditText_leftFixTextColor, -1);
        leftFixedTextSize = typedArray.getDimension(R.styleable.CustomAppCompatEditText_leftFixTextSize, -1.0f);
        leftScaleWidth = typedArray.getDimensionPixelOffset(R.styleable
                .CustomAppCompatEditText_leftDrawableWidth, dp2px(getContext(), 20));
        leftScaleHeight = typedArray.getDimensionPixelOffset(R.styleable
                .CustomAppCompatEditText_leftDrawableHeight, dp2px(getContext(), 20));
        rightScaleWidth = typedArray.getDimensionPixelOffset(R.styleable
                .CustomAppCompatEditText_rightDrawableWidth, dp2px(getContext(), 20));
        rightScaleHeight = typedArray.getDimensionPixelOffset(R.styleable
                .CustomAppCompatEditText_rightDrawableHeight, dp2px(getContext(), 20));

        typedArray.recycle();

        init();
    }

    public CustomAppCompatEditText(Context context) {
        super(context, null);
    }

    private void init() {
        if (rightDrawable != null) {
            setRightDrawable(rightDrawable);
        }

        if (leftDrawable != null) {
            setLeftDrawable(leftDrawable);
        }

        if (!TextUtils.isEmpty(leftFixedText)) {
            setLeftFixedText(leftFixedText);

        }

        setRightDrawableVisibility(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    /**
     * 设置左侧固定的文字，包括字体大小、字体颜色
     *
     * @param text
     */
    public void setLeftFixedText(String text) {
        leftFixedText = text;
//        int left = (int) getPaint().measureText(leftFixedText) + getPaddingLeft();
        int left = DensityUtil.dp2px(getContext(), DEFAULT_LEFT_FIX_TEXT_WITH);
        setPadding(left, getPaddingTop(), getPaddingBottom(), getPaddingRight());
        leftTextPaint = new TextPaint();
        leftTextPaint.set(getPaint());
        if(leftFixTextPaddingleft==-1) {
            leftFixTextPaddingleft = DensityUtil.dp2px(getContext(), DEFAULT_LEFT_FIX_TEXT_PADDINGLEFT);
        }

        if (leftFixedTextSize != -1.0f) {
            leftTextPaint.setTextSize(leftFixedTextSize);
        }
        if (leftFixedTextColor != -1) {
            leftTextPaint.setColor(getResources().getColor(leftFixedTextColor));
        }

        invalidate();
    }

    public void setLeftDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, leftScaleWidth, leftScaleHeight);
        setLeftDrawableVisibility(true);
    }

    public void setRightDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, rightScaleWidth, rightScaleHeight);
        setRightDrawableVisibility(true);
    }

    protected void setRightDrawableVisibility(boolean visibility) {
        Drawable tempDrawable = visibility ? rightDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], tempDrawable,
                getCompoundDrawables()[3]);
    }

    protected void setLeftDrawableVisibility(boolean visibility) {
        Drawable tempDrawable = visibility ? leftDrawable : null;
        setCompoundDrawables(tempDrawable, getCompoundDrawables()[1],
                getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    /**
     * @说明：isInIconWidth, isInIconHeight为true，触摸点在删除图标之内，则视为点击了删除图标 event.getX()
     * 获取相对应自身左上角的X坐标 event.getY() 获取相对应自身左上角的Y坐标 getWidth()
     * 获取控件的宽度 getHeight() 获取控件的高度 getTotalPaddingRight()
     * 获取删除图标左边缘到控件右边缘的距离 getPaddingRight()
     * 获取删除图标右边缘到控件右边缘的距离 isInIconWidth: getWidth() -
     * getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离 getWidth() -
     * getPaddingRight() 计算删除图标右边缘到控件左边缘的距离 isInIconHeight:
     * distance 删除图标顶部边缘到控件顶部边缘的距离 distance + height
     * 删除图标底部边缘到控件顶部边缘的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (null != getCompoundDrawables()[2]) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect clearBounds = getCompoundDrawables()[2].getBounds();
                int height = clearBounds.height();
                int distance = (int) ((getHeight() - height) / 2);
                boolean isInIconWidth = x > (getWidth() - getTotalPaddingRight())
                        && x < (getWidth() - getPaddingRight());
                boolean isInIconHeight = y > distance
                        && y < (distance + height);
                if (isInIconWidth && isInIconHeight) {
                    this.setText("");
                    this.clearFocus();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(leftFixedText) && leftTextPaint != null) {
            Log.e("onDraw","x=="+leftFixTextPaddingleft+" y="+((getMeasuredHeight() - getTextSize())-getPaddingBottom()));
            canvas.drawText(leftFixedText,leftFixTextPaddingleft, (getMeasuredHeight() - getTextSize())-getPaddingBottom(), leftTextPaint);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        this.isFocus = hasFocus;
        if (isFocus) {
            if (getText().length() > 0) {
                setRightDrawableVisibility(true);
            } else {
                setRightDrawableVisibility(false);
            }
        } else {
            setRightDrawableVisibility(false);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (isFocus) {
            if (s.length() > 0) {
                setRightDrawableVisibility(true);
            } else {
                setRightDrawableVisibility(false);
            }
        }
    }

    public int dp2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat
                * paramContext.getResources().getDisplayMetrics().density);
    }

    public int px2dp(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat
                / paramContext.getResources().getDisplayMetrics().density);
    }

}
