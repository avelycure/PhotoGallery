package com.avelycure.photogallery;

import android.app.Application;

import androidx.room.Room;

import com.avelycure.photogallery.room.AppDatabase;

/**
 * In this class the only instance of database is created
 */
public class App extends Application {
    public static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                //.fallbackToDestructiveMigration()
                .build();
    }

    public static App getInstance(){
        return instance;
    }

    public AppDatabase getDatabase(){
        return database;
    }
}
