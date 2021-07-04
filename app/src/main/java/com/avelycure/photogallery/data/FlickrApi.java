package com.avelycure.photogallery.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=17c6829dc9c675db355315a1cab4e9b4&per_page=30&format=json")
    Call<FlickResponse> getImagesUrls(@Query("tags") String tag, @Query("page") int pageNum);
}
