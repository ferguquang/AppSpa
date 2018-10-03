package com.ngo.ducquang.appspa.alarmService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.LogManager;
import com.ngo.ducquang.appspa.base.Manager;
import com.ngo.ducquang.appspa.base.ManagerTime;
import com.ngo.ducquang.appspa.notification.NotificationActivity;

/**
 * Created by ducqu on 9/21/2018.
 */

public class NotificationMessage
{
    private final static long[] VIBRATE = new long[]{0, 200, 200, 200, 200};
    private final static Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

    public static void notificationAlarmService(Context context, int idAlarm, String name)
    {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel("0", "AppSpa android 8", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription("description");
            mChannel.enableVibration(true);

            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.createNotificationChannel(mChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0");

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentTitle("AppSpa android 8")
                    .setSmallIcon(R.mipmap.ic_launcher) // required
                    .setContentText("des")  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setBadgeIconType(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            notifManager.notify(0, notification);
        }
        else
        {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notify = new Notification.Builder(context)
                    .setContentTitle("Tên khách đặt")
                    .setContentText("Gội đầu, làm tóc lúc: " + ManagerTime.convertToMonthDayYearHourMinuteFormatSlash(System.currentTimeMillis()))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .setSound(alarmSound)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVibrate(VIBRATE)
                    .getNotification();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis(), notify);
        }
    }

    public static void cancelNotification(Context context, int id)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    public static void removeAllNotification(Context context)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
