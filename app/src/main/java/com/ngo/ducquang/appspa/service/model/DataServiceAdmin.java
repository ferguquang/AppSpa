package com.ngo.ducquang.appspa.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.List;

/**
 * Created by ducqu on 9/30/2018.
 */

public class DataServiceAdmin {
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
