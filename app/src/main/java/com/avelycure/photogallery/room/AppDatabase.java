package com.avelycure.photogallery.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Image.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();
}
