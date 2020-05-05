package com.habit.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habit.commonlibrary.R;

public class LilayItemEditTextWithDivider extends LinearLayout {

    private LinearLayout itemLilay;
    private TextView itemNameTV;
    private EditText editContentET;
    private TextView dividerTV;
    private TextView verticalDividerTV;

    private int inputType = -1;
    private int maxLength = -1;
    private int nameId = -1;
    private String hintStr = "";
    private int visibility = 1;
    private int backgroundColor=-1;
    private int textColor=-1;
    private int dividerColor=-1;


    public LilayItemEditTextWithDivider(Context context, AttributeSet attrs,
                                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public LilayItemEditTextWithDivider(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LilayItemEditTextWithDivider);
        nameId = a.getResourceId(R.styleable.LilayItemEditTextWithDivider_led_name, -1);
        backgroundColor = a.getColor(R.styleable.LilayItemEditTextWithDivider_led_background_color, -1);
        textColor = a.getColor(R.styleable.LilayItemEditTextWithDivider_led_text_color, -1);
        dividerColor = a.getColor(R.styleable.LilayItemEditTextWithDivider_led_divider_color, -1);
        inputType = a.getInt(R.styleable.LilayItemEditTextWithDivider_led_et_input_type, -1);
        hintStr = a.getString(R.styleable.LilayItemEditTextWithDivider_led_hint);
        maxLength = a.getInt(R.styleable.LilayItemEditTextWithDivider_led_maxlength, -1);
        visibility = a.getInt(R.styleable.LilayItemEditTextWithDivider_led_divider_visibility, 1);
        a.recycle();

        init(context);
        // TODO Auto-generated constructor stub
    }

    public LilayItemEditTextWithDivider(Context context) {
        super(context);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_lilay_item_edit_text_with_divider, this);
        itemLilay = (LinearLayout) findViewById(R.id.lilay_item_edit_text_with_divider);
        itemNameTV = (TextView) findViewById(R.id.tv_name_item_edit_text_with_divider);
        editContentET = (EditText) findViewById(R.id.et_content_item_edit_text_with_divider);
        dividerTV = (TextView) findViewById(R.id.tv_divider_item_edit_text_with_divider);
        verticalDividerTV = (TextView) findViewById(R.id.tv_divider_vertical_item_edit_text_with_divider);

        if (nameId != -1) {
            itemNameTV.setText(nameId);
        }

        if(backgroundColor!=-1){
            itemLilay.setBackgroundColor(backgroundColor);
            editContentET.setBackgroundColor(backgroundColor);
        }

        if(textColor!=-1){
            itemNameTV.setTextColor(textColor);
            editContentET.setTextColor(textColor);
        }

        if(dividerColor!=-1){
            dividerTV.setBackgroundColor(dividerColor);
            verticalDividerTV.setBackgroundColor(dividerColor);
        }

        if (maxLength != -1) {
            editContentET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        if (!TextUtils.isEmpty(hintStr)) {
            editContentET.setHint(hintStr);
        }

        if (inputType != -1) {
            if (inputType == 1) {
                editContentET.setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (inputType == 2) {
                editContentET.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (inputType == 3) {
                editContentET.setInputType(InputType.TYPE_CLASS_PHONE);
            } else if (inputType == 4) {
                editContentET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (inputType == 5) {
                editContentET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            }
        }

        if (visibility == 1) {
            dividerTV.setVisibility(View.VISIBLE);
        } else if (visibility == 2) {
            dividerTV.setVisibility(View.GONE);
        } else if (visibility == 3) {
            dividerTV.setVisibility(View.INVISIBLE);
        }

//        editContentET.requestFocus();
    }

    public void setEditTextSingleLine(boolean isSingleLine) {
        editContentET.setSingleLine(isSingleLine);
    }

    public void setItemNameVisibility(int visibility) {
        itemNameTV.setVisibility(visibility);
    }

    public void setItemNameText(String itemNameStr) {
        itemNameTV.setText(itemNameStr);
    }

    public void setItemNameTextColor(int colorId) {
        itemNameTV.setTextColor(colorId);
    }

    public void setEditContentETHint(String hintStr) {
        editContentET.setHint(hintStr);
    }

    public void setEditContentETText(String contentStr) {
        editContentET.setText(contentStr);
    }

    public String getEditContentETText() {
        return editContentET.getText().toString();
    }

    public void setEditContentETEnabled(boolean isEnabled) {
        editContentET.setEnabled(isEnabled);
    }

    public void setDividerVisibility(int visibility) {
        dividerTV.setVisibility(visibility);
    }

    public void setEditTextTouchListener(OnTouchListener listener) {
        editContentET.setOnTouchListener(listener);
    }

    public void addTextWatcher(TextWatcher watcher) {
        editContentET.addTextChangedListener(watcher);
    }

    public void setEditTextGravity(int gravity) {
        editContentET.setGravity(gravity);
    }

    public void setInputType(int type) {
        editContentET.setInputType(type);
    }

    public EditText getEditText() {
        return editContentET;
    }

    public void setInputNumberALetterEnabled(boolean isEnabled) {
        if (isEnabled) {
            editContentET.setKeyListener(new NumberKeyListener() {

                @Override
                public int getInputType() {
                    // TODO Auto-generated method stub
                    return InputType.TYPE_CLASS_TEXT;
                }

                @Override
                protected char[] getAcceptedChars() {
                    // TODO Auto-generated method stub
                    String str = getResources().getString(R.string.accepted_char_fraction);
                    char[] numberChars = str.toCharArray();
                    return numberChars;
                }
            });
        } else {
            editContentET.setKeyListener(null);
        }
    }
}
