package com.tohabit.skip.utils;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseDialog;
import com.tohabit.skip.pojo.po.VersionBO;
import com.tohabit.skip.ui.activity.MainActivity;
import com.tohabit.skip.widget.HorizontalProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/2111:14
 * desc   : App检查更新的工具类
 * version: 1.0
 */
public class UpdateUtils {

    private boolean mIsCancel = false;
    private String version = "rope_skip.apk";

    public void checkUpdate(onSourssListener listener) {
        HttpServerImpl.getVersion().subscribe(new HttpResultSubscriber<VersionBO>() {
            @Override
            public void onSuccess(VersionBO s) {
                if (listener != null) {
                    listener.onComplan();
                }
                if (s == null) {
                    return;
                }
                String replace = AppUtils.getAppVersionName().replace(".", "");
                int version = 0;
                try {
                    version = Integer.parseInt(replace);
                } catch (Exception e) {
                    ToastUtils.showShort("版本解析有误");
                    return;
                }

                if (Integer.parseInt(s.getVersionCode()) > version) {
                    if (listener != null) {
                        listener.isHaveNewVersion();
                    }
                    createCustomDialogTwo(s, listener);
                } else {
                    if (listener != null) {
                        listener.nowIsNew();
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                if (listener != null) {
                    listener.onComplan();
                }
                // 首页不显示异常弹窗，只有检测更新时弹出
                if (StringUtils.isEmpty(message) || AppManager.getAppManager().curremtActivity()
                        instanceof MainActivity) {
                    return;
                }
                ToastUtils.showShort(message);
            }
        });
    }


    /**
     * 检测进度回调
     */
    public interface onSourssListener {

        void onComplan();

        void isHaveNewVersion();

        void nowIsNew();

        void update(File file);
    }

    HorizontalProgressBar progress;

    private void createCustomDialogTwo(VersionBO versionBO, onSourssListener listener) {
        BaseDialog baseDialog = new BaseDialog(AppManager.getAppManager().curremtActivity(), R.style.BaseDialog, R.layout.custom_dialog_two_layout);

        baseDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if ("1".equals(versionBO.getAppIsUpdate())) { //需要强制更新
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        });
        TextView textView = baseDialog.findViewById(R.id.tv_msg);
        TextView title = baseDialog.findViewById(R.id.tv_title);
        TextView cancle = baseDialog.findViewById(R.id.cancle);
        View line = baseDialog.findViewById(R.id.line);
        title.setText(versionBO.getVersionName() + "版本更新");
        HorizontalProgressBar progress = baseDialog.findViewById(R.id.progress);

        TextView upup = baseDialog.findViewById(R.id.commit);

        upup.setOnClickListener(v -> downloadAPK(versionBO.getDownLoadUrl()));

        textView.setText(versionBO.getUpdateText());
        if ("1".equals(versionBO.getAppIsUpdate())) {   //需要强制更新
            textView.setVisibility(View.VISIBLE);
            baseDialog.setCanceledOnTouchOutside(false);
            cancle.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            baseDialog.setCanceledOnTouchOutside(true);
            textView.setVisibility(View.GONE);
        }
        cancle.setOnClickListener(view -> {
            baseDialog.dismiss();
        });
        baseDialog.show();
    }


    /* 开启新线程下载apk文件
     */
    public void downloadAPK(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIsCancel = false;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File dir = new File(FILE_APK_PATH);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
//                        int length = conn.getContentLength();

                        File apkFile = new File(FILE_APK_PATH, version);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];

                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            Message message = Message.obtain();
                            message.obj = count;
                            handler.sendMessage(message);
                            // 下载完成
                            if (numread < 0) {
                                handler.sendEmptyMessage(0x22);
                                AppUtils.installApp(apkFile);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.e(Thread.currentThread().getName(), "2");
            progress.setCurrentProgress((Float) msg.obj);
        }
    };


    /**
     * 更新apk存放地址
     */
    public final static String FILE_APK_PATH = getFolderPath() + "/APK";

    /**
     * sdb/SynwayOSC
     */
    public static final String getFolderPath() {
        return Environment.getExternalStorageDirectory().getPath()
                + "/rope_skip";
    }
}
