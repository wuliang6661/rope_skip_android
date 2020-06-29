package com.tohabit.skip.utils.blue.cmd;

import android.util.Log;

import com.tohabit.skip.utils.blue.HexDump;

import java.nio.ByteBuffer;

/**
 * Created by jingning on 2019/4/1 0001 16:44
 * E-Mail Address：jingning1101@163.com
 * 所有业务相关的请求指令构建
 */
public class RequstBleCmd {

    /**
     * 同步时间指令
     */
    public static BleCmd createSynTimeCmd() {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.SET_CMD, BleCmd.CmdConstans.TIME_CMD).build();
//        byte[] timeData = HexDump.intToBytes2((int) (System.currentTimeMillis() / 1000));
//        byte[] timeData = DateUtils.formatDeviceYMDHMAsBytes(System.currentTimeMillis());
        long timeSeconds = System.currentTimeMillis() / 1000;
        Log.d("RequstBleCmd", "同步时间： " + timeSeconds);
        byte[] timeData = HexDump.intToBytes2((int) timeSeconds);
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).setDataBody(timeData).build();
        return cmd;
    }

    /**
     * 获取设备时间指令
     *
     * @return
     */
    public static BleCmd createGetTimeCmd() {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.GET_CMD, BleCmd.CmdConstans.TIME_CMD).build();
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).build();
        return cmd;
    }

    /**
     * 获取电量指令
     */
    public static BleCmd createGetEQCmd() {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.GET_CMD, BleCmd.CmdConstans.ELECTRIC_QUANTITY_CMD).build();
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).build();
        return cmd;
    }

    /**
     * 获取当天跳绳次数指令
     */
    public static BleCmd createTodayFrequencyCmd() {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.GET_CMD, BleCmd.CmdConstans.ROPE_TODAY_FREQUENCY_CMD).build();
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).build();
        return cmd;
    }

    /**
     * 获取所有的运动记录目录指令
     */
    public static BleCmd createAllSportRecordCmd() {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.GET_CMD, BleCmd.CmdConstans.ALL_SPORT_RECORD_CMD).build();
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).build();
        return cmd;
    }

    /**
     * 获取某次的运动记录内容
     *
     * @param sportId 从目录获取的次数
     * @return
     */
    public static BleCmd createSportInfoCmd(short sportId) {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.GET_CMD, BleCmd.CmdConstans.ONECE_FREQUENCY_CMD).build();
        byte[] dateCmd = HexDump.toByteArray(sportId);
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).setDataBody(dateCmd).build();
        return cmd;
    }

    /**
     * 获取某次运动的轨迹坐标点
     *
     * @param date 单位为秒
     * @return
     */
    public static BleCmd createGetPointCmd(long date) {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.GET_CMD, BleCmd.CmdConstans.SPORT_POINT_CMD).build();
        byte[] dateData = HexDump.intToBytes2((int) date);
        byte[] noData = new byte[]{0x00, 0x00};
        ByteBuffer byteBuffer = ByteBuffer.allocate(dateData.length + noData.length);
        byteBuffer.put(dateData);
        byteBuffer.put(noData);
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).setDataBody(byteBuffer.array()).build();
        return cmd;
    }

    /**
     * 删除一次运动
     *
     * @param date 单位为秒
     * @return
     */
    public static BleCmd createDeleteSportCmd(long date) {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.CALL_CMD, BleCmd.CmdConstans.DELETE_SPORT_CMD).build();
        byte[] dateData = HexDump.intToBytes2((int) date);
        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).setDataBody(dateData).build();
        return cmd;
    }

    /**
     * 批量删除运动
     *
     * @param date_start 单位为秒
     * @param data_end   单位为秒
     * @return
     */
    public static BleCmd createBatDeleteSportCmd(long date_start, long data_end) {
        byte[] typeCmd = new BleCmd.CmdTypeBuilder().setType(BleCmd.CmdConstans.CALL_CMD, BleCmd.CmdConstans.BAT_DELETE_SPORT_CMD).build();
        byte[] dateStartData = HexDump.intToBytes2((int) date_start);
        byte[] dateEndData = HexDump.intToBytes2((int) data_end);
        byte[] dateData = new byte[dateStartData.length + dateEndData.length];

        System.arraycopy(dateStartData, 0, dateData, 0, dateStartData.length);
        System.arraycopy(dateEndData, 0, dateData, dateStartData.length, dateEndData.length);

        BleCmd cmd = new BleCmd.Builder().setTypeCmd(typeCmd).setDataBody(dateData).build();
        return cmd;
    }
}
