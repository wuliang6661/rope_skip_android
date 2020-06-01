package com.tohabit.skip.utils.blue;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clone of Android's HexDump class, for use in debugging. Cosmetic changes
 * only.十六进制数据操作
 */
public class HexDump {
    private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     * 转换为字符串
     */
    public static String dumpHexString(byte[] array) {
        return dumpHexString(array, 0, array.length);
    }

    /**
     * 转换为字符串
     */
    public static String dumpHexString(byte[] array, int offset, int length) {
        return bytesToHexFun1(array, offset, length);
    }

    public static short bytesToShort(byte[] bytes){
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public static short bytesToShort(byte[] bytes, ByteOrder byteOrder){
        return ByteBuffer.wrap(bytes).order(byteOrder).getShort();
    }

    public static int bytesToInt(byte[] bytes, ByteOrder byteOrder){
        return ByteBuffer.wrap(bytes).order(byteOrder).getInt();
    }

    public static long bytesToLong(byte[] bytes, ByteOrder byteOrder){
        return bytesToInt(bytes, byteOrder);
    }

    /**
     * 格式化数组输出
     */
    public static String toHexString(byte b) {
        return toHexString(toByteArray(b));
    }

    /**
     * 格式化数组输出
     */
    public static String toHexString(byte[] array) {
        return toHexString(array, 0, array.length);
    }

    /**
     * 格式化数组输出
     */
    public static String toHexString(byte[] array, int offset, int length) {
        return bytesToHexFun2(array, offset, length);
    }

    private static String byteToHexFun3(byte[] array, int offset, int length) {
        StringBuilder result = new StringBuilder();

        byte[] line = new byte[16];
        int lineIndex = 0;

        result.append("0x");
        result.append(toHexString(offset));

        for (int i = offset; i < offset + length; i++) {
            if (lineIndex == 16) {
                result.append(" ");

                for (int j = 0; j < 16; j++) {
                    if (line[j] > ' ' && line[j] < '~') {
                        result.append(new String(line, j, 1));
                    } else {
                        result.append(".");
                    }
                }

                result.append("\n0x");
                result.append(toHexString(i));
                lineIndex = 0;
            }

            byte b = array[i];
            result.append(" ");
            result.append(HEX_DIGITS[(b >>> 4) & 0x0F]);
            result.append(HEX_DIGITS[b & 0x0F]);

            line[lineIndex++] = b;
        }

        if (lineIndex != 16) {
            int count = (16 - lineIndex) * 3;
            count++;
            for (int i = 0; i < count; i++) {
                result.append(" ");
            }

            for (int i = 0; i < lineIndex; i++) {
                if (line[i] > ' ' && line[i] < '~') {
                    result.append(new String(line, i, 1));
                } else {
                    result.append(".");
                }
            }
        }

        return result.toString();
    }

    private static String bytesToHexFun1(byte[] array, int offset, int length) {
        char[] buf = new char[length * 2];

        int bufIndex = 0;
        for (int i = offset; i < offset + length; i++) {
            byte b = array[i];
            buf[bufIndex++] = HEX_DIGITS[(b >>> 4) & 0x0F];
            buf[bufIndex++] = HEX_DIGITS[b & 0x0F];
        }

        return new String(buf);
    }

    /**
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexFun2(byte[] bytes, int offset, int length) {
        StringBuffer buf = new StringBuffer();
        buf.append("[ ");
        for (int i = offset; i < offset + length; i++) {
            byte b = bytes[i];
            buf.append(String.format("%02x ", new Integer(b & 0xff)).toUpperCase());
            if ((i - offset + 1) % 4 == 0) {
                buf.append(" ");
            }
        }
        buf.append("]");
        return buf.toString();
    }

    public static String toHexString(int i) {
        return toHexString(toByteArray(i));
    }

    public static String toHexString(short i) {
        return toHexString(toByteArray(i));
    }

    public static byte[] toByteArray(byte b) {
        byte[] array = new byte[1];
        array[0] = b;
        return array;
    }

    public static byte[] toByteArray(int i) {
        byte[] array = new byte[4];

        array[3] = (byte) (i & 0xFF);
        array[2] = (byte) ((i >> 8) & 0xFF);
        array[1] = (byte) ((i >> 16) & 0xFF);
        array[0] = (byte) ((i >> 24) & 0xFF);

        return array;
    }

    /**
     * 高位在前，低位在后
     * @param value
     * @return
     */
    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 低位在前，高位在后
     * @param value
     * @return
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] shortToByteArray(short s) {
        byte[] array = new byte[]{(byte) s};
        return array;
    }

    public static byte shortToByte(short s) {
        return (byte) s;
    }

    public static byte intToByte(int i) {
        byte b = (byte) i;
        return b;
    }

    public static byte[] toByteArray(short i) {
        byte[] array = new byte[2];

        array[1] = (byte) (i & 0xFF);
        array[0] = (byte) ((i >> 8) & 0xFF);

        return array;
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9')
            return (c - '0');
        if (c >= 'A' && c <= 'F')
            return (c - 'A' + 10);
        if (c >= 'a' && c <= 'f')
            return (c - 'a' + 10);

        throw new RuntimeException("Invalid hex char '" + c + "'");
    }

    public static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] buffer = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            buffer[i / 2] = (byte) ((toByte(hexString.charAt(i)) << 4) | toByte(hexString
                    .charAt(i + 1)));
        }
        return buffer;
    }

    /**
     * 手机号码验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1[3|4|5|8][0-9]\\d{8}$");

        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * md5加密方法
     *
     * @param info 密码源
     * @return
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 异或和
     */

    private static final char[] bcdLookup = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    public static final int MESSAGE_DEVICE_NAME = 4;


    public static String XORAnd(byte[] bytes) {
        int str = 0;
        for (int i = 0; i < bytes.length; i++) {
            str ^= Integer.valueOf((bcdLookup[(bytes[i] >>> MESSAGE_DEVICE_NAME) & 15] + "" + bcdLookup[bytes[i] & 15] + ""), 16);
        }
        return Integer.toHexString(str);
    }

    /**
     * 比较两个byte[]时候一致
     */
    public static boolean bytesEquals(byte[] bs1, byte[] bs2) {
        if (bs1 == null || bs2 == null || bs1.length != bs2.length) return false;
        if (bs1.length == 0 && bs1.length == bs2.length) return true;
        for (int i = 0; i < bs1.length; i++) {
            if (bs1[i] != bs2[i]) return false;
        }
        return true;
    }

    public static boolean bytesStartWith(byte[] fromBytes, byte[] toBytes) {
        if (fromBytes == null || toBytes == null || fromBytes.length < toBytes.length) return false;
        for (int i = 0; i < toBytes.length; i++) {
            if (fromBytes[i] != toBytes[i]) return false;
        }
        return true;
    }

    public static boolean bytesEndWith(byte[] fromBytes, byte[] toBytes) {
        if (fromBytes == null || toBytes == null || fromBytes.length < toBytes.length) return false;
        for (int i = 1; i <= toBytes.length; i++) {
            if (fromBytes[fromBytes.length - i] != toBytes[toBytes.length - i]) return false;
        }
        return true;
    }

    /**
     * 获取高四位
     */
    public static int getHeight4(byte data) {
        int height;
        height = ((data & 0xf0) >> 4);
        return height;
    }

    /**
     * 获取低四位
     */
    public static int getLow4(byte data) {
        int low;
        low = (data & 0x0f);
        return low;
    }
}
