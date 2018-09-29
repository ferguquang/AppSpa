package com.ngo.ducquang.appspa.alarmService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

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
        String message = "";
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notify = new Notification.Builder(context)
                .setContentTitle("Tên khách đặt")
                .setContentText("Gội đầu, làm tóc lúc: " + ManagerTime.convertToMonthDayYearHourMinuteFormatSlash(System.currentTimeMillis()))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setSound(alarmSound)
                .setVibrate(VIBRATE)
                .getNotification();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notify);
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
