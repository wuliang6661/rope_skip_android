package com.tohabit.skip.ui.find.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tohabit.skip.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 筛选的弹窗
 */
public class PopShiXuanWindow extends PopupWindow {

    /**
     * 筛选的选项
     */
    TagFlowLayout flowLayout;

    private Activity activity;
    private View dialogView;
    private TextView cancle, commit;

    /**
     * 年龄是否选中
     */
    private int isSelectNianling;
    /**
     * 身高是否选中
     */
    private int isSelectShengao;
    /**
     * 体重是否选中
     */
    private int isSelectTizhong;

    TagAdapter adapter;


    public PopShiXuanWindow(Activity activity, int isSelectNianling, int isSelectShengao, int isSelectTizhong) {
        super(activity);
        this.activity = activity;
        this.isSelectNianling = isSelectNianling;
        this.isSelectShengao = isSelectShengao;
        this.isSelectTizhong = isSelectTizhong;
        dialogView = LayoutInflater.from(activity).inflate(R.layout.pop_shaixuan, null);
        flowLayout = dialogView.findViewById(R.id.id_flowlayout);
        cancle = dialogView.findViewById(R.id.cancle);
        commit = dialogView.findViewById(R.id.commit);
        cancle.setOnClickListener(v -> dismiss());

        setFlow();

        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> backgroundAlpha(1f));

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(listener != null){
                    listener.onSelect(PopShiXuanWindow.this.isSelectNianling,PopShiXuanWindow.this.isSelectShengao,PopShiXuanWindow.this.isSelectTizhong);
                }
            }
        });
        dialogView.findViewById(R.id.kong_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }


    private void setFlow() {
        List<String> list = new ArrayList<>();
        list.add("年龄");
        list.add("身高");
        list.add("体重");
        adapter = new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.text_shaixuan,
                        flowLayout, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.mipmap.flow_select_bg);
                tv.setTextColor(Color.parseColor("#FFFFFF"));
                if (position == 0) {
                    isSelectNianling = 1;
                }
                if (position == 1) {
                    isSelectShengao = 1;
                }
                if (position == 2) {
                    isSelectTizhong = 1;
                }
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.mipmap.flow_un_select_bg);
                tv.setTextColor(Color.parseColor("#CCCCCC"));
                if (position == 0) {
                    isSelectNianling = 0;
                }
                if (position == 1) {
                    isSelectShengao = 0;
                }
                if (position == 2) {
                    isSelectTizhong = 0;
                }
            }
        };
        Set<Integer> set = new TreeSet<>();
        if (isSelectNianling == 1) {   //年龄选中
            set.add(0);
        }
        if (isSelectShengao == 1) {
            set.add(1);
        }
        if (isSelectTizhong == 1) {
            set.add(2);
        }
        adapter.setSelectedList(set);
        flowLayout.setAdapter(adapter);
    }

    private onSelectListener listener;

    public void setListener(onSelectListener listener) {
        this.listener = listener;
    }

    public interface onSelectListener {

        void onSelect(int isSelectNianling, int isSelectShengao, int isSelectTizhong);
    }

}
