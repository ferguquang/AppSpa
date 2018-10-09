package com.ngo.ducquang.appspa.report.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ReportByStore {
    @SerializedName("IDStore")
    @Expose
    private int iDStore;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("TotalOrder")
    @Expose
    private int totalOrder;
    @SerializedName("TotalDone")
    @Expose
    private int totalDone;
    @SerializedName("TotalInProcess")
    @Expose
    private int totalInProcess;
    @SerializedName("TotalNotDone")
    @Expose
    private int totalNotDone;
    @SerializedName("TotalUser")
    @Expose
    private int totalUser;

    public int getiDStore() {
        return iDStore;
    }

    public void setiDStore(int iDStore) {
        this.iDStore = iDStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotalDone() {
        return totalDone;
    }

    public void setTotalDone(int totalDone) {
        this.totalDone = totalDone;
    }

    public int getTotalInProcess() {
        return totalInProcess;
    }

    public void setTotalInProcess(int totalInProcess) {
        this.totalInProcess = totalInProcess;
    }

    public int getTotalNotDone() {
        return totalNotDone;
    }

    public void setTotalNotDone(int totalNotDone) {
        this.totalNotDone = totalNotDone;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }
}
