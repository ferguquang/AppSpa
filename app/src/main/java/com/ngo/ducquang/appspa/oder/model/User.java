package com.ngo.ducquang.appspa.oder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 10/6/2018.
 */

public class User {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Gender")
    @Expose
    private Integer gender;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Birthday")
    @Expose
    private long birthday;

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
