package com.tohabit.skip.utils.blue.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jingning on 2019/4/4 0004 11:17
 * E-Mail Address：jingning1101@163.com
 * 返回的pointdata数据解析
 */
public class BleData {

    private int frameLength;//一个数据的长度

    private byte[] head = new byte[2];

    private byte[] mData;//总数据

    private int index = 0;

    private List<BlePoint> blePointList = new ArrayList<BlePoint>();//解析出来的点

    public BleData(byte[] data, int frameLength) {
        //把头截取出来
        System.arraycopy(data, 0, head, 0, head.length);

        mData = new byte[data.length - head.length];
        System.arraycopy(data, head.length, mData, 0, mData.length);

        this.frameLength = frameLength;

        try {
            byte[] temp = new byte[frameLength];
            for (int i = 0; i < mData.length / frameLength; i++) {
                System.arraycopy(mData, index, temp, 0, temp.length);
                BlePoint blePoint = new BlePoint(temp);
                blePointList.add(blePoint);
                index += frameLength;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 是不是最后一包数据
     *
     * @return
     */
    public boolean isLastPage() {
        byte[] last = new byte[]{(byte) 0xFF, (byte) 0xFF};
        return Arrays.equals(head, last) ? true : false;
    }

    public List<BlePoint> getBlePointList() {
        return blePointList;
    }
}
