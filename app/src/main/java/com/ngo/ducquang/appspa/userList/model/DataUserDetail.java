package com.ngo.ducquang.appspa.userList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.login.modelLogin.UserApp;

/**
 * Created by ducqu on 9/29/2018.
 */

public class DataUserDetail {
    @SerializedName("UserApp")
    @Expose
    private UserApp userApp;

    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }
}
