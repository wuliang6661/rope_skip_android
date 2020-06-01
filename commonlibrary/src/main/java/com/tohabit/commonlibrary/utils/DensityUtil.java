package com.tohabit.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/*
 * dip与px的换算
 */
public class DensityUtil {

	public static final int dp2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	public static final int px2dp(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().density);
	}
	
	/**
	 * 获取当前分辨率下指定单位对应的像素大小（根据设备信息） px,dip,sp -> px
	 * 
	 * Paint.setTextSize()单位为px
	 * 
	 * 代码摘自：TextView.setTextSize()
	 * 
	 * @param unit
	 *            TypedValue.COMPLEX_UNIT_*
	 * @param size
	 * @return
	 */
	public static float getRawSize(Context context, int unit, float size) {
		Context c = context;
		Resources r;

		if (c == null)
			r = Resources.getSystem();
		else
			r = c.getResources();

		return TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
	}
	
	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	
}
