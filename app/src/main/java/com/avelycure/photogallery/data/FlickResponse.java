package com.avelycure.photogallery.data;

import com.google.gson.annotations.SerializedName;

public class FlickResponse {
    @SerializedName("photos")
    private FlickrResponsePhotosInfo photos;

    @SerializedName("stat")
    private String status;
}
