package com.avelycure.photogallery.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public Image(String album, String url) {
        this.album = album;
        this.url = url;
    }

    public String album;

    public String url;
}
