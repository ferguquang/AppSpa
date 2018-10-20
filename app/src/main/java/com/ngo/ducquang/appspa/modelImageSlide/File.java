package com.ngo.ducquang.appspa.modelImageSlide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 10/19/2018.
 */

public class File {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("FileName")
    @Expose
    private String fileName;
    @SerializedName("FilePath")
    @Expose
    private String filePath;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
