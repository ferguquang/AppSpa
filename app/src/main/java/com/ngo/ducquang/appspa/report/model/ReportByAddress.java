package com.ngo.ducquang.appspa.report.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ReportByAddress {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Total")
    @Expose
    private int total;
    @SerializedName("TotalStore")
    @Expose
    private int totalStore;
    @SerializedName("TotalUser")
    @Expose
    private int totalUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalStore() {
        return totalStore;
    }

    public void setTotalStore(int totalStore) {
        this.totalStore = totalStore;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }
}
