package com.ngo.ducquang.appspa.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by QuangND on 12/7/2017.
 */

public class PreferencesManager
{
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;
    private static final String PREFERENCENS_NAME = "intro-slide";
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";

    public PreferencesManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCENS_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime)
    {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch()
    {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
