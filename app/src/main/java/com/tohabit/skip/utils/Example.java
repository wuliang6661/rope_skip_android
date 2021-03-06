package com.tohabit.skip.utils;

import android.content.res.AssetManager;

import com.algorithm.skipevaluation.Evaluator;
import com.algorithm.skipevaluation.dto.SkipInfo;
import com.algorithm.skipevaluation.dto.SkipUnitData;
import com.tohabit.skip.utils.blue.model.BlePoint;

import java.util.ArrayList;
import java.util.List;

public class Example {

    //采样数据
    private double[][] skipData = new double[][]{
            {140, -5108, 5342, 6521, 5449, -3748, 52.86999893, -0.939999998, -115.9100037, 5635, 1, 30921},
            {-3085, -8429, 3494, 6691, 4603, 3777, 62.74000168, 2.069999933, -108.8099976, 5635, 1, 30947},
            {-1008, -6247, 938, 6640, 3858, 7010, 73.12999725, -3.5, -100.1900024, 5635, 2, 30973},
            {1213, -8222, -3590, 4930, 4114, 9442, 80.80999756, -14.85000038, -91.5, 5635, 2, 30998},
            {906, -13660, -6883, 2536, 3466, 12180, 82.59999847, -31, -82.30000305, 5635, 2, 31024},
            {10628, -15370, -3646, -1031, 2631, 14569, 76.91000366, -52.59999847, -72.54000092, 5635, 3, 31075},
            {11519, -14918, 3799, -4918, 7061, 12561, 38.45000076, -67.48000336, -35.61000061, 5635, 3, 31077},
            {18026, -11053, 5952, -5478, 7387, 11603, -14.82999992, -59.02999878, 13.01000023, 5635, 3, 31100},
            {21538, -6566, -7837, -6826, 7818, 5771, -33.70000076, -44.50999832, 25.71999931, 5635, 3, 31126},
            {9022, -9032, 1685, -12171, 9156, -1810, -44.68999863, -34.33000183, 16.61000061, 5635, 3, 31152},
            {373, -10482, 10410, -10280, 7595, -3897, -54.22000122, -30.25, 1.49000001, 5635, 3, 31177},
            {341, -7046, 7468, -8560, 8419, -2813, -60.58000183, -27.57999992, -13.35000038, 5635, -1, 31203},
            {3907, -3277, 9511, -6697, 9322, -2581, -65.01000214, -24.45999908, -29.12000084, 5635, -1, 31228},
            {5282, -1068, 6555, -4849, 10475, -6319, -66.01999664, -24.47999954, -47.43000031, 5635, -1, 31254},
            {3807, -5736, 12301, -855, 5897, -8988, -61.36999893, -30.61000061, -65.98999786, 5635, -1, 31279},
            {960, -6678, 7696, 3644, 3323, -8500, -48.61999893, -38.18999863, -81.80999756, 5635, -1, 31306},
            {802, -6923, 7254, 5947, 2958, -7409, -30.54999924, -41.72000122, -97.62000275, 5635, -1, 31331},
            {-2300, -8093, 5741, 6667, 2675, -6322, -10.86999989, -40.81000137, -112.7600021, 5635, -1, 31356},
            {-13, -7452, 7221, 8887, 1417, -3787, 6.579999924, -37.97999954, -122.0999985, 5635, -1, 31382},
            {3538, -7334, 6620, 7837, 5112, -2985, 20.73999977, -31.46999931, -126.0800018, 5635, -1, 31408},
            {7296, -7045, 4588, 8674, 4108, -2186, 32.93000031, -24.05999947, -126.0199966, 5635, -1, 31433},
            {5404, -6105, 1709, 7350, 5603, -2602, 43.95000076, -16.29999924, -123.8799973, 5635, -1, 31459},
            {366, -5959, 4630, 6748, 5533, -400, 53.00999832, -9.090000153, -118.7099991, 5635, -1, 31484},
            {1279, -6270, 1569, 5832, 7581, 1823, 60.97999954, -5.019999981, -109.4899979, 5635, -1, 31510},
            {-1395, -7383, 4087, 6123, 7599, 4969, 68.44000244, -4.679999828, -96.18000031, 5636, 1, 31561},
            {-2583, -9145, -2718, 6173, 4482, 8634, 76.19999695, -12.14000034, -84.15000153, 5636, 1, 31563},
            {-394, -10044, -9029, 2460, 1907, 11257, 80.90000153, -27.01000023, -77.13999939, 5636, 1, 31586},
            {5105, -9767, -721, -2037, 4083, 11621, 74.72000122, -44.06000137, -67.47000122, 5636, 2, 31612},
            {3471, -11853, 786, -4349, 6288, 10872, 49.81999969, -55.91999817, -42.77000046, 5636, 2, 31638},
            {6904, -15309, -1044, -6232, 5185, 11992, 8.960000038, -57.52999878, -4.869999886, 5636, 2, 31663},
            {11554, -12310, 3382, -8240, 6220, 10444, -25.17000008, -46.68000031, 22.5, 5636, 2, 31689},
            {9444, -6610, 6836, -7151, 6239, 6704, -41.93000031, -31.80999947, 29.36000061, 5636, 2, 31714},
            {9294, -5297, 1536, -7582, 9062, 1694, -50.93999863, -20.13999939, 24.79000092, 5636, 2, 31740},
            {7916, -7140, 3490, -7234, 10367, -3256, -58.74000168, -13.32999992, 10.51000023, 5636, 2, 31768},
            {5838, -11833, 875, -6453, 9758, -5726, -65.05000305, -13.17000008, -7.269999981, 5636, -1, 31791},
            {7425, -7746, 9496, -5904, 7530, -8413, -70.52999878, -18.85000038, -23.70999908, 5636, -1, 31817},
            {1421, -8962, 6123, -3657, 7961, -11043, -69.51999664, -29.75, -41.74000168, 5636, -1, 31842},
            {-1438, -6917, 9239, -1046, 7118, -10786, -60.88999939, -40.02999878, -62.63999939, 5636, -1, 31870},
            {-2484, -7009, 9506, 2192, 7180, -9462, -42.24000168, -44.75, -87.94000244, 5636, -1, 31893},
            {-911, -6171, 9387, 5528, 4361, -6791, -22.05999947, -43.36000061, -107.9899979, 5636, -2, 31919},
            {426, -7659, 8893, 6976, 2904, -5060, -3.609999895, -40.24000168, -120.5199966, 5636, -2, 31945},
            {2007, -7562, 7331, 7185, 4550, -3001, 10.68000031, -33.72000122, -127.0899963, 5636, -2, 31970},
            {10939, -6629, 8672, 7659, 6506, -1280, 21.54000092, -24.62000084, -126.6399994, 5636, -2, 31996},
            {9084, -7880, 9305, 9776, 5832, -728, 32.95000076, -15.09000015, -123.2200012, 5636, -2, 32021},
            {6580, -9254, 3090, 10036, 6537, -590, 46.77999878, -7.929999828, -117.9599991, 5636, -2, 32047},
            {3443, -6306, -1416, 8057, 6660, 1360, 59.27999878, -2.890000105, -109.1800003, 5636, -2, 32072},
            {-3314, -6841, -147, 6166, 6084, 4951, 69.05999756, -3.079999924, -98.44000244, 5637, 1, 32098},
            {-2749, -10418, -4245, 5108, 3764, 9368, 76.20999908, -11.68999958, -87.72000122, 5637, 1, 32124},
            {3147, -11063, -6981, 2329, 808, 11930, 80.15000153, -28.27000046, -81.66999817, 5637, 1, 32149},
            {4573, -14591, -2602, -2531, 4871, 12630, 72.83000183, -46.47999954, -71.12999725, 5637, 1, 32175},
            {4070, -17244, 1834, -5617, 8050, 12960, 37.93000031, -57.5, -37.18000031, 5637, 1, 32200},
            {8792, -17638, 2033, -7297, 6841, 13505, -7.449999809, -52.40999985, 4.570000172, 5637, 2, 32226},
            {12541, -11761, 7370, -9013, 7800, 10495, -34.70000076, -35.47000122, 23.78000069, 5637, 2, 32251},
            {13944, -6720, 5606, -7929, 8875, 5972, -48.38000107, -18.73999977, 24.59000015, 5637, 2, 32277},
            {9916, -4377, 6699, -6338, 10078, -625, -57.18000031, -7.989999771, 13.71000004, 5637, 2, 32303},
            {5625, -8733, 2704, -6280, 9165, -5154, -64.37999725, -6.269999981, -1.440000057, 5637, -1, 32377},
            {7074, -6164, 11227, -4800, 5648, -8009, -70.91000366, -12.09000015, -15.42000008, 5637, -1, 32383},
            {1921, -7333, 10069, -1767, 6259, -9882, -70.68000031, -22.96999931, -29.29000092, 5637, -1, 32385},
            {1225, -6296, 6141, 696, 5838, -9809, -63.11999893, -32.84000015, -45.84000015, 5637, -1, 32407},
            {4312, -7726, 9443, 2448, 5315, -9841, -49.18000031, -40.59999847, -64.55000305, 5637, -1, 32431},
            {831, -8827, 5837, 4012, 5210, -9193, -28.79999924, -43.09000015, -86.91000366, 5637, -2, 32456},
            {776, -7041, 7619, 4989, 3152, -7348, -9.770000458, -41.13999939, -104.9599991, 5637, -2, 32482},
            {-139, -7543, 11049, 7096, 3242, -5369, 7.070000172, -36.56999969, -116.9700012, 5637, -2, 32507},
            {5230, -7905, 5187, 7227, 4777, -3363, 21.51000023, -29.04999924, -121.2699966, 5637, -2, 32533},
            {3844, -8421, 5035, 9695, 4562, -1594, 33.86000061, -19.82999992, -120.3300018, 5637, -2, 32558},
            {3295, -8904, 2098, 9479, 5201, -723, 46.97000122, -13.72999954, -116.5800018, 5637, -2, 32584},
            {-3797, -9694, 312, 7446, 7063, 846, 57.81999969, -8.199999809, -108.9499969, 5637, -2, 32610},
    };

