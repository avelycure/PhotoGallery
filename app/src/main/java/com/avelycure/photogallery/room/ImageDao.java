package com.avelycure.photogallery.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("SELECT * FROM image")
    List<Image> getAll();

    @Query("SELECT * FROM image WHERE id = :id")
    Image getById(long id);

    @Insert
    void insert(Image image);

    @Delete
    void delete(Image image);
}
