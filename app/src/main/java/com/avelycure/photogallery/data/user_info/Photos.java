package com.avelycure.photogallery.data.user_info;

import com.google.gson.annotations.SerializedName;

public class Photos {
    @SerializedName("firstdatetaken")
    private FirstDateTaken firstDateTaken;

    @SerializedName("firstdate")
    private FirstDate firstDate;

    @SerializedName("count")
    private Count count;
}
