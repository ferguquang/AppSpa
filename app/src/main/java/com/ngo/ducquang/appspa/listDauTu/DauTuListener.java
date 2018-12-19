package com.ngo.ducquang.appspa.listDauTu;

import com.ngo.ducquang.appspa.login.modelLogin.UserApp;

public interface DauTuListener {
    void addItem(UserApp userApp);
    void updateItem(UserApp userApp, int position);
}
