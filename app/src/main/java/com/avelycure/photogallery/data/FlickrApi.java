package com.avelycure.photogallery.data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrApi {
    @GET("./")
    Call<FlickResponse> getImagesUrls();
}
