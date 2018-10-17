package com.ngo.ducquang.appspa.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.List;

/**
 * Created by ducqu on 10/8/2018.
 */

public class Notification {
    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Type")
    @Expose
    private int type;
    @SerializedName("IDOrder")
    @Expose
    private int iDOrder;
    @SerializedName("IDStore")
    @Expose
    private int iDStore;
    @SerializedName("StartDate")
    @Expose
    private long startDate;
    @SerializedName("EndDate")
    @Expose
    private long endDate;
    @SerializedName("Describe")
    @Expose
    private String describe;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("Created")
    @Expose
    private long created;
    @SerializedName("IsView")
    @Expose
    private boolean isView;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getiDOrder() {
        return iDOrder;
    }

    public void setiDOrder(int iDOrder) {
        this.iDOrder = iDOrder;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getiDStore() {
        return iDStore;
    }

    public void setiDStore(int iDStore) {
        this.iDStore = iDStore;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
