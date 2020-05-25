package com.habit.star.ui.young.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SizeUtils;
import com.habit.star.R;
import com.habit.star.event.model.SwitchMainEvent;
import com.habit.star.pojo.po.TaskBO;
import com.habit.star.utils.AppManager;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PopYoungHomeDialog extends PopupWindow {

    private Activity activity;
    private View dialogView;
    private List<TaskBO> list;

    public PopYoungHomeDialog(Activity activity, List<TaskBO> s) {
        super(activity);

        this.activity = activity;
        this.list = s;
        dialogView = LayoutInflater.from(activity).inflate(R.layout.pop_home, null);

        initView();
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(SizeUtils.dp2px(450));
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    /**
     * 初始化布局
     */
    private void initView() {
        dialogView.findViewById(R.id.close_img).setOnClickListener(v -> dismiss());
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        LGRecycleViewAdapter<TaskBO> adapter = new LGRecycleViewAdapter<TaskBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_task;
            }

            @Override
            public void convert(LGViewHolder holder, TaskBO taskBO, int position) {
                LinearLayout item_layout = (LinearLayout) holder.getView(R.id.item_layout);
                holder.setText(R.id.type_name, taskBO.getTypeName());
                holder.setText(R.id.task_miaoshuy, taskBO.getName());
                holder.setText(R.id.task_nengliang, taskBO.getDescribes());
                AppCompatTextView goPk = (AppCompatTextView) holder.getView(R.id.go_pk);
                goPk.setBackgroundResource(R.mipmap.ic_entry_bg1);
                switch (taskBO.getType()) {
                    case 0:   //pk任务
                        item_layout.setBackgroundResource(R.mipmap.ic_rw_bg1);
                        holder.setImageResurce(R.id.task_img, R.mipmap.ic_js_pk1);
                        goPk.setText("去挑战");
                        break;
                    case 1:   //幸运兑换
                        item_layout.setBackgroundResource(R.mipmap.ic_xydh_bg);
                        holder.setImageResurce(R.id.task_img, R.mipmap.ic_js_pk2);
                        goPk.setText("去浏览");
                        break;
                    case 2:   //课程学习
                        item_layout.setBackgroundResource(R.mipmap.ic_kcxx_bg);
                        holder.setImageResurce(R.id.task_img, R.mipmap.ic_js_pk3);
                        goPk.setText("去学习");
                        break;
                    case 3:   //知识学习
                        item_layout.setBackgroundResource(R.mipmap.ic_zsxx_bg);
                        holder.setImageResurce(R.id.task_img, R.mipmap.ic_js_pk4);
                        goPk.setText("去完成");
//                        goPk.setBackgroundResource(R.mipmap.ic_to_finish_bg);
                        break;
                    case 4:   //参与问答
                        item_layout.setBackgroundResource(R.mipmap.ic_cywd_bg);
                        holder.setImageResurce(R.id.task_img, R.mipmap.ic_js_pk5);
                        goPk.setText("去参与");
                        break;
                }
            }
        };
        adapter.setOnItemClickListener(R.id.go_pk, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                dismiss();
                SwitchMainEvent event = new SwitchMainEvent();
                switch (list.get(position).getType()) {
                    case 0:   //pk任务
                        Activity activity = AppManager.getAppManager().curremtActivity();
                        Intent intent = new Intent(activity,PKHomeActivity.class);
                        activity.startActivity(intent);
                        break;
                    case 1:   //幸运兑换
                        event.gotoSonPage = 4;
                        EventBus.getDefault().post(event);
                        break;
                    case 2:   //课程学习
                        event.gotoSonPage = 1;
                        EventBus.getDefault().post(event);
                        break;
                    case 3:   //知识学习
                        event.gotoSonPage = 2;
                        EventBus.getDefault().post(event);
                        break;
                    case 4:   //参与问答
                        event.gotoSonPage = 3;
                        EventBus.getDefault().post(event);
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }


    /***
     * 显示时将屏幕置为透明
     */
    public void showAtLocation(View parent) {
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

}
