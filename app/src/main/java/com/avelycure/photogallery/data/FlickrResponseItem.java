package com.avelycure.photogallery.data;

import com.google.gson.annotations.SerializedName;

public class FlickrResponseItem {
    @SerializedName("id")
    private String pictureId;

    @SerializedName("owner")
    private String ownerId;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private int farm;

    @SerializedName("title")
    private String title;

    @SerializedName("ispublic")
    private boolean isPublic;

    @SerializedName("isfriend")
    private boolean isFriend;

    @SerializedName("isfamily")
    private boolean isFamily;
}
