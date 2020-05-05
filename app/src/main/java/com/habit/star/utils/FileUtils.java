package com.habit.star.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件处理的公共类
 *
 * @author ~wangjian3~
 * @version v1.1
 * @date 2014-11-12
 * @修改时间 2016-1-18
 * @修改人 dongdong
 */

public class FileUtils {

    /**
     * http协议
     */
    public static final String HTTP = "http://";

    /**
     * drawable文件夹协议
     */
    public static final String DRAWABLE = "drawable://";

    public static final String DOT = ".";

    /**
     * file协议
     */
    public static final String FILE = "file://";

    /**
     * 无协议
     */
    public static final String NONE = "NONE";

    private static final String CUSTOM_DIR = "/hik/";

    private static final String PNG = ".png";

    private static final String JPG = ".jpg";

    private static String SDPATH = Environment.getExternalStorageDirectory() + "/"; // sd卡根目录路径

    public static final String ASSETS_FILE_PREFIX = "assets://";

    private static int FILESIZE = 4 * 1024;

    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 创建新的文件
    private static boolean __createNewFile(File file) {
        boolean isCreate = false;
        makesureParentExist(file);
        if (file.exists()) {
            delete(file);
        }

        try {
            isCreate = file.createNewFile();
        } catch (IOException localIOException) {
        }
        return isCreate;
    }

    // 文件复制
    public static void copy(InputStream srcInputStream, OutputStream desOutputStream) throws IOException {
        byte[] aryByte;
        int i = 524288; // 512k
        try {
            aryByte = new byte[i];
            while (true) {
                int ret = srcInputStream.read(aryByte);
                if (ret == -1) {
                    break;
                } else {
                    desOutputStream.write(aryByte, 0, ret);
                }
            }
        } catch (IOException localIOException) {
        } finally {
            srcInputStream.close();
            desOutputStream.close();
            desOutputStream.flush();
        }
    }

