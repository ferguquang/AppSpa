package com.ngo.ducquang.appspa.login.modelLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 9/23/2018.
 */

public class DataLogin {
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("UserApp")
    @Expose
    private UserApp userApp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }
}
