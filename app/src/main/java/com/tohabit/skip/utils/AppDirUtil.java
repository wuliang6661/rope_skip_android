package com.tohabit.skip.utils;

import android.os.Environment;

import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;

import java.io.File;

/**
 * APP目录创建，以及相应路径的创建
 */
public class AppDirUtil {

    public static String appDir = Constants.PATH_APP;
    public static String cacheDir = Constants.PATH_CACHE;
    public static String downDir = Constants.PATH_DOWNLOAD;
    public static String imageDir = Constants.PATH_IMAGE;

    public static File getAppDir() {
        String appDir = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            appDir = StorageUtils.printSDCardRoot() + AppDirUtil.appDir
                    + File.separator;
        } else {
            appDir = App.getInstance().getCacheDir().getAbsolutePath();
        }
        File file = new File(appDir);
        FileUtils.makesureDirExist(file);
        return file;
    }

    public static void setAppDir(String appDir) {
        AppDirUtil.appDir = appDir;
    }

    /**
     * 获取某个文件的目录
     *
     * @return
     */
    public static File getFilesDir(String name) {
        File main = getAppDir();
        File file = new File(main.getAbsoluteFile() + File.separator + name);
        FileUtils.makesureDirExist(file);
        return file;
    }

    /**
     * 获取某个目录
     *
     * @return
     */
    public static File getDir(String folderName) {
        File main = getAppDir();
        File file = new File(main.getAbsoluteFile() + File.separator + folderName);
        FileUtils.makesureDirExist(file);
        return file;
    }

    /**
     * 获取保存图片文件的目录
     *
     * @return
     */
    public static File getImageFilesDir() {
        File main = getAppDir();
        File file = new File(main.getAbsoluteFile() + File.separator + imageDir);
        FileUtils.makesureDirExist(file);
        return file;
    }

    /**
     * 获取下载文件的目录
     *
     * @return
     */
    public static File getDownloadFilesDir() {
        File main = getAppDir();
        File file = new File(main.getAbsoluteFile() + downDir);
        FileUtils.makesureDirExist(file);
        return file;
    }

    /**
     * 创建要下载的文件
     *
     * @return
     */
    public static File getDownloadFile(String FileUrl) {
        getFileName(FileUrl);
        File main = getAppDir();
        File file = new File(main.getAbsoluteFile() + downDir
                + File.separator
                + getFileName(FileUrl));
        FileUtils.makesureFileExist(file);
        return file;
    }

    /**
     * 获取文件名称
     *
     * @return
     */
    public static String getFileName(String FileUrl) {
        String[] nameArr = FileUrl.split("/");
        int length = nameArr.length;
        return nameArr[length - 1];
    }

    /**
     * 获取缓存文件存储的目录
     *
     * @return
     */
    public static File getCacheDir() {
        File main = getAppDir();
        File file = new File(main.getAbsoluteFile() + cacheDir);
        FileUtils.makesureDirExist(file);
        return file;
    }

//    /**
//     * 获取下载文件的目录
//     * @return
//     */
//    public static String getDiskCacheDir(Context context) {
//        String cachePath = null;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || !Environment.isExternalStorageRemovable()) {
//            cachePath = context.getExternalCacheDir().getPath();
//        } else {
//            cachePath = context.getCacheDir().getAbsolutePath();
//        }
//        return cachePath;
//    }
}
