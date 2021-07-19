package com.avelycure.photogallery.data.images;

import com.avelycure.photogallery.data.images.FlickrResponseItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrResponsePhotosInfo {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("perpage")
    private int imagesPerPage;

    @SerializedName("total")
    private int totalImages;

    @SerializedName("photo")
    private List<FlickrResponseItem> photo;

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
}
