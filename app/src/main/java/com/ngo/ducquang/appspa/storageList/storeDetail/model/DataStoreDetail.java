package com.ngo.ducquang.appspa.storageList.storeDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

/**
 * Created by ducqu on 9/29/2018.
 */

public class DataStoreDetail {
    @SerializedName("IsCreate")
    @Expose
    private Boolean isCreate;
    @SerializedName("IsUpdate")
    @Expose
    private Boolean isUpdate;
    @SerializedName("UserStore")
    @Expose
    private UserStore userStore;

    public Boolean getCreate() {
        return isCreate;
    }

    public void setCreate(Boolean create) {
        isCreate = create;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
