package com.avelycure.photogallery.albums.data;

public class AlbumListModel {
    private String name;
    private String image;

    public AlbumListModel(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
