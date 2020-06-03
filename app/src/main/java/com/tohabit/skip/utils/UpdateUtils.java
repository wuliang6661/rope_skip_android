package com.tohabit.skip.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
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

    private Context context;

    private boolean mIsCancel = false;
    private String version = "rope_skip.apk";

    public void checkUpdate(Context context, onUpdateListener listener) {
        this.context = context;
        HttpServerImpl.getVersion().subscribe(new HttpResultSubscriber<VersionBO>() {
            @Override
            public void onSuccess(VersionBO s) {
                if (s == null) {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                    return;
                }
                if (s.getVersionCode() > AppUtils.getAppVersionCode()) {
                    if (s.getForceUpdate() == 1) { //强制更新
                        downloadAPK(s.getVersionUrl());
                    } else {
                        createCustomDialogTwo(s);
                    }
                } else {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                // 首页不显示异常弹窗，只有检测更新时弹出
                if (StringUtils.isEmpty(message) || AppManager.getAppManager().curremtActivity()
                        instanceof MainActivity) {
                    return;
                }
                ToastUtils.showShort(message);
            }
        });
    }


    public interface onUpdateListener {
        void noUpdate();
    }


    private ProgressDialog progressDialog;

    /*
     * 显示正在下载对话框
     */
    private void showDownloadDialog(String url) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载...");
        progressDialog.setCancelable(false);//不能手动取消下载进度对话框
        progressDialog.setProgressNumberFormat("");
        progressDialog.show();

        // 下载文件
        downloadAPK(url);
    }


    private void createCustomDialogTwo(VersionBO versionBO) {
        BaseDialog baseDialog = new BaseDialog(AppManager.getAppManager().curremtActivity(), R.style.BaseDialog, R.layout.custom_dialog_two_layout);
        TextView cancle = baseDialog.findViewById(R.id.cancle);
        AppCompatButton upup = baseDialog.findViewById(R.id.commit);
        upup.setOnClickListener(v -> {
                    baseDialog.dismiss();
                    downloadAPK(versionBO.getVersionUrl());
                }
        );
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
