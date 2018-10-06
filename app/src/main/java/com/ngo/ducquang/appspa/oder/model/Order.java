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
    private Integer iD;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("StatusName")
    @Expose
    private String statusName;
    @SerializedName("StatusColor")
    @Expose
    private String statusColor;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("Priority")
    @Expose
    private Integer priority;
    @SerializedName("Describe")
    @Expose
    private String describe;
    @SerializedName("OnDate")
    @Expose
    private long onDate;
    @SerializedName("Finished")
    @Expose
    private Integer finished;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Created")
    @Expose
    private long created;
    @SerializedName("IsView")
    @Expose
    private Boolean isView;
    @SerializedName("IsDelete")
    @Expose
    private Boolean isDelete;
    @SerializedName("IsUpdate")
    @Expose
    private Boolean isUpdate;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
