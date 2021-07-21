package com.avelycure.photogallery.albums;

public class AlbumListModel {
    private String name;
    private String image;
    private boolean checked;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public AlbumListModel(String name, String image, boolean checked) {
        this.name = name;
        this.image = image;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isChecked() {
        return checked;
    }
}
