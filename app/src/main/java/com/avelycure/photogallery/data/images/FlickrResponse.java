package com.avelycure.photogallery.data.images;

import com.google.gson.annotations.SerializedName;

public class FlickrResponse {
    @SerializedName("photos")
    private FlickrResponsePhotosInfo photos;

    @SerializedName("stat")
    private String status;

    public FlickrResponsePhotosInfo getPhotos() {
        return photos;
    }

    public String getStatus() {
        return status;
    }
}
