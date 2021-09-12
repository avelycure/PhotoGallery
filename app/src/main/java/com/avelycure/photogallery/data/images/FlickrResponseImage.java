package com.avelycure.photogallery.data.images;

import com.google.gson.annotations.SerializedName;

public class FlickrResponseImage {
    @SerializedName("photos")
    private FlickrResponsePhotosInfoImage photos;

    @SerializedName("stat")
    private String status;

    public FlickrResponsePhotosInfoImage getPhotos() {
        return photos;
    }

    public String getStatus() {
        return status;
    }
}
