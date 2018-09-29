package com.ngo.ducquang.appspa.storageList.createStore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

/**
 * Created by ducqu on 9/27/2018.
 */

public class DataCreateStore {

    @SerializedName("UserStore")
    @Expose
    private UserStore userStore;

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
