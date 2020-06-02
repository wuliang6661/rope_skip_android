package com.tohabit.skip.utils;

import android.content.Context;
import android.location.LocationManager;
import android.view.View;

public class Utils {
    public static boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        } else {
            return false;
        }
    }

    public static void fitsSystemWindows(boolean isTranslucentStatus, View view) {
        if (isTranslucentStatus) {
            view.getLayoutParams().height = calcStatusBarHeight(view.getContext());
        }
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

//    public static void gotoImageDetailView(SupportFragment fragment, String img){
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.STRING_MODE,img);
//        fragment.start(ImageDetailFragment.newInstance(bundle));
//    }

    public static String timeToString(int seconds){
        if (seconds<60){
            return "00''"+numToString(seconds);
        }else if (seconds<3600){
            int munite = seconds/60;
            int second = seconds%60;
            return numToString(munite)+numToString(second);
        }else {
            int hour = seconds/3600;
            int munite = seconds%3600/60;
            int second = seconds%3600%60;
            return numToString(hour)+numToString(munite)+numToString(second);
        }
    }

    public static String numToString(int count){
        return count<10?"0"+count+"''":count+"''";
    }


    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }
}
