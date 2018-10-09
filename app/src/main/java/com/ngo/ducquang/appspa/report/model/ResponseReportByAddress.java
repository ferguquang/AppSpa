package com.ngo.ducquang.appspa.report.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.base.Message;

import java.util.List;

/**
 * Created by ducqu on 10/9/2018.
 */

public class ResponseReportByAddress {
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("Data")
    @Expose
    private DataReportByAddress data;
    @SerializedName("Messages")
    @Expose
    private List<Message> messages = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataReportByAddress getData() {
        return data;
    }

    public void setData(DataReportByAddress data) {
        this.data = data;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
