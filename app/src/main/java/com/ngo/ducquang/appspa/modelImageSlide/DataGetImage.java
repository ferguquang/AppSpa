package com.ngo.ducquang.appspa.modelImageSlide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/19/2018.
 */

public class DataGetImage {
    @SerializedName("Files")
    @Expose
    private List<File> files = null;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
