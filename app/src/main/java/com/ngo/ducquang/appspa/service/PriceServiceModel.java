package com.ngo.ducquang.appspa.service;

/**
 * Created by ducqu on 10/17/2018.
 */

public class PriceServiceModel {
    private String key, valuePrice;

    public PriceServiceModel(String key, String valuePrice) {
        this.key = key;
        this.valuePrice = valuePrice;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValuePrice() {
        return valuePrice;
    }

    public void setValuePrice(String valuePrice) {
        this.valuePrice = valuePrice;
    }
}
