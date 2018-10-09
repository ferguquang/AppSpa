package com.ngo.ducquang.appspa.alarmService;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telecom.ConnectionService;
import android.widget.Toast;

import com.ngo.ducquang.appspa.base.PreferenceUtil;
import com.ngo.ducquang.appspa.base.api.ApiService;
import com.ngo.ducquang.appspa.notification.model.Notification;
import com.ngo.ducquang.appspa.notification.model.ResponseNotification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onStart(Intent intent, int startId)
    {
        Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();

        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.TOKEN, "");
        ApiService.Factory.getInstance().getNotificationInTop(token).enqueue(new Callback<ResponseNotification>()
        {
            @Override
            public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response)
            {
                if (response.body().getStatus() == 1)
                {
                    List<Notification> notifications = response.body().getData().getNotifications();
                    if (notifications.size() > 0)
                    {
                        for (int i = 0; i < notifications.size(); i++)
                        {
                            Notification notification = notifications.get(i);
                            NotificationMessage.notificationAlarmService(getApplicationContext(), notification);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseNotification> call, Throwable t) {

            }
        });

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
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 30, pintent);
        context.startService(intent);
    }

    public static void stopService(Context context)
    {
        Intent i = new Intent(context, ServiceManager.class);
        context.stopService(i);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context)
    {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
