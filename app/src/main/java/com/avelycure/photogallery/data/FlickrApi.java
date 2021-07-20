package com.avelycure.photogallery.data;

import com.avelycure.photogallery.data.images.FlickrResponseImage;
import com.avelycure.photogallery.data.user_info.FlickrResponsePerson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
    String FORMAT_STRING = "&format=json";
    String NO_ENVELOPE = "&nojsoncallback=1";
    String FLICKR_API = "/services/rest/?method=";
    String API_KEY_STRING = "&api_key=17c6829dc9c675db355315a1cab4e9b4";

    String IMPORTED_PHOTOS_PER_REQUEST = "&per_page=30";
    String FLICKR_GET_PHOTO_METHOD = "flickr.photos.search";

    String FLICKR_GET_USER_INFO_METHOD = "flickr.people.getInfo";

    @GET(FLICKR_API + FLICKR_GET_PHOTO_METHOD + API_KEY_STRING + IMPORTED_PHOTOS_PER_REQUEST +
            FORMAT_STRING + NO_ENVELOPE)
    Call<FlickrResponseImage> getImagesUrls(@Query("tags") String tag, @Query("page") int pageNum);

    @GET(FLICKR_API + FLICKR_GET_USER_INFO_METHOD + API_KEY_STRING + FORMAT_STRING + NO_ENVELOPE)
    Call<FlickrResponsePerson> getPersonInfo(@Query("user_id") String userId);
}
