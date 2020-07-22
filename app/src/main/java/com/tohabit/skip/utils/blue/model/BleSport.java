package com.tohabit.skip.utils.blue.model;


import com.tohabit.skip.utils.blue.HexDump;

import java.nio.ByteOrder;

/**
 * Created by jingning on 2019/4/3 0003 14:46
 * E-Mail Address：jingning1101@163.com
 * 蓝牙传输过来的数据转实体
 */
public class BleSport {

    private static final int DATA_LENGTH = 17;

    private long start_time;//单位为秒
    private long end_time;//单位为秒
    private int length;
    private int frameLength;
    private short frequency;
    private short breakCount;
    byte[] stime;

    public BleSport(byte[] data) {
        if (data.length == DATA_LENGTH) {
            stime = new byte[4];
            System.arraycopy(data, 0, stime, 0, stime.length);
            //先截取开始时间，将开始时间转为字符串，将16进制字符串转化为10进制的long
            start_time = Long.parseLong(HexDump.dumpHexString(stime), 16);

            byte[] etime = new byte[4];
            System.arraycopy(data, stime.length, etime, 0, etime.length);
            end_time = Long.parseLong(HexDump.dumpHexString(etime), 16);

            byte[] blength = new byte[4];
            System.arraycopy(data, stime.length + etime.length, blength, 0, blength.length);
            length = HexDump.bytesToInt(blength, ByteOrder.BIG_ENDIAN);

            frameLength = data[stime.length + etime.length + blength.length];

            byte[] bfrequency = new byte[2];
            System.arraycopy(data, stime.length + etime.length + blength.length + 1, bfrequency, 0, bfrequency.length);
            frequency = HexDump.bytesToShort(bfrequency);

            byte[] bBreak = new byte[2];
            System.arraycopy(data, stime.length + etime.length + blength.length + 1 + bfrequency.length, bBreak, 0, bBreak.length);
            breakCount = HexDump.bytesToShort(bBreak);
        }

    }

    public long getStart_time() {
        return start_time;
    }

    public byte[] getStime() {
        return stime;
    }

    public long getEnd_time() {
        return end_time;
    }

    public int getLength() {
        return length;
    }

    public int getFrameLength() {
        return frameLength;
    }

    public short getFrequency() {
        return frequency;
    }

    public short getBreakCount() {
        return breakCount;
    }

    @Override
    public String toString() {
        return "BleSport{" +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                ", length=" + length +
                ", frameLength=" + frameLength +
                ", frequency=" + frequency +
                ", breakCount=" + breakCount +
                '}';
    }
}
