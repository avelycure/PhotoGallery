package com.avelycure.photogallery.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface provides methods to work with database
 */
@Dao
public interface ImageDao {
    @Query("SELECT * FROM image")
    List<Image> getAll();

    @Query("SELECT * FROM image WHERE id = :id")
    Image getById(long id);

    @Query("SELECT * FROM image WHERE album LIKE :album_param")
    List<Image> getByAlbum(String album_param);

    @Insert
    void insert(Image image);

    @Delete
    void delete(Image image);

    @Query("SELECT DISTINCT album from image")
    List<String> getAlbumsInDB();

    @Query("SELECT url from image " +
            "WHERE album = :album_name " +
            "LIMIT 1")
    String getFirstImage(String album_name);

    @Query("DELETE FROM image WHERE album = :album_name")
    void deleteAlbum(String album_name);
}
