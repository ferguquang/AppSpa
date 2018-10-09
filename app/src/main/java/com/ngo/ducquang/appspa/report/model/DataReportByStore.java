package com.ngo.ducquang.appspa.report.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.modelStore.Store;

import java.util.List;

/**
 * Created by ducqu on 10/9/2018.
 */

public class DataReportByStore {
    @SerializedName("Stores")
    @Expose
    private List<Store> store = null;
    @SerializedName("ReportByStores")
    @Expose
    private List<ReportByStore> reportByStores = null;

    public List<Store> getStore() {
        return store;
    }

    public void setStore(List<Store> store) {
        this.store = store;
    }

    public List<ReportByStore> getReportByStores() {
        return reportByStores;
    }

    public void setReportByStores(List<ReportByStore> reportByStores) {
        this.reportByStores = reportByStores;
    }
}
