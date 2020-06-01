package com.tohabit.skip.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.tohabit.skip.R;

import java.math.BigDecimal;

/**
 * Created by daimajia on 14-4-30.
 *
 * @modifid by dongdong on 2016-04-24
 * @add{增加了加载条平滑加载的逻辑
 */
public class NumberProgressBar extends View {

    private float mMaxProgress = 100f;

    /**
     * Current progress, can not exceed the max progress.
     */
    private float mCurrentProgress = 0f;

    private float mLastProgress = 0f;

    private float progressOffset = 0f;

    /**
     * The progress area bar color.
     */
    private int mReachedBarColor;

    /**
     * The bar unreached area color.
     */
    private int mUnreachedBarColor;

    /**
     * The progress text color.
     */
    private int mTextColor;

    /**
     * The progress text size.
     */
    private float mTextSize;

    /**
     * The height of the reached area.
     */
    private float mReachedBarHeight;

    /**
     * The height of the unreached area.
     */
    private float mUnreachedBarHeight;

    /**
     * The suffix of the number.
     */
    private String mSuffix = "%";

    /**
     * The prefix.
     */
    private String mPrefix = "";

    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66, 145, 241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;

    /**
     * For save and restore instance of progressbar.
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";

    private static final int PROGRESS_TEXT_VISIBLE = 0;

    /**
     * The width of the text that to be drawn.
     */
    private float mDrawTextWidth;

    /**
     * The drawn text start.
     */
    private float mDrawTextStart;

    /**
     * The drawn text end.
     */
    private float mDrawTextEnd;

    /**
     * The text that to be drawn in onDraw().
     */
    private String mCurrentDrawText;

    /**
     * The Paint of the reached area.
     */
    private Paint mReachedBarPaint;
    /**
     * The Paint of the unreached area.
     */
    private Paint mUnreachedBarPaint;
    /**
     * The Paint of the progress text.
     */
    private Paint mTextPaint;

    /**
     * Unreached bar area to draw rect.
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * Reached bar area rect.
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    private RectF resetRectF = new RectF(0, 0, 0, 0);

    /**
     * The progress text offset.
     */
    private float mOffset = 0;

    /**
     * Determine if need to draw unreached area.
     */
    private boolean mDrawUnreachedBar = true;

    private boolean mDrawReachedBar = true;

    private boolean mIfDrawText = true;

    private OnLastProgressCompleteListener mLastProgressCompleteListener = null;

    /**
     * Listener
     */
    private OnProgressBarListener mListener;

    private boolean decelerateMode = true;
    private boolean accelerateMode = false;
    private boolean isFinishedLoadOnePage = false;
    private boolean isFinishLoading = false;
    private boolean isStart = true;
    private int timer = 0;
    private long delayTime = 0;
    private final float accelerateIncrementValue = 2.0f;
    private final float decelerateIncrementValue = 0.2f;
    private float currentAlpha = 1.0f;

