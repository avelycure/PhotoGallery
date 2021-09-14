package com.avelycure.photogallery.album_elements;

public class AlbumElementListModel {
    private boolean checked;
    private long id;
    private String url;
    private String album;
    private String author;

    public long getId() {
        return id;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public AlbumElementListModel(long id, String url, String album, String author) {
        this.id = id;
        this.url = url;
        this.album = album;
        this.author = author;
        checked = false;
    }

    public String getAlbum() {
        return album;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }
}
