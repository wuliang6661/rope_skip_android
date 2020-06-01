package com.tohabit.skip.utils.blue.model;


import com.tohabit.skip.utils.blue.HexDump;

import java.nio.ByteOrder;

/**
 * Created by jingning on 2019/4/4 0004 11:32
 * E-Mail Address：jingning1101@163.com
 */
public class BlePoint {

    private int timestamp;

    private int number;

    private int hallSensor;

    private int accelerationX;
    private int accelerationY;
    private int accelerationZ;

    private int angularVelocityX;
    private int angularVelocityY;
    private int angularVelocityZ;

    private int pitch;
    private int roll;
    private int yaw;

    public BlePoint(byte[] data) {
        if (data.length >= 20) {
            int index = 0;

            byte[] num = new byte[2];
            System.arraycopy(data, index, num, 0, num.length);
            number = HexDump.bytesToShort(num);
            index += 2;

            byte[] baccelerationX = new byte[2];
            System.arraycopy(data, index, baccelerationX, 0, baccelerationX.length);
            accelerationX = HexDump.bytesToShort(baccelerationX);
            index += 2;

            byte[] baccelerationY = new byte[2];
            System.arraycopy(data, index, baccelerationY, 0, baccelerationY.length);
            accelerationY = HexDump.bytesToShort(baccelerationY);
            index += 2;

            byte[] baccelerationZ = new byte[2];
            System.arraycopy(data, index, baccelerationZ, 0, baccelerationZ.length);
            accelerationZ = HexDump.bytesToShort(baccelerationZ);
            index += 2;

            byte[] bangularVelocityX = new byte[2];
            System.arraycopy(data, index, bangularVelocityX, 0, bangularVelocityX.length);
            angularVelocityX = HexDump.bytesToShort(bangularVelocityX);
            index += 2;

            byte[] bangularVelocityY = new byte[2];
            System.arraycopy(data, index, bangularVelocityY, 0, bangularVelocityY.length);
            angularVelocityY = HexDump.bytesToShort(bangularVelocityY);
            index += 2;

            byte[] bangularVelocityZ = new byte[2];
            System.arraycopy(data, index, bangularVelocityZ, 0, bangularVelocityZ.length);
            angularVelocityZ = HexDump.bytesToShort(bangularVelocityZ);
            index += 2;

            byte[] bpitch = new byte[2];
            System.arraycopy(data, index, bpitch, 0, bpitch.length);
            pitch = HexDump.bytesToShort(bpitch);
            index += 2;

            byte[] broll = new byte[2];
            System.arraycopy(data, index, broll, 0, broll.length);
            roll = HexDump.bytesToShort(broll);
            index += 2;

            byte[] byaw = new byte[2];
            System.arraycopy(data, index, byaw, 0, byaw.length);
            yaw = HexDump.bytesToShort(byaw);
            index += 2;

            byte[] btimestamp = new byte[2];
            System.arraycopy(data, index, btimestamp, 0, btimestamp.length);
            timestamp = HexDump.bytesToShort(btimestamp, ByteOrder.BIG_ENDIAN);
            index += 2;

            hallSensor = data[index];
//            index++;
        }
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getNumber() {
        return number;
    }

    public int getHallSensor() {
        return hallSensor;
    }

    public void setHallSensor(int hallSensor) {
        this.hallSensor = hallSensor;
    }

    public int getAccelerationX() {
        return accelerationX;
    }

    public int getAccelerationY() {
        return accelerationY;
    }

    public int getAccelerationZ() {
        return accelerationZ;
    }

    public int getAngularVelocityX() {
        return angularVelocityX;
    }

    public int getAngularVelocityY() {
        return angularVelocityY;
    }

    public int getAngularVelocityZ() {
        return angularVelocityZ;
    }

    public int getPitch() {
        return pitch;
    }

    public int getRoll() {
        return roll;
    }

    public int getYaw() {
        return yaw;
    }

    @Override
    public String toString() {
        return "BlePoint{" +
                "number=" + number +
                ", hallSensor=" + hallSensor +
                ", accelerationX=" + accelerationX +
                ", accelerationY=" + accelerationY +
                ", accelerationZ=" + accelerationZ +
                ", angularVelocityX=" + angularVelocityX +
                ", angularVelocityY=" + angularVelocityY +
                ", angularVelocityZ=" + angularVelocityZ +
                ", pitch=" + pitch +
                ", roll=" + roll +
                ", yaw=" + yaw +
                '}';
    }
}
