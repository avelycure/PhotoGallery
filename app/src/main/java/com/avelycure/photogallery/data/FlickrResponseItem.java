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
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;

    public String getPictureId() {
        return pictureId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public int getIsFamily() {
        return isFamily;
    }
}
