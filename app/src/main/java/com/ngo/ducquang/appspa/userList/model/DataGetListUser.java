package com.ngo.ducquang.appspa.userList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;

import java.util.List;

/**
 * Created by ducqu on 9/28/2018.
 */

public class DataGetListUser {
    @SerializedName("UserApps")
    @Expose
    private List<UserApp> userApps = null;

    public List<UserApp> getUserApps() {
        return userApps;
    }

    public void setUserApps(List<UserApp> userApps) {
        this.userApps = userApps;
    }
}
