package com.habit.star.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dongdong on 2016/10/31.
 * 进入系统各个界面的工具类
 */

public class StartOtherUIUtil {

    //进入图片选择界面的请求code
    public static final int REQUEST_CODE_START_IMAGE_CHOOSE_UI = 10000;
    //进入拍照界面的请求code
    public static final int REQUEST_CODE_START_IMAGE_CAPTURE_UI = 10001;
    //进入GPS设置设置界面的请求code
    public static final int REQUEST_CODE_INTENT_OPEN_GPS_SETTING = 10002;
    //进入条码扫描界面
    public static final int REQUEST_CODE_START_BAR_CODE_SCAN = 10002;
    //图片文件存储的名字
    private static final String IMAGE_STORE_FILE_NAME = "IMG_%s.jpg";
    //图片存储目录
//    private static final String IMAGE_STORE_FILE_DIR_DEFAULT = Environment.getExternalStorageDirectory() + "/DCIM/Camera/";
    private static String IMAGE_STORE_FILE_DIR_DEFAULT = AppDirUtil.getImageFilesDir().getAbsolutePath();

    /**
     * 调用系统图片库选择图片(activity专用)
     *
     * @param activity
     * @param requestCode
     */
    public static void startImageChoose(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");// 设置类型
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    requestCode);
        } else {

        }
    }

    /**
     * 调用系统图片库选择图片(fragment专用)
     *
     * @param fragment
     * @param requestCode
     */
    public static void startImageChoose(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");// 设置类型
        if (intent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    requestCode);
        }
    }

    /**
     * 调用相机进行拍照，照片默认存储在系统相册
     *
     * @param activity
     * @param requestCode
     * @return 图片的绝对路径
     */
    public static Uri startTakeImage(Activity activity, int requestCode) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(activity.getPackageManager()) != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            String fileName = String.format(IMAGE_STORE_FILE_NAME, sdf.format(new Date()));
            File file = new File(AppDirUtil.getImageFilesDir().getAbsolutePath(), fileName);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(captureIntent, requestCode);
            return Uri.fromFile(file);
        } else {
            return null;
        }
    }

    /**
     * 调用相机进行拍照，照片默认存储在系统相册
     *
     * @param rootFragment
     * @param requestCode
     * @return 图片的绝对路径
     */
    public static Uri startTakeImage(Fragment rootFragment, int requestCode) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(rootFragment.getActivity().getPackageManager()) != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            String fileName = String.format(IMAGE_STORE_FILE_NAME, sdf.format(new Date()));
            File file = new File(AppDirUtil.getImageFilesDir().getAbsolutePath(), fileName);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            rootFragment.startActivityForResult(captureIntent, requestCode);
            return Uri.fromFile(file);
        } else {
            return null;
        }
    }

    /**
     * 打开系统GPS设置界面(fragment专用)
     *
     * @param fragment
     * @param requestCode
     */
    public static void startGPSSetting(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开系统GPS设置界面(fragment专用)
     *
     * @param activity
     * @param requestCode
     */
    public static void startGPSSetting(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开系统详情设置界面
     *
     * @param activity
     * @param requestCode
     */
    public static void startAppDetailSetting(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 调用系统拨打电话的功能
     *
     * @param activity
     * @param phoneNumber
     */
    public static void startPhoneDial(Activity activity, String phoneNumber) {
        Intent intent;
        if (phoneNumber == null || phoneNumber.trim().length() <= 0) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        } else {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.replace(" ", "")));
        }

        activity.startActivity(intent);

    }

}
