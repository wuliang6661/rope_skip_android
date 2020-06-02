package com.tohabit.skip.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.tohabit.skip.R;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/6/19:25
 * desc   :
 * version: 1.0
 */

public class CircleProgressbar extends TextView {

    //外部轮廓的颜色
    private int outLineColor = Color.WHITE;

    //外部轮廓的宽度
    private int outLineWidth = SizeUtils.dp2px(15);

    //内部圆的颜色
    private ColorStateList inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);

    //中心圆的颜色
    private int circleColor;

    //进度条的颜色
    private int progressLineColor = Color.parseColor("#AFB6F8");

    //进度条的宽度
    private int progressLineWidth = SizeUtils.dp2px(11);

    //画笔
    private Paint mPaint = new Paint();

    //进度条画笔
    private Paint progressPoint = new Paint();

    //进度条的矩形区域
    private RectF mArcRect = new RectF();

    //进度
    private float progress = 0;

    //进度条类型
    private ProgressType mProgressType = ProgressType.COUNT;

    //进度倒计时时间
    private long timeMillis = 3000;

    //倒计时剩余时间
    private long shengyuTime;

    //View的显示区域。
    final Rect bounds = new Rect();

    //进度条通知。
    private OnCountdownProgressListener mCountdownProgressListener;
    private int listenerWhat = 0;

    public CircleProgressbar(Context context) {
        this(context, null);
    }

    public CircleProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }


    private void initialize(Context context, AttributeSet attributeSet) {
        mPaint.setAntiAlias(true);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressbar);
        if (typedArray.hasValue(R.styleable.CircleProgressbar_in_circle_color))
            inCircleColors = typedArray.getColorStateList(R.styleable.CircleProgressbar_in_circle_color);
//        else
//            inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
//        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        typedArray.recycle();
    }

    /**
     * 设置外部轮廓圆的颜色
     */
    public void setOutLineColor(int outLineColor) {
        this.outLineColor = outLineColor;
        invalidate();
    }

    /**
     * 设置外部轮廓圆的宽度
     */
    public void setOutLineWidth(int outLineWidth) {
        this.outLineWidth = outLineWidth;
        invalidate();
    }

    /**
     * 设置中心圆的颜色
     */
    public void setInCircleColor(int inCircleColor) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor);
        invalidate();
    }

    private void validateCircleColor() {
        int circleColorTemp = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        if (circleColor != circleColorTemp) {
            circleColor = circleColorTemp;
            invalidate();
        }
    }

    /**
     * 设置圆形进度条颜色
     */
    public void setProgressColor(@ColorInt int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    /**
     * 设置圆形进度条宽度
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    /**
     * 设置进度条值
     */
    public void setProgress(float progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }


    private float validateProgress(float progress) {
        if (progress > 100)
            progress = 100;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 获取进度值
     */
    public float getProgress() {
        return progress;
    }

    /**
     * 设置倒计时时间
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 获取倒计时时间
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度条类型  是0-100 还是100_0
     */
    public void setProgressType(ProgressType progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }


    private void resetProgress() {
        shengyuTime = 0;
        switch (mProgressType) {
            case COUNT:
                progress = 0;
                break;
            case COUNT_BACK:
                progress = 100;
                break;
        }
    }

    /**
     * 获取进度条类型
     */
    public ProgressType getProgressType() {
        return mProgressType;
    }

    /**
     * 设置进度监听
     */
    public void setCountdownProgressListener(int what, OnCountdownProgressListener mCountdownProgressListener) {
        this.listenerWhat = what;
        this.mCountdownProgressListener = mCountdownProgressListener;
    }


    public void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 开始旋转倒计时
     */
    public void reStart() {
        resetProgress();
        start();
    }


    public void stop() {
        removeCallbacks(progressChangeTask);
//        resetProgress();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取view的边界
        getDrawingRect(bounds);

        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = size / 2;

        //画内部背景
//        int circleColor = Color.parseColor("#76AFE9");
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#00000000"));
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth, mPaint);

        //画边框圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outLineWidth);
        mPaint.setColor(outLineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth / 2, mPaint);

        //画字
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        float textY = bounds.centerY() - (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(getText().toString(), bounds.centerX(), textY, paint);

        //画进度条
        progressPoint.setColor(progressLineColor);
        progressPoint.setStyle(Paint.Style.STROKE);
        progressPoint.setStrokeWidth(progressLineWidth);
        progressPoint.setStrokeCap(Paint.Cap.ROUND);   //绘制圆角
        //绘制渐变色
        SweepGradient sweepGradient = new SweepGradient(bounds.centerX(),bounds.centerY(),
                Color.parseColor("#ABBBF8"),Color.parseColor("#97D6F8"));
        progressPoint.setShader(sweepGradient);
        progressPoint.setAntiAlias(true);
        int deleteWidth = outLineWidth / 2;
        mArcRect.set(deleteWidth, deleteWidth, bounds.right - deleteWidth, bounds.bottom - deleteWidth);

        canvas.drawArc(mArcRect, -270, 360 * progress / 100, false, progressPoint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineWidth = 4 * (outLineWidth + progressLineWidth);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height ? width : height) + lineWidth;
        setMeasuredDimension(size, size);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        validateCircleColor();
    }


    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                //判断是顺数进度条还是倒数进度条
                case COUNT:
                    shengyuTime = shengyuTime + 1000;
                    progress = (float) (shengyuTime * 1.0 / timeMillis * 100);
                    break;
                case COUNT_BACK:
                    if (shengyuTime == 0) {
                        shengyuTime = timeMillis;
                    }
                    shengyuTime = shengyuTime - 1000;
                    progress = (float) (shengyuTime * 1.0 / timeMillis * 100);
                    break;
            }
            if (shengyuTime <= timeMillis) {
                if (mCountdownProgressListener != null)
                    mCountdownProgressListener.onProgress(listenerWhat, progress);
                invalidate();
                postDelayed(progressChangeTask, 1000);
            } else {
                progress = 100;
                progress = validateProgress(progress);
            }
        }
    };


    public enum ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK;
    }

    public interface OnCountdownProgressListener {
        void onProgress(int what, float progress);
    }
}