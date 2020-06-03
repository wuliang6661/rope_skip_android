package com.tohabit.skip.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tohabit.skip.R;
import com.tohabit.skip.utils.DensityUtil;

public class ShapeDialog extends Dialog {


    private onSelectListener listener;


    public static ShapeDialog getInatance(Context context) {
        return new ShapeDialog(context, R.style.MaterialDialogSheet);
    }


    public ShapeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }


    private void init() {
        View view = getLayoutInflater().inflate(R.layout.dialog_test_invitation, null);
        view.findViewById(R.id.tv_cancer_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        view.findViewById(R.id.ll_weixin_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if(listener != null){
                  listener.clickWeiXin();
                }
            }
        });
        view.findViewById(R.id.ll_pyq_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if(listener != null){
                    listener.clickPYQ();
                }
            }
        });
        view.findViewById(R.id.ll_save_picture_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.saveImg();
                }
            }
        });
        setContentView(view);
        setCancelable(true);

        Window window = getWindow();
        WindowManager.LayoutParams paramsWindow = window.getAttributes();
        paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow.height = DensityUtil.dp2px(getContext(), 160);//android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        paramsWindow.gravity = Gravity.BOTTOM;
        window.setAttributes(paramsWindow);

    }


    public void setListener(onSelectListener listener) {
        this.listener = listener;
    }

    public interface onSelectListener {

        void clickWeiXin();

        void clickPYQ();


        void saveImg();

    }

}
