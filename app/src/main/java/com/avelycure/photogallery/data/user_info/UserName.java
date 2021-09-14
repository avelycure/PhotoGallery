package com.avelycure.photogallery.data.user_info;

import com.google.gson.annotations.SerializedName;

public class UserName {
    @SerializedName("_content")
    private String name;

    public String getName() {
        return name;
    }
}
