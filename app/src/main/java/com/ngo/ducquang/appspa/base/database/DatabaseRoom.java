package com.ngo.ducquang.appspa.base.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ngo.ducquang.appspa.login.modelLogin.UserApp;
import com.ngo.ducquang.appspa.login.modelLogin.UserDao;

import static com.ngo.ducquang.appspa.base.database.DatabaseRoom.DATABASE_VERSION;

/**
 * Created by ducqu on 9/23/2018.
 */

@Database(entities = {UserApp.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class DatabaseRoom extends RoomDatabase {
    private static DatabaseRoom instance;
    public abstract UserDao userDao();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Room-database";

    public static DatabaseRoom getAppDatabase(Context context)
    {
        if (instance == null)
        {
            synchronized (DatabaseRoom.class)
            {
                instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseRoom.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return instance;
    }
}
