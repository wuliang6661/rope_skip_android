package com.habit.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.habit.commonlibrary.R;

public class ProgressbarLayout extends LinearLayout {

    LinearLayout ll;
    ProgressBar progressBar;
    TextView textTV;

    String textStr = "";
    int textColor = -1;
    int textSize = -1;
    int llBackground = -1;
    int cpbVisibility = -1;
    int textVisibility = -1;

    public ProgressbarLayout(Context context) {
        super(context);
        init(context);
    }

    public ProgressbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressbarLayout);
        textStr = a.getString(R.styleable.ProgressbarLayout_pwt_text);
        textColor = a.getColor(R.styleable.ProgressbarLayout_pwt_text_color, -1);
        textSize = a.getDimensionPixelSize(R.styleable.ProgressbarLayout_pwt_text_size, -1);
        llBackground = a.getResourceId(R.styleable.ProgressbarLayout_pwt_background, -1);
        cpbVisibility = a.getInt(R.styleable.ProgressbarLayout_pwt_progressbar_visibility, -1);
        textVisibility = a.getInt(R.styleable.ProgressbarLayout_pwt_text_visibility, -1);
        init(context);
        a.recycle();
    }

    public ProgressbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_progressbar_with_text, this);
        ll = (LinearLayout) findViewById(R.id.ll_content_layout_progress_bar_with_text);
        progressBar = (ProgressBar) findViewById(R.id.cpb_progress_bar_layout_progress_bar_with_text);
        textTV = (TextView) findViewById(R.id.tv_progress_text_layout_progress_bar_with_text);

        if (!TextUtils.isEmpty(textStr)) {
            textTV.setText(textStr);
        }

        if (textColor != -1) {
            textTV.setTextColor(textColor);
        }

        if (textSize != -1) {
            textTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        if (llBackground != -1) {
            ll.setBackgroundResource(llBackground);
        }

        if (cpbVisibility == 1) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (cpbVisibility == 2) {
            progressBar.setVisibility(View.INVISIBLE);
        } else if (cpbVisibility == 3) {
            progressBar.setVisibility(View.GONE);
        }

        if (textVisibility == 1) {
            textTV.setVisibility(View.VISIBLE);
        } else if (textVisibility == 2) {
            textTV.setVisibility(View.INVISIBLE);
        } else if (textVisibility == 3) {
            textTV.setVisibility(View.GONE);
        }
    }
}
