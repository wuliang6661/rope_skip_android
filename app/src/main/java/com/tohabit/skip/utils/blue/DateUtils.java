package com.tohabit.skip.utils.blue;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * 时间工具类
 */
public class DateUtils {

    /**
     * 获取当前时间,返回时间格式为yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getTodayYMD(){
        return createFormatYMD("-","-").format(new Date(System.currentTimeMillis()));
    }


    /**
     * 获取当前时间,返回时间格式为yyyy-MM-dd
     * @return 时间字符串
     */
    public static String formYMD(long date){
        return createFormatYMD("-","-").format(new Date(date));
    }

    /**
     * 字符串转时间,格式化:年(年的后两位)月日时分
     */
    public static Date parseDeviceYMDHM(String dateStr) {
        try {
            return createDeviceYMDHMFormat("", "", "", "").parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串转时间,格式化:年/月/日
     */
    public static @Nullable
    Date parseYMD(String dateStr) {
        try {
            return createFormatYMD("/", "/").parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 转时间:时:分
     */
    public static Date parseHM(String dateStr) {
        try {
            return createFormatHM(":").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转时间,格式化:时:分:秒
     */
    public static @Nullable
    Date parseHMS(String dateStr) {
        try {
            return createFormatHMS(":", ":").parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 格式化:年/月/日
     */
    public static String formatYMD(long date) {
        return createFormatYMD("/", "/").format(new Date(date));
    }

    /**
     * 格式化:时:分
     */
    public static String formatHM(long date) {
        return createFormatHM(":").format(new Date(date));
    }

    /**
     * 格式化:时:分:秒
     */
    public static String formatHMS(long date) {
        return createFormatHMS(":", ":").format(new Date(date));
    }

    /**
     * 创建格式化日期
     *
     * @param delimiterHM 小时和分钟的分隔符
     * @param delimiterMS 分钟和秒钟的分隔符
     */
    public static SimpleDateFormat createFormatHMS(String delimiterHM, String delimiterMS) {
        String format = "HH" + delimiterHM + "mm" + delimiterMS + "ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat;
    }

    /**
     * 创建格式化日期
     *
     * @param delimiterHM 小时和分钟的分隔符
     */
    public static SimpleDateFormat createFormatHM(String delimiterHM) {
        String format = "HH" + delimiterHM + "mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat;
    }

    /**
     * 创建格式化日期
     *
     * @param delimiterYM 年和月的分隔符
     * @param delimiterMD 月和日的分隔符
     */
    public static SimpleDateFormat createFormatYMD(String delimiterYM, String delimiterMD) {
        String format = "yyyy" + delimiterYM + "MM" + delimiterMD + "dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        TimeZone timeZone = TimeZone.getTimeZone("GMT+00:00");
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    /**
     * 创建格式化日期
     *
     * @param delimiterYM 年和月的分隔符
     * @param delimiterMD 月和日的分隔符
     * @param delimiterDH 日和时的分隔符
     * @param delimiterHM 时和分的分隔符
     * @param delimiterMS 分和秒的分隔符
     */
    public static SimpleDateFormat createYMDHMSFormat(String delimiterYM, String delimiterMD, String delimiterDH, String delimiterHM, String delimiterMS) {
        String format = "yyyy" + delimiterYM + "MM" + delimiterMD + "dd" + delimiterDH + "HH" + delimiterHM + "mm" + delimiterMS + "ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        TimeZone timeZone = TimeZone.getTimeZone("GMT+00:00");
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    /**
     * 创建格式化日期 年月日时分
     *
     * @param delimiterYM 年和月的分隔符
     * @param delimiterMD 月和日的分隔符
     * @param delimiterDH 日和时的分隔符
     * @param delimiterHM 时和分的分隔符
     */
    public static SimpleDateFormat createYMDHMFormat(String delimiterYM, String delimiterMD, String delimiterDH, String delimiterHM) {
        String format = "yyyy" + delimiterYM + "MM" + delimiterMD + "dd" + delimiterDH + "HH" + delimiterHM + "mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat;
    }

    /**
     * 格式化:年月日时分秒
     *
     * @param date 时间
     */
    public static String formatDate(long date) {
        return createYMDHMSFormat("", "", "", "", "").format(new Date(date));
    }

    /**
     * 格式化:年-月-日 时:分:秒
     *
     * @param date 时间
     */
    public static String formatLogDate(long date) {
        return createYMDHMSFormat("-", "-", " ", ":", ":").format(new Date(date));
    }

    /**
     * 转换:年-月-日 时:分:秒格式的时间
     *
     * @param dateStr 时间
     */
    public static Date parseLogDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = createYMDHMSFormat("-", "-", " ", ":", ":");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化:年(后两位)月日时分秒
     *
     * @param delimiterYM 年和月的分隔符
     * @param delimiterMD 月和日的分隔符
     * @param delimiterDH 日和时的分隔符
     * @param delimiterHM 时和分的分隔符
     * @param delimiterMS 分和秒的分隔符
     */
    public static SimpleDateFormat createDeviceYMDHMSFormat(String delimiterYM, String delimiterMD, String delimiterDH, String delimiterHM, String delimiterMS) {
        String format = "yy" + delimiterYM + "MM" + delimiterMD + "dd" + delimiterDH + "HH" + delimiterHM + "mm" + delimiterMS + "ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat;
    }

    /**
     * 格式化:年(后两位)月日时分
     *
     * @param delimiterYM 年和月的分隔符
     * @param delimiterMD 月和日的分隔符
     * @param delimiterDH 日和时的分隔符
     * @param delimiterHM 时和分的分隔符
     */
    public static SimpleDateFormat createDeviceYMDHMFormat(String delimiterYM, String delimiterMD, String delimiterDH, String delimiterHM) {
        String format = "yy" + delimiterYM + "MM" + delimiterMD + "dd" + delimiterDH + "HH" + delimiterHM + "mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat;
    }

    /**
     * 格式化::年月日时分
     *
     * @param date 时间
     */
    public static String formatDeviceYMDHMDate(long date) {
        String format = "yyMMddHHmm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(date));
    }

    /**
     * 秒转时分秒
     *
     * @param time 单位s
     * @return HH:mm:ss
     */
    public static String secToTime(int time) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        hour = time / 3600;
        minute = time / 60 % 60;
        second = time % 60;
        if (time <= 0)
            return "00:00:00";
        else {
            return unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }
    }

    /**
     * 將個位數前面加0
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * 格式化指定时间为设备使用的格式并转换为字节数组:年(后两位)月日时分 如:1801010212
     */
    public static byte[] formatDeviceYMDHMAsBytes(long time) {
        byte[] dateBytes;
        if (time <= 0) {
            dateBytes = HexDump.hexStringToByteArray("000000000000");
        } else {
            String spliteStr = ",";
            String dateStr = createDeviceYMDHMSFormat(spliteStr, spliteStr, spliteStr, spliteStr, spliteStr).format(new Date(time));
            String[] splitStrs = dateStr.split(spliteStr);
            dateBytes = new byte[splitStrs.length];
            for (int i = 0; i < splitStrs.length; i++) {
                int dateInt = Integer.parseInt(splitStrs[i],16);
                dateBytes[i] = (byte) dateInt;
            }
        }
        return dateBytes;
    }
}
