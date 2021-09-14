package com.avelycure.photogallery.data;

import com.avelycure.photogallery.data.images.FlickrResponseImage;
import com.avelycure.photogallery.data.user_info.FlickrResponsePerson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface that provides methods to access Flickr.com. Base url is set in place of creating
 * retrofit object
 */
public interface FlickrApi {
    /**
     * Format of the response
     */
    String FORMAT_STRING = "&format=json";

    /**
     * Deletes extra nesting
     */
    String NO_ENVELOPE = "&nojsoncallback=1";

    /**
     * Part of flickr api address
     */
    String FLICKR_API = "/services/rest/?method=";

    /**
     * Obligatory param to the request, which helps to identify this app
     */
    String API_KEY_STRING = "&api_key=17c6829dc9c675db355315a1cab4e9b4";

    String IMPORTED_PHOTOS_PER_REQUEST = "&per_page=30";


    String GET_PHOTO_METHOD = "flickr.photos.search";
    String GET_USER_INFO = "flickr.people.getInfo";

    /**
     * This method is called to got a set of images by tag from flick.com.
     * @param tag is used to choose images by tag
     * @param pageNum is used to choose from which page take photos.
     * @return Call<FlickrResponseImage> is a callback which is called when retrofit gets result
     */
    @GET(FLICKR_API + GET_PHOTO_METHOD + API_KEY_STRING + IMPORTED_PHOTOS_PER_REQUEST +
            FORMAT_STRING + NO_ENVELOPE)
    Call<FlickrResponseImage> getImagesUrls(@Query("tags") String tag, @Query("page") int pageNum);

    /**
     * This method is called to get description of the flickr user
     * @param userId is needed to identify user
     * @return Call<FlickrResponseImage> is a callback which is called when retrofit gets result
     */
    @GET(FLICKR_API + GET_USER_INFO + API_KEY_STRING + FORMAT_STRING + NO_ENVELOPE)
    Call<FlickrResponsePerson> getPersonInfo(@Query("user_id") String userId);
}
