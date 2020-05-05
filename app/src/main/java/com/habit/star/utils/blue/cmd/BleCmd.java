package com.habit.star.utils.blue.cmd;

import android.support.annotation.NonNull;
import android.util.Log;

import com.habit.star.utils.blue.HexDump;

import java.nio.ByteBuffer;

/**
 * Created by jingning on 2019/4/1 0001 15:32
 * E-Mail Address：jingning1101@163.com
 * 基础的蓝牙指令构建
 */
public class BleCmd {

    private static final String TAG = "BleCmd";

    private byte[] cmdBytes;

    private Builder mBuilder;

    public byte[] getCmdByte() {
        return cmdBytes;
    }

    public void setCmdBytes(byte[] cmdBytes) {
        this.cmdBytes = cmdBytes;
    }

    BleCmd(Builder builder) {
        this.cmdBytes = builder.buildCmds();
        this.mBuilder = builder;
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    @Override
    public String toString() {
        return cmdBytes == null ? "null" : HexDump.toHexString(cmdBytes);
    }


    public static class Builder {

        private byte headCmd = CmdConstans.HEAD_CMD;//指令开始标识
        private byte dataLength;//数据长度
        private byte[] typeCmd;//指令接口
        private byte[] dataBody;//请求数据体
        private byte result; //请求返回时判断，请求时是dataBody返回时是result
        private byte xor;//异或校验

        private ByteBuffer mBuffer;

        public Builder setBuilder(byte[] data) {
            if (data == null) return null;
            int length = data.length;
            if (length < 5) return null;
            headCmd = data[0];
            dataLength = data[1];
            typeCmd = new byte[2];
            dataBody = new byte[length - 5];
            System.arraycopy(data, 2, typeCmd, 0, typeCmd.length);
            System.arraycopy(data, 4, dataBody, 0, length - 5);
            result = data[length - 2];
            xor = data[length - 1];
            return this;
        }

        public Builder setTypeCmd(byte[] typeCmd) {
            this.typeCmd = typeCmd;
            return this;
        }

        public byte getResult() {
            return result;
        }

        public byte[] getTypeCmd() {
            return typeCmd;
        }

        public Builder setDataBody(byte[] dataBody) {
            this.dataBody = dataBody;
            return this;
        }

        public byte[] getDataBody() {
            return dataBody;
        }

        public byte getDataLength() {
            return dataLength;
        }

        public BleCmd build() {
            BleCmd cmd = new BleCmd(this);
            Log.d(TAG, String.format("create cmd=%s", cmd.toString()));
            return cmd;
        }

        protected byte[] buildCmds() {
            int bodyLength = dataBody == null ? 0 : dataBody.length;
            dataLength = HexDump.shortToByte((short) (typeCmd.length + bodyLength));
            mBuffer = ByteBuffer.allocate(1 + 1 + typeCmd.length + bodyLength + 1);
            mBuffer.put(headCmd);
            mBuffer.put(dataLength);
            if (typeCmd != null) {
                mBuffer.put(typeCmd);
            }
            if (dataBody != null) {
                mBuffer.put(dataBody);
            }
            mBuffer.put(getXor(mBuffer.array()));
            byte[] cmds = mBuffer.array();
            mBuffer = null;
            return cmds;
        }
    }

    public static class CmdTypeBuilder {
        private byte action; //get set ret call echo
        private byte type; //时间，电量，次数

        public static final int TYPE_CMD_LENGTH = 2;

        private ByteBuffer mBuffer;

        public CmdTypeBuilder() {
            mBuffer = ByteBuffer.allocate(TYPE_CMD_LENGTH);
        }

        public CmdTypeBuilder setType(@NonNull byte action, @NonNull byte type) {
            mBuffer.put(action);
            mBuffer.put(type);
            return this;
        }

        public byte[] build() {
            byte[] bodyBs = new byte[mBuffer.position()];
            mBuffer.flip();
            mBuffer.get(bodyBs);
            mBuffer = null;
            return bodyBs;
        }
    }

    /**
     * 异或校验
     *
     * @param data
     * @return
     */
    private static byte getXor(byte[] data) {
        byte temp = 0x00;

        for (int i = 0; i < data.length; i++) {
            temp += data[i] ^ i;
        }
        return temp;
    }

    /**
     * 指令常量
     */
    public static class CmdConstans {

        /**
         * 帧头
         */
        public static final byte HEAD_CMD = 0x23;

        /**
         * 用于设置参数，发送该类型接口需要等待应答
         */
        public static final byte SET_CMD = 0x01;

        /**
         * 用于请求参数，发送该类型接口需要等待应答
         */
        public static final byte GET_CMD = 0x02;

        /**
         * 响应参数
         */
        public static final byte RESPONSE_CMD = 0x04;

        /**
         * 执行某些操作，发送该类型接口需要等待应答
         */
        public static final byte CALL_CMD = 0x08;
        /**
         * 通知，发送该类型接口不需要等待应答
         */
        public static final byte ECHO_CMD = 0x10;

        /**
         * 请求返回时间指令
         */
        public static final byte RETIME_CMD = 0x01;
        /**
         * 时间指令
         */
        public static final byte TIME_CMD = 0x02;

        /**
         * 电量指令
         */
        public static final byte ELECTRIC_QUANTITY_CMD = 0x03;

        /**
         * 当天跳绳累计次数
         */
        public static final byte ROPE_TODAY_FREQUENCY_CMD = 0x04;

        /**
         * 所有运动目录
         */
        public static final byte ALL_SPORT_RECORD_CMD = (byte) 0xF0;

        /**
         * 某次的运动详情
         */
        public static final byte ONECE_FREQUENCY_CMD = (byte) 0xF1;

        /**
         * 运动轨迹坐标点
         */
        public static final byte SPORT_POINT_CMD = (byte) 0xF2;

        /**
         * 删除某次运动
         */
        public static final byte DELETE_SPORT_CMD = (byte) 0xF3;

        /**
         * 批量删除运动
         */
        public static final byte BAT_DELETE_SPORT_CMD = (byte) 0xF5;


        /**
         * 操作成功
         */
        public static final byte SUCCESS_CMD = 0x00;

    }


}
