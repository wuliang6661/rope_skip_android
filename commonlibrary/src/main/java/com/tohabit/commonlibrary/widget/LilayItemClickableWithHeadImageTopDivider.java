package com.tohabit.commonlibrary.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tohabit.commonlibrary.R;
import com.tohabit.commonlibrary.utils.DensityUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class LilayItemClickableWithHeadImageTopDivider extends LinearLayout {

    private LinearLayout itemLilay;
    private LinearLayout itemContentLilay;
    private AppCompatImageView headerIconIv;
    private Button markerBtn;
    private TextView statusTv;
    private ImageView itemRightIV;
    private TextView itemNameTV;
    private TextView itemChildNameTV;
    private TextView itemContentShowTV;
    private TextView itemRemindShowTV;
    private RoundedImageView imageContentIV;
    private de.hdodenhof.circleimageview.CircleImageView circleImageContentIV;
    private TextView topDividerTV;
    private TextView dividerTV;

    private final float DEFAULT_ROTATION = 90f;

    private int headerImageResourceId = -1;
    private String title = "";
    private String childTitle = "";
    private String content = "";
    private String statusText = "";
    private int statusVisibility = -1;
    private int contentPaddingLeft = -1;
    private int contentPaddingRight = -1;
    private int topDividerPaddingLeft = -1;
    private int topDividerPaddingRight = -1;
    private int bottomDividerPaddingLeft = -1;
    private int bottomDividerPaddingRight = -1;
    ;
    private int contentCircleImageWidth = -1;
    private int contentCircleImageHeight = -1;
    private boolean isSingleline = true;
    private int titleTextSize = -1;
    private int contentTextSize = -1;
    private int titleTextColor = -1;
    private int contentTextColor = -1;
    private int dividerColor = -1;
    private int imageResourceId = -1;
    private int contentType = -1;
    private int detailIvVisibility = -1;
    private int topDividerVisibility = -1;
    private int bottomDividerVisibility = -1;
    private float dividerMargins = -1.0f;
    private int backGroundId = -1;

    public LilayItemClickableWithHeadImageTopDivider(Context context, AttributeSet attrs,
                                                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public LilayItemClickableWithHeadImageTopDivider(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LilayItemClickableWithHeadImageTopDivider);
        title = a.getString(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_title);
        childTitle = a.getString(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_child_title);
        content = a.getString(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content);
        statusVisibility = a.getInt(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_status_visibility, -1);
        statusText = a.getString(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_status_text);
        isSingleline = a.getBoolean(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_is_single_line, true);
        titleTextSize = (int) a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_title_text_size, -1);
        contentTextSize = (int) a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_text_size, -1);
        titleTextColor = a.getColor(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_title_text_color, -1);
        contentTextColor = a.getColor(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_text_color, -1);
        dividerColor = a.getColor(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_divider_color, -1);
        imageResourceId = a.getResourceId(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_src, -1);
        headerImageResourceId = a.getResourceId(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_head_icon, -1);
        contentType = a.getInt(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_type, -1);
        detailIvVisibility = a.getInt(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_iv_detail_visibility, -1);
        topDividerVisibility = a.getInt(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_top_divider_visibility, -1);
        bottomDividerVisibility = a.getInt(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_bottom_divider_visibility, -1);
        dividerMargins = a.getDimension(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_divider_margins, -1.0f);
        backGroundId = a.getResourceId(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_background, -1);
        contentPaddingLeft = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_left_padding, -1);
        contentPaddingRight = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_right_padding, -1);
        topDividerPaddingLeft = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_top_divider_left_padding, -1);
        topDividerPaddingRight = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_top_divider_right_padding, -1);
        bottomDividerPaddingLeft = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_bottom_divider_left_padding, -1);
        bottomDividerPaddingRight = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_bottom_divider_right_padding, -1);
        contentCircleImageWidth = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_circle_image_width, -1);
        contentCircleImageHeight = a.getDimensionPixelSize(R.styleable.LilayItemClickableWithHeadImageTopDivider_lichtd_content_circle_image_height, -1);
        a.recycle();

        init(context);
        // TODO Auto-generated constructor stub
    }

    public LilayItemClickableWithHeadImageTopDivider(Context context) {
        super(context);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_item_info_detail_clickable_top_divider, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        setClickable(true);
        itemLilay = (LinearLayout) findViewById(R.id.lilay_item_layout_item_info_detail_clickable_top_divider);
        headerIconIv = (AppCompatImageView) findViewById(R.id.iv_header_icon_layout_item_info_detail_clickable_top_divider);
        itemContentLilay = (LinearLayout) findViewById(R.id.lilay_content_layout_item_info_detail_clickable_top_divider);
        statusTv = (TextView) findViewById(R.id.tv_item_status_layout_item_info_detail_clickable_top_divider);
        itemNameTV = (TextView) findViewById(R.id.tv_item_name_layout_item_info_detail_clickable_top_divider);
        itemChildNameTV = (TextView) findViewById(R.id.tv_item_child_title_show_layout_item_info_detail_clickable_top_divider);
        itemRightIV = (ImageView) findViewById(R.id.iv_item_right_layout_item_info_detail_clickable_top_divider);
        itemContentShowTV = (TextView) findViewById(R.id.tv_item_content_show_layout_item_info_detail_clickable_top_divider);
        itemRemindShowTV = (TextView) findViewById(R.id.tv_item_remind_show_layout_item_info_detail_clickable_top_divider);
        imageContentIV = (RoundedImageView) findViewById(R.id.iv_rounded_image_content_layout_item_info_detail_clickable_top_divider);
        circleImageContentIV = (CircleImageView) findViewById(R.id.iv_circle_image_content_layout_item_info_detail_clickable_top_divider);
        topDividerTV = (TextView) findViewById(R.id.tv_top_divider_layout_item_info_detail_clickable_top_divider);
        dividerTV = (TextView) findViewById(R.id.tv_divider_layout_item_info_detail_clickable_top_divider);

        if (!TextUtils.isEmpty(title)) {
            itemNameTV.setText(title);
        }
        if (!TextUtils.isEmpty(childTitle)) {
            itemChildNameTV.setText(childTitle);
            itemChildNameTV.setVisibility(VISIBLE);
        }

        if (isSingleline) {
            itemContentShowTV.setSingleLine(true);
        } else {
            itemContentShowTV.setSingleLine(false);
        }

        if (!TextUtils.isEmpty(content)) {
            itemContentShowTV.setText(content);
        }

        if (!TextUtils.isEmpty(statusText)) {
            statusTv.setText(statusText);
        }

        if (titleTextColor != -1) {
            itemNameTV.setTextColor(titleTextColor);
        }
        if (contentTextColor != -1) {
            itemContentShowTV.setTextColor(contentTextColor);
        }
        if (dividerColor != -1) {
            topDividerTV.setBackgroundColor(dividerColor);
            dividerTV.setBackgroundColor(dividerColor);
        }
        if (titleTextSize != -1) {
            itemNameTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        }
        if (contentTextSize != -1) {
            itemContentShowTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        }

        if (imageResourceId != -1) {
            itemRightIV.setImageResource(imageResourceId);
        }

        if (headerImageResourceId != -1) {
            headerIconIv.setVisibility(View.VISIBLE);
            headerIconIv.setImageResource(headerImageResourceId);
        } else {
            headerIconIv.setVisibility(View.GONE);
        }

        if (contentType != -1) {
            if (contentType == 1) {
                itemContentShowTV.setVisibility(View.VISIBLE);
                imageContentIV.setVisibility(View.GONE);
                circleImageContentIV.setVisibility(View.GONE);
            } else if (contentType == 2) {
                itemContentShowTV.setVisibility(View.GONE);
                imageContentIV.setVisibility(View.VISIBLE);
                imageContentIV.setCornerRadius(0);
                circleImageContentIV.setVisibility(View.GONE);
            } else if (contentType == 3) {
                itemContentShowTV.setVisibility(View.GONE);
                imageContentIV.setVisibility(View.VISIBLE);
                imageContentIV.setCornerRadiusDimen(R.dimen.corner_radius_imageview_default);
                circleImageContentIV.setVisibility(View.GONE);
            } else if (contentType == 4) {
                itemContentShowTV.setVisibility(View.GONE);
                imageContentIV.setVisibility(View.GONE);
                circleImageContentIV.setVisibility(View.VISIBLE);
            }
        }


        if (statusVisibility != -1) {
            if (statusVisibility == 1) {
                statusTv.setVisibility(View.VISIBLE);
            } else if (statusVisibility == 2) {
                statusTv.setVisibility(View.INVISIBLE);
            } else if (statusVisibility == 3) {
                statusTv.setVisibility(View.GONE);
            }
        }

        if (contentPaddingLeft != -1 && contentPaddingRight != -1) {
            itemLilay.setPadding(contentPaddingLeft, 0, contentPaddingRight, 0);
        } else if (contentPaddingLeft == -1 && contentPaddingRight != -1) {
            itemLilay.setPadding(0, 0, contentPaddingRight, 0);
        } else if (contentPaddingLeft != -1 && contentPaddingRight == -1) {
            itemLilay.setPadding(contentPaddingLeft, 0, 0, 0);
        }

        if (topDividerPaddingLeft != -1 && topDividerPaddingRight != -1) {
            LayoutParams params = (LayoutParams) topDividerTV
                    .getLayoutParams();
            params.setMargins((int) topDividerPaddingLeft, 0, topDividerPaddingRight, 0);
            topDividerTV.setLayoutParams(params);
        } else if (topDividerPaddingLeft == -1 && topDividerPaddingRight != -1) {
            LayoutParams params = (LayoutParams) topDividerTV
                    .getLayoutParams();
            params.setMargins(0, 0, topDividerPaddingRight, 0);
            topDividerTV.setLayoutParams(params);
        } else if (topDividerPaddingLeft != -1 && topDividerPaddingRight == -1) {
            LayoutParams params = (LayoutParams) topDividerTV
                    .getLayoutParams();
            params.setMargins((int) topDividerPaddingLeft, 0, 0, 0);
            topDividerTV.setLayoutParams(params);
        }

        if (bottomDividerPaddingLeft != -1 && bottomDividerPaddingRight != -1) {
            LayoutParams params = (LayoutParams) dividerTV
                    .getLayoutParams();
            params.setMargins((int) bottomDividerPaddingLeft, 0, bottomDividerPaddingRight, 0);
            dividerTV.setLayoutParams(params);
        } else if (bottomDividerPaddingLeft == -1 && bottomDividerPaddingRight != -1) {
            LayoutParams params = (LayoutParams) dividerTV
                    .getLayoutParams();
            params.setMargins(0, 0, bottomDividerPaddingRight, 0);
            dividerTV.setLayoutParams(params);
        } else if (bottomDividerPaddingLeft != -1 && bottomDividerPaddingRight == -1) {
            LayoutParams params = (LayoutParams) dividerTV
                    .getLayoutParams();
            params.setMargins((int) bottomDividerPaddingLeft, 0, 0, 0);
            dividerTV.setLayoutParams(params);
        }

        if (detailIvVisibility != -1) {
            if (detailIvVisibility == 1) {
                itemRightIV.setVisibility(View.VISIBLE);
            } else if (detailIvVisibility == 2) {
                itemRightIV.setVisibility(View.INVISIBLE);
            } else if (detailIvVisibility == 3) {
                itemRightIV.setVisibility(View.GONE);
            }
        }

        if (topDividerVisibility != -1) {
            if (topDividerVisibility == 1) {
                topDividerTV.setVisibility(View.VISIBLE);
            } else if (topDividerVisibility == 2) {
                topDividerTV.setVisibility(View.INVISIBLE);
            } else if (topDividerVisibility == 3) {
                topDividerTV.setVisibility(View.GONE);
            }
        }

        if (bottomDividerVisibility != -1) {
            if (bottomDividerVisibility == 1) {
                dividerTV.setVisibility(View.VISIBLE);
            } else if (bottomDividerVisibility == 2) {
                dividerTV.setVisibility(View.INVISIBLE);
            } else if (bottomDividerVisibility == 3) {
                dividerTV.setVisibility(View.GONE);
            }
        }

        if (dividerMargins != -1.0f) {
            LayoutParams params = (LayoutParams) topDividerTV
                    .getLayoutParams();
            params.setMargins((int) dividerMargins, 0, (int) dividerMargins, 0);
            topDividerTV.setLayoutParams(params);

            LayoutParams params2 = (LayoutParams) dividerTV
                    .getLayoutParams();
            params.setMargins((int) dividerMargins, 0, (int) dividerMargins, 0);
            dividerTV.setLayoutParams(params);
        }

        if (backGroundId != -1) {
            itemLilay.setBackgroundResource(backGroundId);
        }

        if (contentCircleImageHeight != -1 || contentCircleImageWidth != -1) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) circleImageContentIV.getLayoutParams();
            params.width = contentCircleImageWidth;
            params.height = contentCircleImageHeight;
            circleImageContentIV.setLayoutParams(params);
        }

    }

    public void setItemNameText(CharSequence itemNameStr) {
        itemNameTV.setText(itemNameStr);
    }

    public void setStatusTvVisibility(int visibility) {
        statusTv.setVisibility(visibility);
    }

    public void setStatusText(CharSequence text) {
        statusTv.setText(text);
    }

    public void setItemNameColor(int colorId) {
        itemNameTV.setTextColor(colorId);
    }

    public void setItemNameColorListState(ColorStateList color) {
        itemNameTV.setTextColor(color);
    }

    public void setItemContent(String contentStr) {
        if (!TextUtils.isEmpty(contentStr)) {
            itemContentShowTV.setText(contentStr);
        } else {
            itemContentShowTV.setText("");
        }
    }

    public void setRemindContent(String remindStr) {
        if (!TextUtils.isEmpty(remindStr)) {
            itemRemindShowTV.setText(remindStr);
        } else {
            itemRemindShowTV.setText("");
        }
    }

    public void setItemContent(SpannableStringBuilder spanable) {
        if (spanable != null && !TextUtils.isEmpty(spanable.toString())) {
            itemContentShowTV.setText(spanable);
        } else {
            itemContentShowTV.setText("");
        }
    }

    public void setItemContent(String contentStr, String remindStr) {
        if (!TextUtils.isEmpty(contentStr)) {
            itemContentShowTV.setText(contentStr);
        } else {
            itemContentShowTV.setText(remindStr);
        }
    }

    public void setImageContent(String imageUrl) {
        if (imageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(getContext())
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageContentIV);
        } else if (circleImageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(getContext())
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(circleImageContentIV);
        }
    }

    public void setImageContent(String imageUrl, Context context) {
        if (imageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageContentIV);
        } else if (circleImageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(circleImageContentIV);
        }
    }

    public void setImageContent(String imageUrl, int resId, Context context) {
        if (imageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageContentIV);
        } else if (circleImageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(circleImageContentIV);
        }
    }

    public void setImageContent(String imageUrl, String remindStr) {
        if (TextUtils.isEmpty(remindStr)) {
            itemContentShowTV.setVisibility(View.VISIBLE);
            imageContentIV.setVisibility(View.GONE);
            circleImageContentIV.setVisibility(View.GONE);
            itemContentShowTV.setText(remindStr);
            return;
        }
        if (imageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(getContext())
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageContentIV);
        } else if (circleImageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(getContext())
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(circleImageContentIV);
        }
    }

    public void setImageContent(String imageUrl, String remindStr, Context context) {
        if (imageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageContentIV);
        } else if (circleImageContentIV.getVisibility() == View.VISIBLE) {
            Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(0.3f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(circleImageContentIV);
        }
    }

    public void setItemNameVisiblity(int visibility) {
        itemNameTV.setVisibility(visibility);
    }

    public void setItemContentGravity(int gravity) {
        itemContentShowTV.setGravity(gravity);
    }

    public void setItemContentColor(int colorId) {
        itemContentShowTV.setTextColor(colorId);
    }

    public void setItemContentColorListState(ColorStateList color) {
        itemContentShowTV.setTextColor(color);
    }

    public void setMarkerBtnText(String markerStr) {
        markerBtn.setText(markerStr);
    }

    public void setMarkerBtnTextColor(int colorID) {
        markerBtn.setTextColor(colorID);
    }

    public void setMarkerBtnBackground(int resid) {
        markerBtn.setBackgroundResource(resid);
    }

    public void setDividerVisibity(int visibility) {
        dividerTV.setVisibility(visibility);
    }

    public void setMarkerVisibility(int visibility) {
        markerBtn.setVisibility(visibility);
    }

    public TextView getItemContentTv() {
        return itemContentShowTV;
    }

    public TextView getItemRemindShowTv() {
        return itemRemindShowTV;
    }

    public String getItemContent() {
        if (itemContentShowTV.getText() != null) {
            return itemContentShowTV.getText().toString();
        } else {
            return "";
        }
    }

    public void rotateRightIV(float rotationDegrees) {
        itemRightIV.setRotation(rotationDegrees);
    }

    public void toggleRotate(float rotationDegrees) {
        if (itemRightIV.getRotation() == rotationDegrees) {
            itemRightIV.setRotation(0);
        } else {
            itemRightIV.setRotation(rotationDegrees);
        }
    }

    public void toggleRotateDefaultDegrees() {
        if (itemRightIV.getRotation() == DEFAULT_ROTATION) {
            itemRightIV.setRotation(0);
        } else {
            itemRightIV.setRotation(DEFAULT_ROTATION);
        }
    }

    public void setRightIVVisibility(int visibility) {
        itemRightIV.setVisibility(visibility);
    }

    public void setItemNameToMarkerMarginDP(int dp) {
        LayoutParams params = (LayoutParams) itemNameTV
                .getLayoutParams();
        params.setMargins(DensityUtil.dp2px(getContext(), dp), 0, 0, 0);
        itemNameTV.setLayoutParams(params);
    }

    public void setOnRightClickListener(OnClickListener listener) {
        itemContentShowTV.setOnClickListener(listener);
    }


    /**
     * 设置内容做出图片
     *
     * @param resourceId
     */
    public void setContentLeftDrawable(int resourceId) {
        Drawable drawable = getResources().getDrawable(resourceId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        itemContentShowTV.setCompoundDrawables(drawable, null, null, null);
    }

}
