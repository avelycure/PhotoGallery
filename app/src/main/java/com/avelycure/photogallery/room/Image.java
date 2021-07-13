package com.avelycure.photogallery.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static androidx.room.ColumnInfo.TEXT;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(typeAffinity = TEXT)
    public String album;

    @ColumnInfo(typeAffinity = TEXT)
    public String url;

    public Image(String album, String url) {
        this.album = album;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getUrl() {
        return url;
    }
}