    public Evaluator evaluator;

    /**
     * 计算模型得分
     *
     * @param assetManager
     * @param breakCount   断绳数量
     * @param circleCount  跳绳数量
     * @param duration     跳绳时间
     */
    public Example(AssetManager assetManager, int breakCount, int circleCount, int duration) {
        SkipInfo skipInfo = new SkipInfo();
        skipInfo.setBreakCount(breakCount);
        skipInfo.setCircleCount(circleCount);
        skipInfo.setDuration(duration);

        List<List<SkipUnitData>> data = new ArrayList<>();
        List<SkipUnitData> circle = new ArrayList<>();
        int currCircle = (int) Math.round(skipData[0][9]);
        int circlenum = currCircle;
        //按圈保存在一个list中
        for (int row = 0; row < skipData.length; row++) {
            currCircle = (int) Math.round(skipData[row][9]);
            if (circlenum != currCircle) {
                circlenum = currCircle;
                data.add(circle);
                circle = new ArrayList<>();
            }
            SkipUnitData unit = new SkipUnitData();
            unit.setAccelerationX(skipData[row][0]);
            unit.setAccelerationY(skipData[row][1]);
            unit.setAccelerationZ(skipData[row][2]);
            unit.setAngularVelocityX(skipData[row][3]);
            unit.setAngularVelocityY(skipData[row][4]);
            unit.setAngularVelocityZ(skipData[row][5]);
            unit.setPitch(skipData[row][6]);
            unit.setRoll(skipData[row][7]);
            unit.setYaw(skipData[row][8]);
            unit.setHallSensor((int) Math.round(skipData[row][10]));
            unit.setTimestamp((int) Math.round(skipData[row][11]));

            circle.add(unit);
        }
        if (circle.size() > 0) {
            data.add(circle);
            circle = new ArrayList<>();
        }
        skipInfo.setSkipData(data);

        //创建并评估
        evaluator = new Evaluator(assetManager, skipInfo);
        evaluator.evaluate();

    }


