package com.avelycure.photogallery.albums;

public class AlbumListModel {
    private String name;
    private String image;
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

    public AlbumListModel(String name, String image) {
        this.name = name;
        this.image = image;
        this.checked = false;
        toDelete = false;
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
