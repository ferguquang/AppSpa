package com.ngo.ducquang.appspa.book;

/**
 * Created by ducqu on 9/22/2018.
 */

public class BookOptionModel {
    private int id;
    private String name;

    public BookOptionModel(int id, String name) {
        this.id = id;
        this.name = name;
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
}