    public Example(AssetManager assetManager, int breakCount, int circleCount, int duration, List<BlePoint> blePoints) {
        SkipInfo skipInfo = new SkipInfo();
        skipInfo.setBreakCount(breakCount);
        skipInfo.setCircleCount(circleCount);
        skipInfo.setDuration(duration);

        List<List<SkipUnitData>> data = new ArrayList<>();
        List<SkipUnitData> circle = new ArrayList<>();
        int currCircle = Math.round(blePoints.get(0).getNumber());
        int circlenum = currCircle;
        //按圈保存在一个list中
        for (int row = 0; row < blePoints.size(); row++) {
            currCircle = Math.round(blePoints.get(row).getNumber());
            if (circlenum != currCircle) {
                circlenum = currCircle;
                data.add(circle);
                circle = new ArrayList<>();
            }
            SkipUnitData unit = new SkipUnitData();
            unit.setAccelerationX(blePoints.get(row).getAccelerationX());
            unit.setAccelerationY(blePoints.get(row).getAccelerationY());
            unit.setAccelerationZ(blePoints.get(row).getAccelerationZ());
            unit.setAngularVelocityX(blePoints.get(row).getAngularVelocityX());
            unit.setAngularVelocityY(blePoints.get(row).getAngularVelocityY());
            unit.setAngularVelocityZ(blePoints.get(row).getAngularVelocityZ());
            unit.setPitch(blePoints.get(row).getPitch());
            unit.setRoll(blePoints.get(row).getRoll());
            unit.setYaw(blePoints.get(row).getYaw());
            unit.setHallSensor(Math.round(blePoints.get(row).getHallSensor()));
            unit.setTimestamp(Math.round(blePoints.get(row).getTimestamp()));

            circle.add(unit);
        }
        if (circle.size() > 0) {
            data.add(circle);
            circle = new ArrayList<>();
        }
        skipInfo.setSkipData(data);

        //创建并评估
        evaluator = new Evaluator(assetManager, skipInfo);
        evaluator.evaluate();

    }


    public Evaluator getData() {
        print();
        return evaluator;
    }


    public void print() {

        System.out.println("摇绳得分： " + evaluator.getRopeSwingingScore());
        System.out.println("稳定性得分： " + evaluator.getPositionStabilityScore());
        System.out.println("跳绳节奏得分： " + evaluator.getSpeedStabilityScore());
        System.out.println("耐力得分： " + evaluator.getEnduranceScore());
        System.out.println("协调性得分： " + evaluator.getCoordinationScore());
    }
}
