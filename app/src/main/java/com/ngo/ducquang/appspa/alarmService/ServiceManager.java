package com.ngo.ducquang.appspa.alarmService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telecom.ConnectionService;
import android.widget.Toast;

/**
 * Created by ducqu on 9/29/2018.
 */

public class ServiceManager extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_STICKY;
//    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
        NotificationMessage.notificationAlarmService(getApplicationContext(), (int) System.currentTimeMillis(), "test");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public static void startService(Context context)
    {
        Intent intent = new Intent(context, ServiceManager.class);
        PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pintent);
        context.startService(intent);
    }

    public static void stopService(Context context)
    {
        Intent i = new Intent(context, ServiceManager.class);
        context.stopService(i);
    }
}
