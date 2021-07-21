package com.avelycure.photogallery.album_elements;

public class AlbumElementListModel {
    private boolean checked;
    private long id;
    private String url;
    private String album;

    public long getId() {
        return id;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public AlbumElementListModel(long id, String url, String album) {
        this.id = id;
        this.url = url;
        this.album = album;
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
}
