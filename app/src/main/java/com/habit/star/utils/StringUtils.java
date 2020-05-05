/******************************************************************
 系统名称: 海康威视移动办公系统
 类  名  称: StringUtils.java
 软件版权: 杭州海康威视数字技术股份有限公司
 系统版本: 1.0
 开发人员: lulei3
 开发时间: 2014-9-9
 功能说明: 字符串处理通用类
 审核人员:
 相关文档:
 修改记录: 需求编号         修改日期         修改人员                     修改说明

 ******************************************************************/
package com.habit.star.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import net.sourceforge.pinyin4j.PinyinHelper;

public class StringUtils {

    private static final String PRE_FIX_UTF = "&#x";
    private static final String POS_FIX_UTF = ";";

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        String str = null;
        if (cs != null) {
            str = cs.toString();
        }
        return isNotEmpty(str);
    }

    public static int length(String str) {
        if (str != null) {
            return str.length();
        } else {
            return 0;
        }
    }

    /**
     * 前后空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(CharSequence cs) {
        String str = null;
        if (cs != null) {
            str = cs.toString();
        }
        return isEmpty(str);
    }

    /**
     * 获取带有颜色的字
     * @param spanStr 想要加颜色的字符串
     * @param text 包含了想加颜色的字符串的文字
     * @return
     */
    public static SpannableStringBuilder getSpanable(int color,String spanStr, String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.clear();
        builder.append(text);
        builder.setSpan(new ForegroundColorSpan(color), text.indexOf(spanStr), text.indexOf(spanStr) + spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static boolean compareTwoStr(String leftStr, String rightStr) {
        if (null != leftStr && null != rightStr) {
            if (!leftStr.equals(rightStr)) {
                return false;
            } else {
                return true;
            }
        } else if (null == leftStr && null == rightStr) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(Collection c) {
        return c != null && c.size() > 0;
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.size() == 0;
    }

    /**
     * 设置日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String buildDate(int year, int month, int day) {
        String monthTemp = month < 10 ? "0" + month : String.valueOf(month);
        String dayTemp = day < 10 ? "0" + day : String.valueOf(day);
        return year + monthTemp + dayTemp;
    }

    /**
     * 时分String处理
     *
     * @param str
     * @return
     */
    public static String stringToStringBuffer(String str) {
        try {
            StringBuffer stringBuffer = new StringBuffer(str);
            stringBuffer.insert(2, ":");
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00";
        }
    }

    /**
     * 在flowMenu里onclick 的url带参数的特殊处理
     *
     * @param path
     * @param id
     * @param type
     * @return
     */
    public static String modifyGotoPath(String path, String id, String type) {
        String newPath;
        int index = path.indexOf("%@");
        int endIndex = path.lastIndexOf("%@");
        newPath = path.substring(0, index) + id
                + path.substring(index + 2, endIndex) + type
                + path.substring(endIndex + 2);
        return newPath;
    }

    public static String transform(String content) {
        if (null == content || "".equals((content))) {
            return content;
        }
        content = content.replaceAll("&", "&amp;");
        content = content.replaceAll("<", "&lt;");
        content = content.replaceAll("\"", "&quot;");
        // content = content.replaceAll(" ", "&nbsp;");
        content = content.replaceAll(">", "&gt;");
        // content = content.replaceAll( "“", "&ldquo;");
        // content = content.replaceAll( "”", "&rdquo;");
        // content = content.replaceAll( "—", "&mdash;");
        // content = content.replaceAll("\n", "<br>");
        return content;
    }

    /**
     * 规范化数据库表名称
     */
    public static String stdTbName(String content) {
        if (null == content || "".equals((content))) {
            return content;
        }
        content = content.replaceAll("\\.", "_");
        return content;
    }

    /**
     * 规范化手机号码,使用HashSet避免重复手机号码
     */
    public static HashSet<String> splitMobile(String mobile) {
        HashSet<String> hashSet = new HashSet<String>();
        if (null == mobile || "".equals((mobile))) {
            return hashSet;
        }
        String formatMobile = mobile.replaceAll("[\\/\\\\]", "\\|");
        String[] phones = formatMobile.split("\\|");
        if (phones != null && phones.length > 0) {
            for (String phone : phones) {
                hashSet.add(phone);
            }
        }
        return hashSet;
    }

    public static String join(Collection c, char separator) {
        if (c == null) {
            return "";
        }
        return join(c, separator, 0, c.size());
    }

    /**
     * 将集合转换成字符串，格式如下: ["a","b","c","d"] 转换后如下 a,b,c,d 分隔符由参数自定义
     *
     * @param c
     * @param separator
     * @return
     */
    public static String join(Collection c, char separator, int startIndex,
                              int endIndex) {
        if (c == null) {
            return "";
        }
        Object[] array = c.toArray();
        if (array == null) {
            return "";
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 字符串是否包含指定字符串
     *
     * @param content 内容字符串
     * @param thisStr 指定字符串
     * @return
     */
    public static Boolean isContainerThisString(String content, String thisStr) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(thisStr)) {
            return false;
        } else {
            return content.contains(thisStr);
        }
    }

    /**
     * 判断字符串从索引0开始是否包含指定字符
     *
     * @param content 内容字符串
     * @param thisStr 指定字符串
     * @return
     */
    public static Boolean isContainerThisCharByInitials(String content,
                                                        String thisStr) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(thisStr)) {
            return false;
        } else {
            int chillen = thisStr.length();
            if (chillen > content.length()) {
                return false;
            } else {
                String childStr = content.substring(0, chillen);
                if (childStr.equalsIgnoreCase(thisStr)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * 判断字符串結尾是否包含指定字符
     *
     * @param content 内容字符串
     * @param thisStr 指定字符串
     * @return
     */
    public static Boolean isContainerThisCharByEnd(String content,
                                                   String thisStr) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(thisStr)) {
            return false;
        } else {
            int chillen = thisStr.length();
            if (chillen > content.length()) {
                return false;
            } else {
                int startIdx = content.length() - chillen;
                String childStr = content.substring(startIdx);
                if (childStr.equalsIgnoreCase(thisStr)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * Translate charset encoding to unicode
     *
     * @param sTemp charset encoding is gb2312
     * @return charset encoding is unicode
     */
    public static String xmlFormalize(String sTemp) {
        StringBuffer sb = new StringBuffer();

        if (sTemp == null || sTemp.equals("")) {
            return "";
        }
        String s = transEncode2GBK(sTemp);
        for (int i = 0; i < s.length(); i++) {
            char cChar = s.charAt(i);
            if (isGB2312(cChar)) {
                sb.append(PRE_FIX_UTF);
                sb.append(Integer.toHexString(cChar));
                sb.append(POS_FIX_UTF);
            } else {
                switch ((int) cChar) {
                    case 32:
                        sb.append("&#32;");
                        break;
                    case 34:
                        sb.append("&quot;");
                        break;
                    case 38:
                        sb.append("&amp;");
                        break;
                    case 60:
                        sb.append("&lt;");
                        break;
                    case 62:
                        sb.append("&gt;");
                        break;
                    default:
                        sb.append(cChar);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串编码格式转成GB2312
     *
     * @param str
     * @return
     */
    public static String transEncode2GBK(String str) {
        try {
            String strEncode = getEncoding(str);
            String temp = new String(str.getBytes(strEncode), "GBK");
            return temp;
        } catch (java.io.IOException ex) {

            return null;
        }
    }

    /**
     * 判断输入字符是否为gb2312的编码格式
     *
     * @param c 输入字符
     * @return 如果是gb2312返回真，否则返回假
     */
    public static boolean isGB2312(char c) {
        Character ch = Character.valueOf(c);
        String sCh = ch.toString();
        try {
            byte[] bb = sCh.getBytes("gb2312");
            if (bb.length > 1) {
                return true;
            }
        } catch (java.io.UnsupportedEncodingException ex) {
            return false;
        }
        return false;
    }


    /**
     * {简述，保留点号}.
     * <p>
     * {判定是否为数字、字母、下划线}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     *
     * @param mobiles
     * @return
     * @author zhanxinchao 2015-9-30 下午2:34:40
     * @modify {上次修改原因} by zhanxinchao 2015-9-30 下午2:34:40
     */
    public static boolean isFigureOrLetterOrUnderlineNo(String mobiles) {
        Pattern p = Pattern.compile("^[0-9a-zA-Z_]+$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    /**
     * 分隔符：“,”
     *
     * @param resource
     * @return
     */
    public static String[] splite(String resource){
        if(resource.equals("")){
            return null;
        }
        String[] results = resource.split("[,]");
        return results;
    }

    public static final String EN_PART_ONE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzO0xy";

    public static final String EN_PART_TOW = "Zla7Y";

    public static final String EN_PART_THREE = "xmggWEWeMZq0B68oj+";

    public static final String EN_PART_FOUR = "HMRrT41bgKnyc3wG4oDLlm68QKJEgwazVZgmiV9wIPjFc+KoxSmtljn5ENh8u7TWOlB69jVnokNqlJ2";

    public static final String EN_PART_FIVE = "2FRVY5XLxruwcPlCMAvWv75nwCdgasrKHfzlwiy8GZyIS6u+NxL0DnciX2KcKrr95ihnQNIjb1";

    public static final String EN_PART_SIX = "hVQNh9e0KY4GI3lxOU9qUUvJHYwVh6bmrTQq1PdX9U6gFBJB";

    public static final String EN_PART_SEVEN = "U7zoAc+H4gEkWXwHbPzvHLI1gdyBsIPrjlczSxm2Nc";

    public static final String EN_PART_EIGHT = "7nWarrZFU9O671PAdinTPNsfHSLq03EYRGOASJH+B4hIbs5BA+asK94t11bvqbQIDAQAB";
}
