package com.avelycure.photogallery.data;

import com.avelycure.photogallery.data.images.FlickrResponseImage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
    String FORMAT_STRING = "&format=json";
    String NO_ENVELOPE = "&nojsoncallback=1";
    String FLICKR_API = "/services/rest/?method=";
    String IMPORTED_PHOTOS_PER_REQUEST = "&per_page=30";
    String FLICKR_GET_PHOTO_METHOD = "flickr.photos.search";
    String API_KEY_STRING = "&api_key=17c6829dc9c675db355315a1cab4e9b4";

    @GET(FLICKR_API + FLICKR_GET_PHOTO_METHOD + API_KEY_STRING + IMPORTED_PHOTOS_PER_REQUEST + FORMAT_STRING + NO_ENVELOPE)
    Call<FlickrResponseImage> getImagesUrls(@Query("tags") String tag, @Query("page") int pageNum);


}
