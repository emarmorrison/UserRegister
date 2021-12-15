package com.emar.userregister.database;

import android.content.Context;

import com.emar.userregister.dao.UserDao;
import com.emar.userregister.entities.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context){

        if(appDatabase==null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"user_registry_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return appDatabase;

    }
}
