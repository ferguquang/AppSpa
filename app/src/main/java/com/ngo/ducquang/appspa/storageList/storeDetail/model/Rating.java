package com.ngo.ducquang.appspa.storageList.storeDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 9/30/2018.
 */

public class Rating {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Rate")
    @Expose
    private Float rate;
    @SerializedName("Note")
    @Expose
    private String note;
    @SerializedName("IDStore")
    @Expose
    private int iDStore;
    @SerializedName("CreatedByName")
    @Expose
    private String createdByName;
    @SerializedName("CreatedBy")
    @Expose
    private int createdBy;
    @SerializedName("Created")
    @Expose
    private long created;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getiDStore() {
        return iDStore;
    }

    public void setiDStore(int iDStore) {
        this.iDStore = iDStore;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
