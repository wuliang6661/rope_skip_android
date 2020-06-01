package com.tohabit.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tohabit.commonlibrary.R;

public class LilayItemEditTopDivider extends LinearLayout {


    private LinearLayout itemLilay;
    private TextView itemTitleLilay;
    private EditText itemEditLilay;
    private String title = "";
    private String hintText = "";
    private String inputstyle = "";
    private int titleTextSize = -1;
    private int contentTextSize = -1;
    private int titleTextColor = -1;
    private int contentTextColor = -1;
    private int dividerColor = -1;

    public LilayItemEditTopDivider(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public LilayItemEditTopDivider(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LilayItemEditTopDivider);
        title = a.getString(R.styleable.LilayItemEditTopDivider_lichtd_edit_title);
        hintText = a.getString(R.styleable.LilayItemEditTopDivider_lichtd_edit_content);
        titleTextSize = (int) a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_edit_title_text_size, -1);
        contentTextSize = (int) a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_edit_content_text_size, -1);
        titleTextColor = a.getColor(R.styleable.LilayItemEditTopDivider_lichtd_edit_title_text_color, -1);
        contentTextColor = a.getColor(R.styleable.LilayItemEditTopDivider_lichtd_edit_content_text_color, -1);
        dividerColor = a.getColor(R.styleable.LilayItemEditTopDivider_lichtd_edit_divider_color, -1);
//        topDividerVisibility = a.getInt(R.styleable.LilayItemEditTopDivider_lichtd_top_divider_visibility, -1);
//        bottomDividerVisibility = a.getInt(R.styleable.LilayItemEditTopDivider_lichtd_bottom_divider_visibility, -1);
//        dividerMargins = a.getDimension(R.styleable.LilayItemEditTopDivider_lichtd_divider_margins, -1.0f);
//        backGroundId = a.getResourceId(R.styleable.LilayItemEditTopDivider_lichtd_background, -1);
//        contentPaddingLeft = a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_content_left_padding, -1);
//        contentPaddingRight = a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_content_right_padding, -1);
//        topDividerPaddingLeft = a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_top_divider_left_padding, -1);
//        topDividerPaddingRight = a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_top_divider_right_padding, -1);
//        bottomDividerPaddingLeft = a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_bottom_divider_left_padding, -1);
//        bottomDividerPaddingRight = a.getDimensionPixelSize(R.styleable.LilayItemEditTopDivider_lichtd_bottom_divider_right_padding, -1);
        a.recycle();

        init(context);
        // TODO Auto-generated constructor stub
    }

    public LilayItemEditTopDivider(Context context) {
        super(context);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_item_edit_info_detail_clickable_top_divider, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        setClickable(true);
        itemLilay = (LinearLayout) findViewById(R.id.lilay_item_layout_item_edit_info_detail_clickable_top_divider);
        itemTitleLilay = (AppCompatTextView) findViewById(R.id.tv_item_title_layout_item_edit_info_detail_clickable_top_divider);
        itemEditLilay = (EditText) findViewById(R.id.tv_item_edit_layout_item_edit_info_detail_clickable_top_divider);

        if (!TextUtils.isEmpty(title)) {
            itemTitleLilay.setText(title);
        }

        if (!TextUtils.isEmpty(hintText)) {
            itemEditLilay.setHint(hintText);
        }

        if (titleTextColor != -1) {
            itemTitleLilay.setTextColor(titleTextColor);
        }
        if (contentTextColor != -1) {
            itemEditLilay.setTextColor(contentTextColor);
        }
    }


    public String getContentEdit() {
        return itemEditLilay.getText().toString();
    }
}
