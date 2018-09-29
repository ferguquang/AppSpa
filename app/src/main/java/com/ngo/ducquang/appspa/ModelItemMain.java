package com.ngo.ducquang.appspa;

import java.io.Serializable;

/**
 * Created by ducqu on 9/25/2018.
 */

public class ModelItemMain implements Serializable {
    private int id;
    private String name, content;
    private int idImage;

    public ModelItemMain(int id, String name, String content, int idImage) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.idImage = idImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}
