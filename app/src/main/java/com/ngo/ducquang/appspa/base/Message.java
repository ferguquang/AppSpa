package com.ngo.ducquang.appspa.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 9/23/2018.
 */

public class Message {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("text")
    @Expose
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
