package com.ngo.ducquang.appspa.storageList.storeDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/1/2018.
 */

public class DataRate {
    @SerializedName("Ratings")
    @Expose
    private List<Rating> ratings = null;
    @SerializedName("Rating")
    @Expose
    private Float rating;
    @SerializedName("RatingAverage")
    @Expose
    private Float ratingAverage;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Float getRating() {
        return rating;
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
}
