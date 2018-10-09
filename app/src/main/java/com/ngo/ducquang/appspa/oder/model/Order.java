package com.ngo.ducquang.appspa.oder.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ducqu on 10/3/2018.
 */

public class Order implements Serializable {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("StatusName")
    @Expose
    private String statusName;
    @SerializedName("StatusColor")
    @Expose
    private String statusColor;
    @SerializedName("Type")
    @Expose
    private int type;
    @SerializedName("Describe")
    @Expose
    private String describe;
    @SerializedName("OnDate")
    @Expose
    private long onDate;
    @SerializedName("Finished")
    @Expose
    private long finished;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreID")
    @Expose
    private int storeID;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Created")
    @Expose
    private long created;
    @SerializedName("Responsed")
    @Expose
    private long responsed;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("IsView")
    @Expose
    private Boolean isView;
    @SerializedName("IsDelete")
    @Expose
    private Boolean isDelete;
    @SerializedName("IsUpdate")
    @Expose
    private Boolean isUpdate;
    @SerializedName("IsDone")
    @Expose
    private Boolean isDone;
    @SerializedName("IsReject")
    @Expose
    private Boolean isReject;
    @SerializedName("IsApproved")
    @Expose
    private Boolean isApproved;
    @SerializedName("IsCancel")
    @Expose
    private Boolean isCancel;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public long getOnDate() {
        return onDate;
    }

    public void setOnDate(long onDate) {
        this.onDate = onDate;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getView() {
        return isView;
    }

    public void setView(Boolean view) {
        isView = view;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getReject() {
        return isReject;
    }

    public void setReject(Boolean reject) {
        isReject = reject;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getCancel() {
        return isCancel;
    }

    public void setCancel(Boolean cancel) {
        isCancel = cancel;
    }

    public long getResponsed() {
        return responsed;
    }

    public void setResponsed(long responsed) {
        this.responsed = responsed;
    }

    public static Order initialize(String jsonGson)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonGson, Order.class);
    }

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
