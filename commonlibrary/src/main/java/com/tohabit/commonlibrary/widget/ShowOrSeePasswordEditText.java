package com.tohabit.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.tohabit.commonlibrary.R;

/**
 * 显示或隐藏EditText中的字符，用于密码输入框
 * @author sundongdong
 *
 */
public class ShowOrSeePasswordEditText extends android.support.v7.widget.AppCompatEditText implements
		OnFocusChangeListener, TextWatcher {

	private Drawable shIconDrawalbe;
	private Drawable leftIconDrawable;
	private int leftDrawableId=-1;
	private int seeDrawableId=-1;
	private int hideDrawableId=-1;
	private int rightScaleWidth = -1;
	private int rightScaleHeight = -1;
	private boolean isFocus;
	private boolean isShowing = false;


	public ShowOrSeePasswordEditText(Context context, AttributeSet attrs,
                                     int defStyle) {
		super(context, attrs, defStyle);
	}

	public ShowOrSeePasswordEditText(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.editTextStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShowOrSeePasswordEditText);
		leftDrawableId = a.getResourceId(R.styleable.ShowOrSeePasswordEditText_spe_left_image, -1);
		seeDrawableId = a.getResourceId(R.styleable.ShowOrSeePasswordEditText_spe_see_image, -1);
		hideDrawableId = a.getResourceId(R.styleable.ShowOrSeePasswordEditText_spe_hide_image, -1);
		rightScaleWidth = a.getDimensionPixelOffset(R.styleable
				.ShowOrSeePasswordEditText_spe_rightDrawableWidth, dp2px(getContext(), 20));
		rightScaleHeight = a.getDimensionPixelOffset(R.styleable
				.ShowOrSeePasswordEditText_spe_rightDrawableHeight, dp2px(getContext(), 20));
		a.recycle();

		init();
	}

	public ShowOrSeePasswordEditText(Context context) {
		super(context, null);
	}

	private void init() {
        if(leftDrawableId!=-1){
            setLeftDrawable(leftDrawableId);
		}

		if(hideDrawableId!=-1){
			setRightDrawable(hideDrawableId);
		}

		setKeyListener(new NumberKeyListener() {

			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return InputType.TYPE_CLASS_TEXT;
			}

			@Override
			protected char[] getAcceptedChars() {
				// TODO Auto-generated method stub
				String str = getResources().getString(R.string.accepted_char);
				char[] numberChars = str.toCharArray();
				return numberChars;
			}
		});
		setLongClickable(false);
		setTextIsSelectable(false);
		setCustomSelectionActionModeCallback(new ActionMode.Callback() {
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {

			}
		});
		setTransformationMethod(PasswordTransformationMethod.getInstance());
//		// 获取在EditText中DrawableRight，即显示在EditText中的Drawable对象
//		shIconDrawalbe = getCompoundDrawables()[2];
//		if (null == shIconDrawalbe) {
//			shIconDrawalbe = getResources().getDrawable(R.drawable.ic_hidden);
//		}
//
//		shIconDrawalbe.setBounds(0, 0, dp2px(getContext(), 35.0f),
//				dp2px(getContext(), 35.0f));

		setRightIconVisibility(false);
		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	@Override
	public boolean onTextContextMenuItem(int id) {
		return true;
	}

	public void setLeftDrawable(int drawableId) {
		leftIconDrawable = getCompoundDrawables()[0];
		if (null == leftIconDrawable) {
			leftIconDrawable = getResources().getDrawable(drawableId);
		}
		leftIconDrawable.setBounds(0, 0, dp2px(getContext(), 30.0f),
				dp2px(getContext(), 30.0f));
		setLeftIconVisibility(true);
	}

	public void setRightDrawable(int drawableId) {
		shIconDrawalbe = getResources().getDrawable(drawableId);

		shIconDrawalbe.setBounds(0, 0, rightScaleWidth,
				rightScaleHeight);

		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], shIconDrawalbe,
				getCompoundDrawables()[3]);
	}

	protected void setRightIconVisibility(boolean visibility) {
		Drawable tempDrawable = visibility ? shIconDrawalbe : null;
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], tempDrawable,
				getCompoundDrawables()[3]);
	}

	protected void setLeftIconVisibility(boolean visibility) {
		Drawable tempDrawable = visibility ? leftIconDrawable : null;
		setCompoundDrawables(tempDrawable, getCompoundDrawables()[1],
				getCompoundDrawables()[2], getCompoundDrawables()[3]);
	}

	/**
	 * @说明：isInIconWidth, isInIconHeight为true，触摸点在删除图标之内，则视为点击了删除图标 event.getX()
	 *                    获取相对应自身左上角的X坐标 event.getY() 获取相对应自身左上角的Y坐标 getWidth()
	 *                    获取控件的宽度 getHeight() 获取控件的高度 getTotalPaddingRight()
	 *                    获取删除图标左边缘到控件右边缘的距离 getPaddingRight()
	 *                    获取删除图标右边缘到控件右边缘的距离 isInIconWidth: getWidth() -
	 *                    getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离 getWidth() -
	 *                    getPaddingRight() 计算删除图标右边缘到控件左边缘的距离 isInIconHeight:
	 *                    distance 删除图标顶部边缘到控件顶部边缘的距离 distance + height
	 *                    删除图标底部边缘到控件顶部边缘的距离
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
					toggleShow();
				}
			}
		}

		return super.onTouchEvent(event);
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
		this.isFocus = hasFocus;
		// if (isFocus) {
		// if (getText().length() > 0) {
		// setRightIconVisibility(true);
		// } else {
		// setRightIconVisibility(false);
		// }
		// }
		if (isFocus) {
			if (getText().length() > 0) {
				setRightIconVisibility(true);
			}
		} else {
			setRightIconVisibility(false);
		}

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (isFocus) {
			if (s.length() > 0) {
				setRightIconVisibility(true);
			} else {
				setRightIconVisibility(false);
			}
		}
	}

	private void toggleShow() {
		if (!isShowing) {
			// setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			setTransformationMethod(HideReturnsTransformationMethod
					.getInstance());
			if(seeDrawableId!=-1) {
				setRightDrawable(seeDrawableId);
			}else{
				setRightDrawable(R.drawable.ic_see_psw);
			}
			isShowing = true;
		} else {
			// setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
			setTransformationMethod(PasswordTransformationMethod.getInstance());
			Editable etable_setting = getText();
			Selection.setSelection(etable_setting, etable_setting.length());
			if(hideDrawableId!=-1) {
				setRightDrawable(hideDrawableId);
			}else{
				setRightDrawable(R.drawable.ic_hidden);
			}
			isShowing = false;
		}
		setLongClickable(false);
		postInvalidate();
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
