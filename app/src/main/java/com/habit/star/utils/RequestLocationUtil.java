package com.habit.star.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by dongdong on 2016/10/26.
 */

public class RequestLocationUtil {

    private static final String TAG = "RequestLocationUtil";
    public static final int GPS_OPEN_FALSE = 0;
    public static final int GPS_OPEN_TRUE = 1;


    public AMapLocationClientOption mLocationOption = null;
    // 声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    // 声明定位回调监听器
    public AMapLocationListener mLocationListener = null;

    private Context ctx;

    private OnLocationChangedListener mListener = null;

    private static RequestLocationUtil instance;

    public void addLocationChangedListener(OnLocationChangedListener listener) {
        this.mListener = listener;
    }

    public void removeLocationChangedListener() {
        mListener = null;
    }

    public RequestLocationUtil(Context applicationContext) {
        this.ctx = applicationContext;
        initToLocation();
    }

    /**
     * 一次定位
     */
    private void initToLocation() {
        mLocationListener = new AMapLocationListener() {


            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                // TODO Auto-generated method stub
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        if (mListener != null) {
                            mListener.onLocationChanged(amapLocation);
                        }
//                        // 定位成功回调信息，设置相关消息
//                        amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        amapLocation.getAccuracy();// 获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat(
//                                "yyyy-MM-dd HH:mm:ss");
//                        Date applicationTripTime = new Date(amapLocation.getTime());
//                        df.format(applicationTripTime);// 定位时间
//                        amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        amapLocation.getCountry();// 国家信息
//                        amapLocation.getProvince();// 省信息
//                        amapLocation.getCity();// 城市信息
//                        amapLocation.getDistrict();// 城区信息
//                        amapLocation.getStreet();// 街道信息
//                        amapLocation.getStreetNum();// 街道门牌号信息
//                        amapLocation.getCityCode();// 城市编码
//                        amapLocation.getAdCode();// 地区编码
//                        amapLocation.getAoiName();// 获取当前定位点的AOI信息
//                        amapLocation.getLatitude();// 获取纬度
//                        amapLocation.getLongitude();// 获取经度

                    } else {
                        // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        if (mListener != null) {
                            mListener.onLocationFailed(amapLocation.getErrorCode(), amapLocation.getErrorInfo());
                        }
                    }
                }
            }


        };

        // 初始化定位
        mLocationClient = new AMapLocationClient(ctx);
        // 设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为5000ms
        mLocationOption.setInterval(5000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    public static RequestLocationUtil getInstance(Context ctx) {
        if (instance == null) {
            instance = new RequestLocationUtil(ctx);
        }
        return instance;
    }

    public void onDestroy() {
        instance = null;
    }

    public int requestLocation() {
        if (!Utils.isGPSOpen(ctx)) {
            mLocationClient.startLocation();
            return GPS_OPEN_FALSE;
        }
        // 启动定位
        mLocationClient.startLocation();
        return GPS_OPEN_TRUE;
    }

    public void stopRequestLocation() {
        mLocationClient.stopLocation();
    }

    public interface OnLocationChangedListener {
        void onLocationChanged(AMapLocation amapLocation);

        void onLocationFailed(int errorCode, String errorMsg);
    }
}