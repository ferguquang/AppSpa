package com.ngo.ducquang.appspa.alarmService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ducqu on 9/21/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ALARM_NAME = "alarmname";
    public static final String ACTION_ALARM_RECEIVER = "actionalarmreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationMessage.notificationAlarmService(context, 9, "dsg");
    }
}
