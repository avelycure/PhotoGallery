package com.avelycure.photogallery.utils;

import com.avelycure.photogallery.data.FlickrApi;
import com.avelycure.photogallery.data.images.FlickrResponseImage;
import com.avelycure.photogallery.data.user_info.FlickrResponsePerson;
import com.avelycure.photogallery.home.HomeCardModel;
import com.avelycure.photogallery.home.HomeViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is used to work with Network and make network requests
 */
public class NetworkUtils {
    private final String BASE_URL = "https://api.flickr.com";
    private final Retrofit mRetrofit;
    private HomeViewModel homeViewModel;

    public NetworkUtils(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //todo maybe do something with multithreading or javarx
    //todo add async request to server to get info about user
    public void makeRequest(String tag, int pageNum, List<HomeCardModel> cards) {
        mRetrofit
                .create(FlickrApi.class)
                .getImagesUrls(tag, pageNum)
                .enqueue(new Callback<FlickrResponseImage>() {
                    @Override
                    public void onResponse(Call<FlickrResponseImage> call, Response<FlickrResponseImage> response) {
                        FlickrResponseImage flickrResponseImage = response.body();
                        for (int i = 0; i < flickrResponseImage.getPhotos().getPhoto().size(); i++) {
                            cards.add(new HomeCardModel(createPictureAddress(flickrResponseImage, i),
                                    false,
                                    flickrResponseImage.getPhotos().getPhoto().get(i).getOwnerId()
                                    ));
                        }
                        homeViewModel.gotRequest(cards);
                    }

                    @Override
                    public void onFailure(Call<FlickrResponseImage> call, Throwable t) {}
                });
    }

    /**
     * This functions is needed to get picture address from json response
     * @param flickrResponseImage is a flickr response
     * @param i is number of picture in set of images in flickr response
     * @return picture url in internet
     */
    public String createPictureAddress(FlickrResponseImage flickrResponseImage, int i) {
        return "https://farm" + flickrResponseImage.getPhotos().getPhoto().get(i).getFarm() +
                ".staticflickr.com/" + flickrResponseImage.getPhotos().getPhoto().get(i).getServer() +
                "/" + flickrResponseImage.getPhotos().getPhoto().get(i).getPictureId() +
                "_" + flickrResponseImage.getPhotos().getPhoto().get(i).getSecret() + ".jpg";
    }
}