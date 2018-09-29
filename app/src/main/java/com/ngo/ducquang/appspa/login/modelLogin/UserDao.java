package com.ngo.ducquang.appspa.login.modelLogin;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ducqu on 9/23/2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserApp")
    UserApp getUser();

    @Insert(onConflict = REPLACE)
    void insert(UserApp userApp);

    @Query("DELETE FROM UserApp")
    void clearData();

    @Update
    void updateClue(UserApp userApp);
}
