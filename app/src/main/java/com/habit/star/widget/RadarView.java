package com.habit.star.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.habit.star.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2913:55
 * desc   :雷达图
 * * version: 1.0
 */
public class RadarView extends View {

    //数据个数
    private int count = 5;
    //成绩圆点半径
    private int valueRadius = 8;
    //网格最大半径
    private float radius;
    //中心X
    private float centerX;
    //中心Y
    private float centerY;
    //雷达区画笔
    private Paint mainPaint;
    //文本画笔
    private Paint textPaint;
    //数据区画笔
    private Paint valuePaint;
    //标题文字
    private List<String> titles;
    //各维度分值
    private List<Double> data;
    //各维度图标
    private List<Integer> images;
    //数据最大值
    private double maxValue = 5;
    //弧度
    private float angle;

    private Matrix matrix;


    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //雷达区画笔初始化
        mainPaint = new Paint();
        mainPaint.setColor(Color.parseColor("#979797"));
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1);
        mainPaint.setStyle(Paint.Style.STROKE);
        //文本画笔初始化
        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(SizeUtils.sp2px(12));
        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);
        //数据区（分数）画笔初始化
        valuePaint = new Paint();
        valuePaint.setColor(Color.parseColor("#908ACCF6"));
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);

        matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f);
        titles = new ArrayList<>();
        titles.add("动作标准度");
        titles.add("协调性");
        titles.add("稳定性");
        titles.add("节奏性");
        titles.add("耐力");
        count = titles.size();

        //默认分数
        data = new ArrayList<>(count);
        data.add(0d);
        data.add(0d);
        data.add(0d);
        data.add(0d);
        data.add(0d);

        images = new ArrayList<>();
        images.add(R.mipmap.ic_test_result8);
        images.add(R.mipmap.ic_test_result6);
        images.add(R.mipmap.ic_stability);
        images.add(R.mipmap.ic_jumping_standard);
        images.add(R.mipmap.ic_test_result3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.6f;
        centerX = w / 2;
        centerY = h / 2 + 30;
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPolygon(canvas);//绘制蜘蛛网
        drawLines(canvas);//绘制直线
        drawTitle(canvas);//绘制标题
        drawRegion(canvas);//绘制覆盖区域
    }

    /**
     * 绘制多边形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //1度=1*PI/180   360度=2*PI   那么我们每旋转一次的角度为2*PI/内角个数
        //中心与相邻两个内角相连的夹角角度
        angle = (float) (2 * Math.PI / count);
        //每个蛛丝之间的间距
        float r = radius / (count - 1);

        for (int i = 0; i < count; i++) {
            //当前半径
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.moveTo(x, y);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x1 = (float) (centerX + curR * Math.sin(angle / 2));
                    float y1 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x1, y1);
                    float x2 = (float) (centerX - curR * Math.sin(angle / 2));
                    float y2 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x2, y2);
                    float x3 = (float) (centerX - curR * Math.sin(angle));
                    float y3 = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x3, y3);
                    float x4 = centerX;
                    float y4 = centerY - curR;
                    path.lineTo(x4, y4);
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }


    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        path.reset();
        //直线1
        path.moveTo(centerX, centerY);
        float x1 = (float) (centerX + radius * Math.sin(angle));
        float y1 = (float) (centerY - radius * Math.cos(angle));
        path.lineTo(x1, y1);
        //直线2
        path.moveTo(centerX, centerY);
        float x2 = (float) (centerX + radius * Math.sin(angle / 2));
        float y2 = (float) (centerY + radius * Math.cos(angle / 2));
        path.lineTo(x2, y2);
        //直线3
        path.moveTo(centerX, centerY);
        float x3 = (float) (centerX - radius * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * Math.cos(angle / 2));
        path.lineTo(x3, y3);
        //直线4
        path.moveTo(centerX, centerY);
        float x4 = (float) (centerX - radius * Math.sin(angle));
        float y4 = (float) (centerY - radius * Math.cos(angle));
        path.lineTo(x4, y4);
        //直线5
        path.moveTo(centerX, centerY);
        float x5 = (float) (centerX);
        float y5 = (float) (centerY - radius);
        path.lineTo(x5, y5);
        path.close();
        canvas.drawPath(path, mainPaint);
    }

    /**
     * 绘制标题文字
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        if (count != titles.size()) {
            return;
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;//标题高度
        //绘制文字1
        float x1 = centerX;
        float y1 = centerY - radius;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images.get(0));
        canvas.drawBitmap(bitmap, x1 - SizeUtils.dp2px(18), y1 - fontHeight / 5 - bitmap.getHeight() -
                SizeUtils.dp2px(18), textPaint);
        canvas.drawText(titles.get(0), x1, y1 - fontHeight / 5 - 20, textPaint);
        canvas.drawText(data.get(0) + "", x1, y1 + 10, textPaint);
        //绘制文字2
        float x2 = (float) (centerX + radius * Math.sin(angle)) - 20;
        float y2 = (float) (centerY - radius * Math.cos(angle));
        float dis = textPaint.measureText(titles.get(1));//标题一半的宽度
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), images.get(1));
        canvas.drawBitmap(bitmap1, x2 + dis - SizeUtils.dp2px(18), y2 + fontHeight / 5 - bitmap.getHeight() -
                SizeUtils.dp2px(12), textPaint);
        canvas.drawText(titles.get(1), x2 + dis, y2 + fontHeight / 5, textPaint);
        canvas.drawText(data.get(1) + "", x2 + dis, y2 + fontHeight + 10, textPaint);
        //绘制文字3
        float x3 = (float) (centerX + radius * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * Math.cos(angle / 2)) + SizeUtils.dp2px(30);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), images.get(2));
        canvas.drawBitmap(bitmap2, x3 - SizeUtils.dp2px(18), y3 + fontHeight - bitmap.getHeight() -
                SizeUtils.dp2px(12), textPaint);
        canvas.drawText(titles.get(2), x3, y3 + fontHeight, textPaint);
        canvas.drawText(data.get(2) + "", x3, y3 + fontHeight + 50, textPaint);
        //绘制文字4
        float x4 = (float) (centerX - radius * Math.sin(angle / 2));
        float y4 = (float) (centerY + radius * Math.cos(angle / 2)) + SizeUtils.dp2px(30);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), images.get(3));
        canvas.drawBitmap(bitmap3, x4 - SizeUtils.dp2px(18), y4 + fontHeight - bitmap.getHeight() -
                SizeUtils.dp2px(12), textPaint);
        canvas.drawText(titles.get(3), x4, y4 + fontHeight, textPaint);
        canvas.drawText(data.get(3) + "", x4, y4 + fontHeight + 50, textPaint);
        //绘制文字5
        float x5 = (float) (centerX - radius * Math.sin(angle)) + 20;
        float y5 = (float) (centerY - radius * Math.cos(angle));
        float dis5 = textPaint.measureText(titles.get(1));//标题的宽度

        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), images.get(4));
        canvas.drawBitmap(bitmap4, x5 - dis5 - SizeUtils.dp2px(18), y5 + fontHeight / 5 - bitmap.getHeight() -
                SizeUtils.dp2px(12), textPaint);
        canvas.drawText(titles.get(4), x5 - dis5, y5 + fontHeight / 5, textPaint);
        canvas.drawText(data.get(4) + "", x5 - dis5, y5 + fontHeight + 10, textPaint);
    }

    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Canvas canvas) {
        valuePaint.setAlpha(255);
        Path path = new Path();
        double dataValue;
        double percent;
        //绘制圆点1
        dataValue = data.get(0);
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x1 = centerX;
        float y1 = (float) (centerY - radius * percent);
        path.moveTo(x1, y1);
        valuePaint.setColor(Color.parseColor("#D37B75"));
//        canvas.drawCircle(x1, y1, valueRadius, valuePaint);
        //绘制圆点2
        dataValue = data.get(1);
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x2 = (float) (centerX + radius * percent * Math.sin(angle));
        float y2 = (float) (centerY - radius * percent * Math.cos(angle));
        path.lineTo(x2, y2);
        valuePaint.setColor(Color.parseColor("#7C9BC1"));
//        canvas.drawCircle(x2, y2, valueRadius, valuePaint);
        //绘制圆点3
        dataValue = data.get(2);
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x3 = (float) (centerX + radius * percent * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * percent * Math.cos(angle / 2));
        path.lineTo(x3, y3);
        valuePaint.setColor(Color.parseColor("#7163A0"));
//        canvas.drawCircle(x3, y3, valueRadius, valuePaint);
        //绘制圆点4
        dataValue = data.get(3);
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x4 = (float) (centerX - radius * percent * Math.sin(angle / 2));
        float y4 = (float) (centerY + radius * percent * Math.cos(angle / 2));
        path.lineTo(x4, y4);
        valuePaint.setColor(Color.parseColor("#6AA5D1"));
//        canvas.drawCircle(x4, y4, valueRadius, valuePaint);
        //绘制圆点5
        dataValue = data.get(3);
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x5 = (float) (centerX - radius * percent * Math.sin(angle));
        float y5 = (float) (centerY - radius * percent * Math.cos(angle));
        path.lineTo(x5, y5);
        valuePaint.setColor(Color.parseColor("#5CACAF"));
//        canvas.drawCircle(x5, y5, valueRadius, valuePaint);
        valuePaint.setColor(Color.parseColor("#908ACCF6"));
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        //绘制覆盖区域外的连线
        canvas.drawPath(path, valuePaint);
        //填充覆盖区域
        valuePaint.setAlpha(128);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, valuePaint);
    }


    //设置蜘蛛网颜色
    public void setMainPaintColor(Paint mainPaint) {
        this.mainPaint = mainPaint;
        postInvalidate();
    }

    //设置标题颜色
    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    //设置覆盖局域颜色
    public void setValuePaint(Paint valuePaint) {
        this.valuePaint = valuePaint;
        postInvalidate();
    }

    //设置各门得分
    public void setData(List<Double> data) {
        this.data = data;
        postInvalidate();
    }

    //设置满分分数，默认是100分满分
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }
}