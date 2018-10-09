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

    private static final String PREF_ACCOUNT = "account_id";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void savePreferences(Context context, String key, boolean content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(key, content);
        editor.apply();
    }

    public static void savePreferences(Context context, String key, String content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static void savePreferences(Context context, String key, int content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(key, content);
        editor.apply();
    }

    public static boolean getPreferences(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(key, defaultValue);
    }

    public static String getPreferences(Context context, String key, String defVal) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(key, defVal);
    }

    public static int getPreferences(Context context, String key, int defVal) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getInt(key, defVal);
    }

    public static int getLoginAccount(Context context) {
        return getPreferences(context, PREF_ACCOUNT, 0);
    }

    public static void clearPreference(Context context)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}
