package com.ngo.ducquang.appspa.storageList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 9/27/2018.
 */

public class UserStore {
    @SerializedName("IDUser")
    @Expose
    private int iDUser;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PositionID")
    @Expose
    private int positionID;
    @SerializedName("PositionName")
    @Expose
    private String positionName;
    @SerializedName("Describe")
    @Expose
    private String describe;
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
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
