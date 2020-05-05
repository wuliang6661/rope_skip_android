package com.habit.star.widget;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class CutRelativeLayout extends RelativeLayout {
    public CutRelativeLayout(Context context) {
        super(context);
    }

    public CutRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CutRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 创建分享的图片文件
     */
    public String createShareFile() {
        Log.d("35583", "createShareFile: 111");

        Bitmap bitmap = createBitmap();
        //将生成的Bitmap插入到手机的图片库当中，获取到图片路径
        String filePath = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                bitmap, null, null);

        Log.d("35583", "createShareFile: "+filePath);
        //及时回收Bitmap对象，防止OOM
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        //转uri之前必须判空，防止保存图片失败
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return getRealPathFromURI(getContext(), Uri.parse(filePath));
    }

    /**
     * 创建分享Bitmap
     */
    private Bitmap createBitmap() {
        Log.d("35583", "createBitmap: 111");
        //自定义ViewGroup，一定要手动调用测量，布局的方法
        measure(getLayoutParams().width, getLayoutParams().height);
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        //如果图片对透明度无要求，可以设置为RGB_565
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        Log.d("35583", "createBitmap: 111");
        return bitmap;
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor == null) {
                return "";
            }
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