    public enum ProgressTextVisibility {
        Visible, Invisible
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private Handler canvasHandler;
    private int frameRate = 5;
    private Animation alphaAnimation = null;

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.numberProgressBarStyle);
    }

    public NumberProgressBar(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_reached_bar_height = dp2px(1.5f);
        default_unreached_bar_height = dp2px(1.0f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(3.0f);

        // load styled attributes.
        final TypedArray attributes = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.NumberProgressBar,
                        defStyleAttr, 0);

        mReachedBarColor = attributes.getColor(
                R.styleable.NumberProgressBar_progress_reached_color,
                default_reached_color);
        mUnreachedBarColor = attributes.getColor(
                R.styleable.NumberProgressBar_progress_unreached_color,
                default_unreached_color);
        mTextColor = attributes.getColor(
                R.styleable.NumberProgressBar_progress_text_color,
                default_text_color);
        mTextSize = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_text_size,
                default_text_size);

        mReachedBarHeight = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_reached_bar_height,
                default_reached_bar_height);
        mUnreachedBarHeight = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_unreached_bar_height,
                default_unreached_bar_height);
        mOffset = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_text_offset,
                default_progress_text_offset);

        int textVisible = attributes.getInt(
                R.styleable.NumberProgressBar_progress_text_visibility,
                PROGRESS_TEXT_VISIBLE);
        if (textVisible != PROGRESS_TEXT_VISIBLE) {
            mIfDrawText = false;
        }

        setProgress(attributes.getInt(
                R.styleable.NumberProgressBar_progress_current, 0));
        setMax(attributes.getInt(R.styleable.NumberProgressBar_progress_max,
                100));

        canvasHandler = new Handler();

        attributes.recycle();
        initializePainters();

        ViewCompat.setLayerType(this, LAYER_TYPE_HARDWARE);

        alphaAnimation = AnimationUtils.loadAnimation(context,
                R.anim.abc_fade_out);
        alphaAnimation.setInterpolator(new DecelerateInterpolator(2));
        alphaAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                clearAnimation();
                setVisibility(View.INVISIBLE);
                resetProgressBar();
            }
        });
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) mTextSize,
                Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true),
                measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight()
                : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth()
                    : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.draw(canvas);

        // 当是否加载完的标志位假时才执行以下绘制的操作。否则不执行，主要用于标志一次加载是否完成（这里的一次加载是指加载条是否慢了“满了”了）
        if (!isFinishLoading) {
            // Log.i("NumberProgressBar", "current_progress==" +
            // mCurrentProgress);
            // Log.i("NumberProgressBar", "mlastProgress==" + mLastProgress);
            // Log.i("NumberProgressBar", "accelerate_mode==" + accelerateMode);

            if (mDrawReachedBar && mLastProgress < mCurrentProgress) {
                //此处使用BigDecimal是避免使用float型进行累加造成的精度误差
                BigDecimal b1 = new BigDecimal(Float.toString(mLastProgress));
                BigDecimal b2 = null;
                // 此处设置加速模式和减速模式情况下每次刷新view时加载条增加的差值（伪智能化）；
                if (decelerateMode) {
                    b2 = new BigDecimal(
                            Float.toString(decelerateIncrementValue));
                } else if (accelerateMode) {
                    b2 = new BigDecimal(
                            Float.toString(accelerateIncrementValue));
                }

                if (b2 != null) {
                    mLastProgress = b1.add(b2).floatValue();
                    calculateDrawRectF(mLastProgress);
                }

                // 此处设置在快要加载完的时候加载条透明度逐渐减小，给人以柔和之感。
                if (mLastProgress > 90.0f && mCurrentProgress == 100.0f
                        && currentAlpha > 0.1f) {
                    currentAlpha = currentAlpha - 0.05f;
                    setAlpha(currentAlpha);
                }

                // 只有当渐变的进度大于等于当前的进度时，判断如果渐变的进度等于最大进度时，表明加载条加载完了，然后执行回调。
                if (mLastProgress >= mCurrentProgress) {
                    progressOffset = 0f;
                    isFinishedLoadOnePage = false;
                    if (mLastProgress >= getMax()) {
                        setAlpha(0f);
                        mLastProgressCompleteListener.onLastProgressComplete();
                        return;
                    }
                }
            }

            canvas.drawRect(mReachedRectF, mReachedBarPaint);
            invalidate();
        }
    }

    // @Override
    // protected void onDraw(Canvas canvas) {
    // if (mIfDrawText) {
    // calculateDrawRectF();
    // } else {
    // calculateDrawRectFWithoutProgressText();
    // }

    // Log.i("NumberProgressBar", "current_progress==" + mCurrentProgress);
    // Log.i("NumberProgressBar", "mlastProgress==" + mLastProgress);
    // Log.i("NumberProgressBar", "accelerate_mode==" + accelerateMode);
    // if (mDrawReachedBar && mLastProgress < mCurrentProgress) {
    // mLastProgress += 1;
    // calculateDrawRectF(mLastProgress);
    // if (mLastProgress == mCurrentProgress) {
    // timer = 0;
    // progressOffset = 0;
    // isFinishedLoadOnePage = false;
    // if (mLastProgress >= getMax()) {
    // mCurrentProgress = 0;
    // mLastProgress = 0;
    // accelerateMode = false;
    // decelerateMode = true;
    // // Log.i("NumberProgressBar", "isFinishedLoadOnePage=="
    // // + isFinishedLoadOnePage);
    // mLastProgressCompleteListener.onLastProgressComplete();
    // return;
    // }
    // }
    //
    // // 只刷新扫描区域
    //
    // if (decelerateMode) {
    // delayTime = (long) (timer * timer) / 50;
    // } else if (accelerateMode) {
    // delayTime = 0;
    // }
    // // if (timer < 98) {
    // // PROGRESS_DISTANCE = 4;
    // // delayTime = (long) (timer * timer) / 3000;
    // // } else if (timer < 108 && timer > 98) {
    // // PROGRESS_DISTANCE = 3;
    // // delayTime = (long) (timer * timer) / 3000;
    // // } else if (timer < 118 && timer > 108) {
    // // PROGRESS_DISTANCE = 2;
    // // delayTime = (long) (timer * timer) / 2000;
    // // } else {
    // // PROGRESS_DISTANCE = 1;
    // // delayTime = (long) (timer * timer) / 1000;
    // // }
    // timer += 1;
    // // canvas.drawRect(mReachedRectF, mReachedBarPaint);
    // Log.i("NumberProgressBar", "invalidate");
    // }
    //
    // canvas.drawRect(mReachedRectF, mReachedBarPaint);
    // postInvalidateDelayed(delayTime, (int) mReachedRectF.left,
    // (int) mReachedRectF.top, (int) mReachedRectF.right,
    // (int) mReachedRectF.bottom);

    // if (mDrawUnreachedBar) {
    // canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
    // }

    // if (mIfDrawText)
    // canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd,
    // mTextPaint);
    // }

    public void hideProgressBar() {
        // startAnimation(alphaAnimation);
        resetProgressBar();
    }

    public void resetProgressBar() {
        timer = 0;
        progressOffset = 0f;
        mCurrentProgress = 0f;
        mLastProgress = 0f;
        isFinishedLoadOnePage = false;
        accelerateMode = false;
        decelerateMode = true;
        isFinishLoading = false;
        isStart = true;
        Log.i("NumberProgressBar", "isFinishLoading==" + isFinishLoading);

        // 非常关键的一步，此处重置了矩形，避免了在每次点击链接的瞬间显示加载满的加载条后又重头开始加载。
        mReachedRectF = new RectF(0, 0, 0, 0);
        currentAlpha = 1.0f;
        setAlpha(1.0f);
        // calculateDrawRectF(mLastProgress);
        // invalidate();
        // setProgress(0);
    }

    private void initializePainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    private void calculateDrawRectFWithoutProgressText() {
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight())
                / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight
                / 2.0f;
    }

    private void calculateDrawRectF() {

        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if (getProgress() == 0) {
            mDrawReachedBar = false;
            mDrawTextStart = getPaddingLeft();
        } else {
            mDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
            mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight())
                    / (getMax() * 1.0f)
                    * getProgress()
                    - mOffset
                    + getPaddingLeft();
            mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight
                    / 2.0f;
            mDrawTextStart = (mReachedRectF.right + mOffset);
        }

        mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint
                .ascent()) / 2.0f));

        if ((mDrawTextStart + mDrawTextWidth) >= getWidth() - getPaddingRight()) {
            mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
            mReachedRectF.right = mDrawTextStart - mOffset;
        }

        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
        if (unreachedBarStart >= getWidth() - getPaddingRight()) {
            mDrawUnreachedBar = false;
        } else {
            mDrawUnreachedBar = true;
            mUnreachedRectF.left = unreachedBarStart;
            mUnreachedRectF.right = getWidth() - getPaddingRight();
            mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight
                    / 2.0f;
            mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight
                    / 2.0f;
        }
    }

    private void calculateDrawRectF(float curProgress) {

        // mCurrentDrawText = String.format("%d", getProgress() * 100 /
        // getMax());
        // mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        // mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if (getProgress() == 0) {
            mDrawReachedBar = false;
            // mDrawTextStart = getPaddingLeft();
        } else {
            mDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = 0f;
            mReachedRectF.right = (getWidth()) / (getMax() * 1.0f)
                    * curProgress;
            // Log.i("NumberProgressBar", "mReachedRectF.right=="
            // + mReachedRectF.right);
            mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight
                    / 2.0f;
            mDrawTextStart = (mReachedRectF.right + mOffset);
        }

        // mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() +
        // mTextPaint
        // .ascent()) / 2.0f));
        //
        // if ((mDrawTextStart + mDrawTextWidth) >= getWidth() -
        // getPaddingRight()) {
        // mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
        // mReachedRectF.right = mDrawTextStart - mOffset;
        // }
        //
        // float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
        // if (unreachedBarStart >= getWidth() - getPaddingRight()) {
        // mDrawUnreachedBar = false;
        // } else {
        // mDrawUnreachedBar = true;
        // mUnreachedRectF.left = unreachedBarStart;
        // mUnreachedRectF.right = getWidth() - getPaddingRight();
        // mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight
        // / 2.0f;
        // mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight
        // / 2.0f;
        // }
    }

    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public float getProgress() {
        return mCurrentProgress;
    }

    public float getLastProgress() {
        return mLastProgress;
    }

    public float getMax() {
        return mMaxProgress;
    }

    public float getReachedBarHeight() {
        return mReachedBarHeight;
    }

    public float getUnreachedBarHeight() {
        return mUnreachedBarHeight;
    }

    public void setProgressTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextAlpha(int alpha) {
        mTextPaint.setAlpha(alpha);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this.mUnreachedBarColor = barColor;
        mUnreachedBarPaint.setColor(mUnreachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this.mReachedBarColor = progressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarHeight(float height) {
        mReachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height) {
        mUnreachedBarHeight = height;
    }

    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            mPrefix = "";
        else {
            mPrefix = prefix;
        }
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(getProgress() + by);
        }

        if (mListener != null) {
            mListener.onProgressChange(getProgress(), getMax());
        }
    }

    public void setProgress(float progress) {
        if (progress <= getMax() && progress >= 0) {
            this.mCurrentProgress = progress;
            progressOffset = mCurrentProgress - mLastProgress;
            if (isFinishLoading) {
                isFinishLoading = false;
            }
            if (mCurrentProgress == getMax()) {
                accelerateMode = true;
                decelerateMode = false;
                isFinishedLoadOnePage = false;
            }
            Log.i("NumberProgressBar", "isFinishedLoadOnePage=="
                    + isFinishedLoadOnePage);
            if (getVisibility() == View.INVISIBLE) {
                setVisibility(View.VISIBLE);
            }
            if (!isFinishedLoadOnePage) {
                isFinishedLoadOnePage = true;
                invalidate();
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putFloat(INSTANCE_MAX, getMax());
        bundle.putFloat(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString(INSTANCE_PREFIX, getPrefix());
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
            mUnreachedBarHeight = bundle
                    .getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
            mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
            mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
            initializePainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setPrefix(bundle.getString(INSTANCE_PREFIX));
            setSuffix(bundle.getString(INSTANCE_SUFFIX));
            setProgressTextVisibility(bundle
                    .getBoolean(INSTANCE_TEXT_VISIBILITY) ? ProgressTextVisibility.Visible : ProgressTextVisibility.Invisible);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public void setProgressTextVisibility(ProgressTextVisibility visibility) {
        mIfDrawText = visibility == ProgressTextVisibility.Visible;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return mIfDrawText;
    }

    public void setOnProgressBarListener(OnProgressBarListener listener) {
        mListener = listener;
    }

    public void setOnLastProgressCompleteListener(
            OnLastProgressCompleteListener listener) {
        this.mLastProgressCompleteListener = listener;
    }

    public interface OnLastProgressCompleteListener {
        public void onLastProgressComplete();
    }
}
