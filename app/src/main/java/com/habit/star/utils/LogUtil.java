package com.habit.star.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by sundongdong on 2017/2/24.
 */
public class LogUtil {

    private static final String TAG = "com.hkbt.customer %s %s %s";
    public static boolean isDebug = true;

    public static void e(String tag, Object... o) {
        if (isDebug) {
            Logger.e(tag, o);
        }
    }

    public static void e(Object o) {
        LogUtil.e(TAG, o);
    }

    public static void w(String tag, Object... o) {
        if (isDebug) {
            Logger.w(tag, o);
        }
    }

    public static void w(Object... o) {
        LogUtil.w(TAG, o);
    }

    public static void d(String tag, Object... object) {
        if (isDebug) {
            Logger.d(tag, object);
        }
    }

    public static void d(Object object) {
        if (isDebug) {
            Logger.d(object);
        }
    }

    public static void i(Object... object) {
        if (isDebug) {
            Logger.i("", object);
        }

    }

    public static void logSend(Object... object) {
        if (isDebug) {
            StringBuffer stringBuffer = new StringBuffer("http send: ");
            for (Object obj : object) {
                if (obj instanceof HashMap) {
                    HashMap<String, String> map = (HashMap<String, String>) obj;
                    for (HashMap.Entry entry : map.entrySet()) {
                        stringBuffer.append(entry.getKey());
                        stringBuffer.append("=");
                        stringBuffer.append(entry.getValue());
                        stringBuffer.append("&");
                    }
                } else {
                    stringBuffer.append(obj);
                    stringBuffer.append("&");
                }
            }
            Log.i("okhttp", stringBuffer.toString());
        }
    }

    public static void logReceive(Object... object) {
        if (isDebug) {
            StringBuffer stringBuffer = new StringBuffer("http receive:\n");
            Field[] fields = null;
            Gson gson = new Gson();
            for (Object obj : object) {
                stringBuffer.append(gson.toJson(obj));
                stringBuffer.append("\n");
            }
            Log.i("okhttp", stringBuffer.toString());
        }
    }

    public static void json(String json) {
        if (isDebug) {
            if (TextUtils.isEmpty(json)) {
                d("Empty/Null json content");
                return;
            }
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    String message = jsonObject.toString(2);
                    d(message);
                    return;
                }
                if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    String message = jsonArray.toString(2);
                    d(message);
                    return;
                }
                d("Invalid Json:" + json);
            } catch (JSONException e) {
                d("Invalid Json" + json);
            }
        }
    }

    public static void xml(String xml) {
        if (isDebug) {
            Logger.xml(xml);
        }
    }

    public static void jsonD(String json) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                d(message);
                return;
            }
            if (json.length() > 1000) {
                d("Invalid Json:" + json.substring(0, 1000));
            } else {
                d("Invalid Json:" + json);
            }
        } catch (JSONException e) {
            if (json.length() > 1000) {
                d("Invalid Json:" + json.substring(0, 1000));
            } else {
                d("Invalid Json:" + json);
            }
        }
    }
}
