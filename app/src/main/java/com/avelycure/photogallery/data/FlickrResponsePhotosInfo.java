package com.avelycure.photogallery.data;

import com.google.gson.annotations.SerializedName;

public class FlickrResponsePhotosInfo {
    @SerializedName("page")
    private String page;

    @SerializedName("pages")
    private String totalPages;

    @SerializedName("perpage")
    private String imagesPerPage;

    @SerializedName("total")
    private String totalImages;

    @SerializedName("photo")
    private FlickrResponseItem photo;
}
