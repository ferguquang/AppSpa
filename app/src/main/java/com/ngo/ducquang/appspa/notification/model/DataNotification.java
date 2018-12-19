package com.ngo.ducquang.appspa.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/8/2018.
 */

public class DataNotification {
    @SerializedName("TotalNotView")
    @Expose
    int totalNotView;
    @SerializedName("Notifications")
    @Expose
    private List<Notification> notifications = null;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public int getTotalNotView() {
        return totalNotView;
    }

    public void setTotalNotView(int totalNotView) {
        this.totalNotView = totalNotView;
    }
}
