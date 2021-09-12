package com.avelycure.photogallery.data.user_info;

import com.google.gson.annotations.SerializedName;

public class FlickrResponsePerson {
    @SerializedName("person")
    private FlickrResponseItemPerson person;

    @SerializedName("stat")
    private String status;

    public FlickrResponseItemPerson getPerson() {
        return person;
    }

    public String getStatus() {
        return status;
    }
}
