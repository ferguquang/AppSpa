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

@Entity(tableName = "UserApp")
public class UserApp
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("IDUser")
    @Expose
    @ColumnInfo(name = "IDUser")
    int iDUser;
    @SerializedName("Name")
    @Expose
    @ColumnInfo(name = "Name")
    private String name;
    @SerializedName("PositionID")
    @Expose
    @ColumnInfo(name = "PositionID")
    private int positionID;
    @SerializedName("PositionName")
    @Expose
    @ColumnInfo(name = "PositionName")
    private String positionName;
    @SerializedName("ProvinceID")
    @Expose
    @ColumnInfo(name = "ProvinceID")
    private int provinceID;
    @SerializedName("ProvinceName")
    @Expose
    @ColumnInfo(name = "ProvinceName")
    private String provinceName;
    @SerializedName("DistrictID")
    @Expose
    @ColumnInfo(name = "DistrictID")
    private int districtID;
    @SerializedName("DistrictName")
    @Expose
    @ColumnInfo(name = "DistrictName")
    private String districtName;
    @SerializedName("Address")
    @Expose
    @ColumnInfo(name = "Address")
    private String address;
    @SerializedName("Gender")
    @Expose
    @ColumnInfo(name = "Gender")
    private int gender;
    @SerializedName("Avatar")
    @Expose
    @ColumnInfo(name = "Avatar")
    private String avatar;
    @SerializedName("Email")
    @Expose
    @ColumnInfo(name = "Email")
    private String email;
    @SerializedName("Birthday")
    @Expose
    @ColumnInfo(name = "Birthday")
    private long birthday;
    @SerializedName("Phone")
    @Expose
    @ColumnInfo(name = "Phone")
    private String phone;
    @SerializedName("Status")
    @Expose
    @ColumnInfo(name = "Status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getiDUser() {
        return iDUser;
    }

    public void setiDUser(int iDUser) {
        this.iDUser = iDUser;
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
