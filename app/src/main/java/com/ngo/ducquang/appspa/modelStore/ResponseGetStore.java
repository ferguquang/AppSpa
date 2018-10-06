package com.ngo.ducquang.appspa.modelStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.base.Message;

import java.util.List;

/**
 * Created by ducqu on 10/6/2018.
 */

public class ResponseGetStore {
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Data")
    @Expose
    private DataGetStore data;
    @SerializedName("Messages")
    @Expose
    private List<Message> messages = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DataGetStore getData() {
        return data;
    }

    public void setData(DataGetStore data) {
        this.data = data;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
