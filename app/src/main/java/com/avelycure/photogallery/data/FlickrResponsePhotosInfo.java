package com.avelycure.photogallery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrResponsePhotosInfo {
    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getImagesPerPage() {
        return imagesPerPage;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public List<FlickrResponseItem> getPhoto() {
        return photo;
    }

    @SerializedName("perpage")
    private int imagesPerPage;

    @SerializedName("total")
    private int totalImages;

    @SerializedName("photo")
    private List<FlickrResponseItem> photo;
}
