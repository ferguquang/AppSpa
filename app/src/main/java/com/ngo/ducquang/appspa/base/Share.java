package com.ngo.ducquang.appspa.base;

import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;
import com.ngo.ducquang.appspa.storageList.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducqu on 9/23/2018.
 */

public class Share
{
    private static final Share INSTANCE = new Share();
    private Share() {}
    public static Share getInstance() {
        return INSTANCE;
    }

    public List<Province> provinces = new ArrayList<>();
    public List<District> districts = new ArrayList<>();

    public List<Category> categoryList = new ArrayList<>();
}
