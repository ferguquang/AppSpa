package com.ngo.ducquang.appspa;

/**
 * Created by ducqu on 10/13/2018.
 */

public class BannerModel {
    private int id;
    private String url;

    public BannerModel(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
