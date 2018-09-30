package com.ngo.ducquang.appspa.storageList.storeDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.storageList.model.UserStore;

import java.util.List;

/**
 * Created by ducqu on 9/29/2018.
 */

public class DataStoreDetail {
    @SerializedName("IsAdmin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("UserStore")
    @Expose
    private UserStore userStore;
    @SerializedName("Discusses")
    @Expose
    private List<Discuss> discusses = null;
    @SerializedName("Ratings")
    @Expose
    private List<Rating> ratings = null;
    @SerializedName("Rating")
    @Expose
    private Float rating;
    @SerializedName("RatingAverage")
    @Expose
    private Float ratingAverage;
    @SerializedName("IsCmt")
    @Expose
    private Boolean isCmt;
    @SerializedName("isRating")
    @Expose
    private boolean isRating;
    @SerializedName("IsOrder")
    @Expose
    private Boolean isOrder;

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public List<Discuss> getDiscusses() {
        return discusses;
    }

    public void setDiscusses(List<Discuss> discusses) {
        this.discusses = discusses;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Float getRating() {
        return rating;
    }

    public boolean isRating() {
        return isRating;
    }

    public void setRating(boolean rating) {
        isRating = rating;
    }

    public Boolean getOrder() {
        return isOrder;
    }

    public void setOrder(Boolean order) {
        isOrder = order;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Float ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public Boolean getCmt() {
        return isCmt;
    }

    public void setCmt(Boolean cmt) {
        isCmt = cmt;
    }

}
