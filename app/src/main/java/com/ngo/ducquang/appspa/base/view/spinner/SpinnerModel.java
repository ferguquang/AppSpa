package com.ngo.ducquang.appspa.base.view.spinner;

/**
 * Created by ducqu on 9/23/2018.
 */

public class SpinnerModel {
    private int id;
    private String name;
    private String code;

    public SpinnerModel(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
