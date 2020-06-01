package com.tohabit.skip.utils.blue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ByteUtils {


    private static final char[] mChars = "0123456789ABCDEF".toCharArray();

    /**
     * bytes转换成十六进制字符串
     *
     * @param b    byte[] byte数组
     * @param iLen int 取前N位处理 N=iLen
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b, int iLen) {

        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[((b[n] & 0xFF) >> 4)]);
            sb.append(mChars[(b[n] & 0xF)]);
            sb.append(' ');
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }


    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }


    /**
     * 输入流读取数据1
     */
    public static byte[] readStream1(InputStream in) {
        byte[] b = null;
        ByteArrayOutputStream outSteam = null;
        try {
            byte[] buffer = new byte[1024 * 4];
            outSteam = new ByteArrayOutputStream();
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            b = outSteam.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outSteam != null) {
                    outSteam.close();
                    outSteam = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    /**
     * 输入流读取数据2
     */
    public static byte[] readStream(InputStream in) {
        if (in == null) {
            return null;
        }
        byte[] buffer = null;
        try {
            int availableLength = 0;
            while (availableLength == 0) {
                availableLength = in.available();//可获取的字节流长度
                if (availableLength == 0 && in.read() == -1) {//防止读取流返回为空造成死循环
                    break;
                }
            }
            buffer = new byte[availableLength];
            int readCount = 0;
            while (readCount < availableLength) {
                readCount += in.read(buffer, readCount, availableLength - readCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return buffer;
    }
}
