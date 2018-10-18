package com.ngo.ducquang.appspa.base;

/**
 * Created by ducqu on 10/18/2018.
 */

public class Permission {
    private int index = 0;
    private String  value = "";

    public Permission(int index, String value)
    {
        this.index = index;
        this.value = value;
    }

    public int getIndex()
    {
        return index;
    }

    public String getValue()
    {
        return value;
    }
}
