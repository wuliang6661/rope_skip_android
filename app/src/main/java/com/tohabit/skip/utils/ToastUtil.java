package com.tohabit.skip.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;

/**
 * Created by sundongdong on 2017/2/24.
 */
public class ToastUtil {

    static ToastUtil td;
    Context context;
    static Toast toast;
    String msg;


    public ToastUtil(Context context) {
        this.context = context;
    }

    public static void show(int resId) {
        show(App.getInstance().getString(resId));
    }

    public static void show(String msg) {
////        if (td == null) {
//            td = new ToastUtil(App.getInstance());
////        }
//        td.setText(msg);
//        td.create(msg).show();

        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                //已在主线程中，可以更新UI
//                Context context = AppManager.getAppManager().curremtActivity();
                Context context = Utils.getApp();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //使用布局加载器，将编写的toast_layout布局加载进来
                View view = layoutInflater.inflate(R.layout.layout_dialog_toast, null);
                //获取TextView
                TextView title = view.findViewById(R.id.tv_toast_msg);
                //设置显示的内容
                title.setText(msg);
                Toast toast = new Toast(context);
                //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
                toast.setGravity(Gravity.CENTER, 0, 0);
                //设置显示时间
                toast.setDuration(Toast.LENGTH_SHORT);

                toast.setView(view);
                toast.show();
            }
        });
    }

    public static void shortShow(String msg) {
        if (td == null) {
            td = new ToastUtil(App.getInstance());
        }
        td.setText(msg);
        td.createShort().show();
    }

    public Toast create(String msg) {
        View contentView = View.inflate(context, R.layout.layout_dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }

    public Toast createShort() {
        View contentView = View.inflate(context, R.layout.layout_dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
