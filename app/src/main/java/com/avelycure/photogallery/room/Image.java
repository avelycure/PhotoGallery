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

    @ColumnInfo(typeAffinity = TEXT)
    public String author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Image(String album, String url, String author) {
        this.album = album;
        this.url = url;
        this.author = author;
    }
}
