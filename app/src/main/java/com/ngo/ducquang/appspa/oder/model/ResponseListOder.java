package com.ngo.ducquang.appspa.oder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/3/2018.
 */

public class ResponseListOder {
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("Data")
    @Expose
    private DataListOrder data;
    @SerializedName("Messages")
    @Expose
    private List<Object> messages = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataListOrder getData() {
        return data;
    }

    public void setData(DataListOrder data) {
        this.data = data;
    }

    public List<Object> getMessages() {
        return messages;
    }

    public void setMessages(List<Object> messages) {
        this.messages = messages;
    }
}
