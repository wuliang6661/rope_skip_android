package com.habit.star.ui.train.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;

/**
 * @version V1.0
 * @date: 2020-02-22 14:08
 * @ClassName: TestResultShareSuccessFragment.java
 * @Description:分享成功
 * @author: sundongdong
 */
public class TestResultShareSuccessFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    private String shareFilePath;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            catchScreen();
        }
    };

    public static TestResultShareSuccessFragment newInstance(Bundle bundle) {
        TestResultShareSuccessFragment fragment = new TestResultShareSuccessFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_result_share_success;
    }

    @Override
    protected String getLogTag() {
        return "TestResultShareSuccessFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        handler.sendEmptyMessageDelayed(0, 500);
    }


    private void initDialog() {

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    private void catchScreen() {
        // 1. 初始化布局：
        View decorView = _mActivity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
//        decorView.measure(View.MeasureSpec.makeMeasureSpec(decorView.getWidth(), View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(decorView.getHeight(), View.MeasureSpec.EXACTLY));
//        decorView.layout((int) decorView.getX(), (int) decorView.getY(), (int) decorView.getX() + decorView.getMeasuredWidth(), (int) decorView.getY() + decorView.getMeasuredHeight());
        createPicture(decorView);
    }

    // 2. 将布局转成bitmap
    private void createPicture(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getWidth(), view.getHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        //3. 将bitmap存入本地
        shareFilePath = "/Pictures/" + Calendar.getInstance().getTime().toString() + ".png";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            FileOutputStream fos = null;
            try {
                File file = new File(sdCardDir, shareFilePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(file);

                //当指定压缩格式为PNG时保存下来的图片显示正常
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                log("图片生成：" + file.getAbsolutePath());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
