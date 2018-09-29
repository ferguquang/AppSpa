package com.ngo.ducquang.appspa.storageList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;

import java.util.List;

/**
 * Created by ducqu on 9/27/2018.
 */

public class DataStoreList {
    @SerializedName("IsAdmin")
    @Expose
    private Boolean isAdmin;

    @SerializedName("UserStores")
    @Expose
    private List<UserStore> userStores = null;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("Provinces")
    @Expose
    private List<Province> provinces = null;
    @SerializedName("Districts")
    @Expose
    private List<District> districts = null;

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<UserStore> getUserStores() {
        return userStores;
    }

    public void setUserStores(List<UserStore> userStores) {
        this.userStores = userStores;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