    public static void copy(String srcFile, String desFile) {
        if (!hasExists(srcFile)) {
            return;
        }
        makesureFileExist(desFile);
        if (!hasExists(desFile)) {
            return;
        }
        try {
            copy(new FileInputStream(srcFile), new FileOutputStream(desFile));
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static void createNewFile(File file) {
        if (!__createNewFile(file)) {
            String strError = String.valueOf(file.getAbsolutePath()) + " doesn't be created!";
            throw new RuntimeException(strError);
        }
    }

    // 删除文件
    public static void delete(File file) {
        if ((file != null && file.exists()) && !file.delete()) {
            String strError = String.valueOf(file.getAbsolutePath()) + " doesn't be deleted!";
            throw new RuntimeException(strError);
        }
    }

    public static boolean deleteDependon(File file) {
        return deleteDependon(file, 0);
    }

    public static boolean deleteDependon(File file, int iMax) {
        int i = 1;
        if (iMax < 1) {
            iMax = 5;
        }
        boolean ret = false;
        while (i < iMax) {
            if (file == null || i > iMax || !file.isFile() || !file.exists()) {
                return ret;
            } else {
                ret = file.delete();
            }
            i++;
        }
        return ret;
    }

    // 确保文件存在
    public static void makesureFileExist(File file) {
        if (!file.exists()) {
            makesureParentExist(file);
            createNewFile(file);
        }
    }

    public static void makesureFileExist(String strFileName) {
        makesureFileExist(new File(strFileName));
    }

    // 确保文件路径存在
    public static void makesureDirExist(File file) {
        if (!file.exists()) {
            makesureParentExist(file);
            file.mkdir();
        }
    }

    public static void makesureDirExist(String strFileName) {
        makesureDirExist(new File(strFileName));
    }

    // 确保文件上级目录存在
    public static void makesureParentExist(File paramFile) {
        File file = paramFile.getParentFile();
        if (file != null && !file.exists()) {
            mkdirs(file);
        }
    }

    public static void makesureParentExist(String paramString) {
        makesureParentExist(new File(paramString));
    }

    // 创建文件目录
    public static void mkdirs(File file) {
        if (!file.exists() && !file.mkdirs()) {
            String strError = "Fail to make " + file.getAbsolutePath();
            Log.e("mkdirs", "file:" + file.getAbsolutePath() + "===" + "strError:" + strError);
        }
    }

    // 检查文件是否存在
    public static boolean hasExists(File f) {
        if (f != null && f.exists()) {
            return true;
        }
        return false;
    }

    public static boolean hasExists(String strFileName) {
        if (strFileName == null || strFileName == "") {
            return false;
        } else {
            return hasExists(new File(strFileName));
        }
    }

    public static void deleteDir(String dirPath) {
        try {
            clearDir(dirPath);
            String filePath = dirPath;
            filePath = filePath.toString();
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 清理目录下所有文件
    public static boolean clearDir(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    clearDir(path + File.separator + tempList[i]);// 清理目录下的文件
                    deleteDir(path + File.separator + tempList[i]);// 删除目录
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 换算文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = String.format("%1$.2f", (double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = String.format("%1$.2f", (double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = String.format("%1$.2f", (double) fileS / 1048576) + "MB";
            // fileSizeString = String.format("%1$.2f", (3 + new
            // Random().nextFloat())) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
            // fileSizeString = String.format("%1$.2f", (3 + new
            // Random().nextFloat())) + "MB";
        }
        return fileSizeString;
    }

    /**
     * 获取目录下文件大小
     *
     * @param dir
     * @return
     */
    //    public static long getDirSize(File dir) {
    //        if (dir == null) {
    //            return 0;
    //        }
    //        if (!dir.isDirectory()) {
    //            return 0;
    //        }
    //        long dirSize = 0;
    //        File[] files = dir.listFiles();
    //        for (File file : files) {
    //            if (file.isFile()) {
    //                dirSize += file.length();
    //            } else if (file.isDirectory()) {
    //                dirSize += file.length();
    //                dirSize += getDirSize(file); // 递归计算
    //            }
    //        }
    //        return dirSize;
    //    }

    /**
     * 判断文件是否:drawable://开头。 如果是直接从drawable读取。
     *
     * @param drawable
     * @return
     */
    public static String getDrawableName(String drawable) {
        if (StringUtils.isEmpty(drawable)) {
            return "";
        }

        if (drawable.indexOf(DRAWABLE) == 0) {
            return getNameFromDrawable(drawable);
        }
        return "";
    }

    /**
     * 从drawable://mrtp_icon 中获取mrtp_icon
     *
     * @param drawable
     * @return
     */
    private static String getNameFromDrawable(String drawable) {
        int start = DRAWABLE.length();
        return drawable.substring(start);
    }

    /**
     * 获取全路径
     *
     * @param url 支持:./img.png /img.png http://www.baidu.com/img.png
     * @return
     */
    public static String getHttpUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            return "";
        }

        String tempUrl = url.replace("\\", "/");// 服务部署在windows上.则文件分隔符是\.所以要将\
        // 替换成/

        if (tempUrl.indexOf(HTTP) == 0) {// http开头不需要做任何处理
            return tempUrl;
        }

        // 需要加入绝对路径后返回，待处理 mdf by lulei3 at 20140909
        return tempUrl;
    }

    public static InputStream getInputStream(Context context, String filepath) throws FileNotFoundException, IOException {
        InputStream is = null;
        if (filepath.startsWith(ASSETS_FILE_PREFIX)) {
            is = context.getAssets().open(filepath.substring(ASSETS_FILE_PREFIX.length()));
        } else {
            is = new FileInputStream(filepath);
        }
        return is;
    }

    /**
     * 获取文件的字节数组
     *
     * @param context
     * @param filepath
     * @return
     */
    public static byte[] getBytesFromPath(Context context, String filepath) {
        InputStream iStream = null;
        ByteArrayOutputStream baos = null;
        byte[] result = null;
        try {
            iStream = getInputStream(context, filepath);
            baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            int read;

            while (-1 != (read = iStream.read(buffer))) {
                baos.write(buffer, 0, read);
            }

            buffer = null;

            result = baos.toByteArray();
            return result;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                if (iStream != null) {
                    iStream.close();
                    iStream = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                if (baos != null) {
                    baos.close();
                    baos = null;
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 返回绝对路径
     *
     * @param file
     * @return
     */
    public static String getPath(File file) {
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 返回绝对路径 如果不存在，也不会创建
     *
     * @param dir
     * @param filename
     * @return
     */
    public static String getPath(File dir, String filename) {
        File file = new File(dir, filename);
        return file.getAbsolutePath();
    }

    /**
     * 根据uri来取得文件的路径
     *
     * @param context
     * @param uri
     * @return
     */
    //    public static String getPath(Context context, Uri uri) {
    //
    //        if ("content".equalsIgnoreCase(uri.getScheme())) {
    //            String[] projection = {"_data"};
    //            Cursor cursor = null;
    //
    //            try {
    //                cursor = context.getContentResolver().query(uri, projection,
    //                        null, null, null);
    //                int column_index = cursor.getColumnIndexOrThrow("_data");
    //                if (cursor.moveToFirst()) {
    //                    return cursor.getString(column_index);
    //                }
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
    //            return uri.getPath();
    //        }
    //
    //        return null;
    //    }


    /**
     * 创建目录
     *
     * @param dir  主目录
     * @param dest 需要创建的子目录
     * @return
     */
    public static File createDirectory(File dir, String dest) {
        File result = new File(dir, dest);
        if (!result.exists()) {
            result.mkdirs();
        }
        return result;
    }

    /**
     * 创建目录
     *
     * @param dirpath 绝对路径
     * @return
     */
    public static File createDirectory(String dirpath) {
        File dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 创建文件所在的目录 不创建文件本身
     *
     * @param filepath 绝对路径 /sdcard/hik/xxxxxx.txt
     * @return
     */
    public static File createParentDirectory(String filepath) {
        File file = new File(filepath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件所在的目录 不创建文件本身
     *
     * @param file
     * @return
     */
    public static File createParentDirectory(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param dir  目录 绝对路径。如:/sdcard/hik
     * @param name
     * @return
     * @throws IOException 创建失败,抛出异常 文件
     */
    public static File createFile(File dir, String name, boolean delete) throws IOException {
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        File result = new File(dir, name);
        if (result.exists() && delete) {
            result.delete();
        }
        result.createNewFile();
        return result;
    }

    /**
     * @param filepath 目录+文件.绝对文件路径。如:/sdcard/hik/error/XXXXXXXX.txt
     * @param delete   如果已经在在，是否需要删除后重新创建
     * @return
     */
    public static File createFile(String filepath, boolean delete) throws IOException {
        File file = new File(filepath);
        File parentDir = file.getParentFile();
        String name = FilenameUtils.getName(filepath);
        return createFile(parentDir, name, delete);
    }

    /**
     * 重新命名
     *
     * @param src
     * @param dest
     */
    public static void moveFile(String src, String dest) {
        try {
            moveFile(new File(src), new File(dest));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 重新命名
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void moveFile(File src, File dest) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
        if (!src.isDirectory()) {
            throw new IOException("Source '" + src + "' is not a directory");
        }
        if (dest.exists()) {
            throw new IllegalArgumentException("Destination '" + dest + "' already exists");
        }
        boolean rename = src.renameTo(dest);
        if (!rename) {
            throw new IllegalArgumentException("move failed");
        }
    }

    public static String getFileName(String FileUrl) {
        String[] nameArr = FileUrl.split("/");
        int length = nameArr.length;
        return nameArr[length - 1];
    }

    /**
     * 判断即将下载的文件是否存在
     *
     * @param fileUrl   网络文件的url
     * @param localPath 本地文件存放的路径
     * @return
     */
    public static boolean downFileExist(String fileUrl, File localPath) {
        Log.e("FileUtils", localPath.getAbsolutePath() + File.separator + getFileName(fileUrl));
        Log.e("FileUtils-2", localPath + File.separator + getFileName(fileUrl));
        return fileExist(localPath, getFileName(fileUrl));
    }

    /**
     * 判断文件是否存在
     *
     * @param filepath 绝对路径
     * @return
     */

    public static boolean fileExist(String filepath) {
        File file = new File(filepath);
        return fileExist(file);
    }

    public static boolean fileExist(File file) {
        if (file != null && file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param dir
     * @param filename
     * @return
     */
    public static boolean fileExist(File dir, String filename) {
        File file = new File(dir, filename);
        return file.exists();
    }

    /**
     * 删除目录或者文件
     *
     * @param filepath
     * @return
     */
    public static boolean deleteQuietly(String filepath) {
        File file = new File(filepath);
        return deleteQuietly(file);
    }

    /**
     * @param dir      父目录
     * @param filepath 文件名称
     * @return
     */
    public static boolean deleteQuietly(String dir, String filepath) {
        File file = new File(dir, filepath);
        return deleteQuietly(file);
    }

    /**
     * 删除目录或者文件。 目录则递归删除， 文件直接删除 不抛出异常
     *
     * @param file
     * @return
     */
    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception ignored) {
        }

        try {
            return file.delete();
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 删除目录中的文件及目录 支持递归删除 删除目录本身 抛出异常
     *
     * @param directory
     * @throws IOException
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        if (!isSymlink(directory)) {// 不是符号链接的，递归删除
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * 递归删除目录中的目录或者文件 不删除目录本身 抛出异常
     *
     * @param directory
     * @throws IOException
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * 删除目录或者文件 支持递归删除 删除目录本身
     *
     * @param file
     * @throws IOException
     */
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    /**
     * 是否符号链接判断
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (FilenameUtils.isSystemWindows()) {
            return false;
        }
        File fileInCanonicalDir = null;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }

        if (fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile())) {
            return false;
        } else {
            return true;
        }
    }

}
