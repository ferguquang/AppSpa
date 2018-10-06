package com.ngo.ducquang.appspa.oder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 10/3/2018.
 */

public class DataOrder {
    @SerializedName("Order")
    @Expose
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
