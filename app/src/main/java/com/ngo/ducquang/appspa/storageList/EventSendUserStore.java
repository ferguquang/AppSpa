package com.ngo.ducquang.appspa.storageList;

import com.ngo.ducquang.appspa.storageList.model.UserStore;

/**
 * Created by ducqu on 9/27/2018.
 */

public class EventSendUserStore {
    private UserStore userStore;

    public EventSendUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
