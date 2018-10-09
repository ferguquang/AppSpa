package com.ngo.ducquang.appspa.login.modelLogin;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ducqu on 9/23/2018.
 */

public class UserApp
{
    @SerializedName("IDUser")
    @Expose
    private int iDUser;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PositionID")
    @Expose
    private int positionID;
    @SerializedName("PositionName")
    @Expose
    private String positionName;
    @SerializedName("ProvinceID")
    @Expose
    private int provinceID;
    @SerializedName("ProvinceName")
    @Expose
    private String provinceName;
    @SerializedName("DistrictID")
    @Expose
    private int districtID;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Gender")
    @Expose
    private int gender;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Birthday")
    @Expose
    private long birthday;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Status")
    @Expose
    private int status;

    public int getiDUser() {
        return iDUser;
    }

    public void setiDUser(int iDUser) {
        this.iDUser = iDUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static UserApp initialize(String jsonGson)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonGson, UserApp.class);
    }

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
