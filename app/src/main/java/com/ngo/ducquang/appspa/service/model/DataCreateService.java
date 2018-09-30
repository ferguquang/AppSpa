package com.ngo.ducquang.appspa.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.storageList.model.Category;

/**
 * Created by ducqu on 9/30/2018.
 */

public class DataCreateService {
    @SerializedName("Category")
    @Expose
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
