package com.ngo.ducquang.appspa.storageList.storeDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 9/30/2018.
 */

public class Discuss {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Body")
    @Expose
    private String body;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
