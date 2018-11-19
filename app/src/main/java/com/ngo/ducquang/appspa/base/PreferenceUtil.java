package com.ngo.ducquang.appspa.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ducqu on 9/21/2018.
 */

public class PreferenceUtil
{
    public static final String LOGIN_SUCCESS = "loginsucces";
    public static final String ALARM_NOTIFICATION = "alarmnotification";
    public static final String TOKEN = "token";
    public static final String USER_APP = "userapp";
    public static final String POSITION_ID = "positionid";

    public static final String DATA_GET_STORE = "datagetstore";
    public static final String DATA_GET_ADDRESS = "datagetaddress";

    private static final String PREF_ACCOUNT = "account_id";

    private static SharedPreferences getPreferences(Context context) throws Exception
    {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void savePreferences(Context context, String key, boolean content) {
        SharedPreferences.Editor editor = null;
        try {
            editor = getPreferences(context).edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putBoolean(key, content);
        editor.apply();
    }

    public static void savePreferences(Context context, String key, String content) {
        SharedPreferences.Editor editor = null;
        try {
            editor = getPreferences(context).edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putString(key, content);
        editor.apply();
    }

    public static void savePreferences(Context context, String key, int content) {
        SharedPreferences.Editor editor = null;
        try {
            editor = getPreferences(context).edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putInt(key, content);
        editor.apply();
    }

    public static boolean getPreferences(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = null;
        try {
            preferences = getPreferences(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preferences.getBoolean(key, defaultValue);
    }

    public static String getPreferences(Context context, String key, String defVal) {
        SharedPreferences preferences = null;
        try {
            preferences = getPreferences(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preferences.getString(key, defVal);
    }

    public static int getPreferences(Context context, String key, int defVal) {
        SharedPreferences preferences = null;
        try {
            preferences = getPreferences(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preferences.getInt(key, defVal);
    }

    public static int getLoginAccount(Context context) {
        return getPreferences(context, PREF_ACCOUNT, 0);
    }

    public static void clearPreference(Context context)
    {
        SharedPreferences.Editor editor = null;
        try {
            editor = getPreferences(context).edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.clear();
        editor.apply();
    }
}
