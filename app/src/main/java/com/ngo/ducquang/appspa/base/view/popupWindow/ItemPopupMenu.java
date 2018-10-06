package com.ngo.ducquang.appspa.base.view.popupWindow;

/**
 * Created by ducqu on 9/28/2018.
 */

public class ItemPopupMenu {
    private int id;
    private String title, code;

    public ItemPopupMenu(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public ItemPopupMenu(int id, String title, String code) {
        this.id = id;
        this.title = title;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
