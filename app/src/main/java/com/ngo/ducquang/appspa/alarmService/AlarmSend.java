package com.ngo.ducquang.appspa.alarmService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by ducqu on 9/21/2018.
 */

public class AlarmSend
{
//    private static AlarmManager alarmManager;
//    private static PendingIntent pendingIntent;

    private static final int REQUEST_CODE = 100;

    public static void setAlarm(Context context, String name)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.ALARM_NAME, name);
        intent.setAction(AlarmReceiver.ACTION_ALARM_RECEIVER);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pendingIntent);
    }

    public static void canclerAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ACTION_ALARM_RECEIVER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static boolean checkWorking(Context context)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);//the same as up
        intent.setAction(AlarmReceiver.ACTION_ALARM_RECEIVER);
        boolean isWorking = (PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
        return isWorking;
    }

    public static void cancelAlarmRTC()
    {
//        if (alarmManager!= null) {
//            alarmManager.cancel(pendingIntent);
//            pendingIntent.cancel();
//        }
    }

    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
