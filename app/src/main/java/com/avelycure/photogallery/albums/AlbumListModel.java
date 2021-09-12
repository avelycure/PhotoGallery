package com.avelycure.photogallery.albums;

public class AlbumListModel {
    private String name;
    private String imgUrl;
    private boolean checked;
    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public AlbumListModel(String name, String imgUrl) {
        this.name = name;
        this.checked = false;
        this.imgUrl = imgUrl;
        toDelete = false;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
