package com.avelycure.photogallery.utils;

import com.avelycure.photogallery.data.images.FlickrApi;
import com.avelycure.photogallery.data.images.FlickrResponse;
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
    public void makeRequest(String tag, int pageNum, List<HomeCardModel> cards) {
        mRetrofit
                .create(FlickrApi.class)
                .getImagesUrls(tag, pageNum)
                .enqueue(new Callback<FlickrResponse>() {
                    @Override
                    public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                        FlickrResponse flickrResponse = response.body();
                        for (int i = 0; i < flickrResponse.getPhotos().getPhoto().size(); i++)
                            cards.add(new HomeCardModel(createPictureAddress(flickrResponse, i), false,
                                    flickrResponse.getPhotos().getPhoto().get(i).getOwnerId()));
                        homeViewModel.gotRequest(cards);
                    }

                    @Override
                    public void onFailure(Call<FlickrResponse> call, Throwable t) {
                    }
                });
    }

    public String createPictureAddress(FlickrResponse flickrResponse, int i){
        return "https://farm" + flickrResponse.getPhotos().getPhoto().get(i).getFarm() +
                ".staticflickr.com/" + flickrResponse.getPhotos().getPhoto().get(i).getServer() +
                "/" + flickrResponse.getPhotos().getPhoto().get(i).getPictureId() +
                "_" + flickrResponse.getPhotos().getPhoto().get(i).getSecret() + ".jpg";
    }

}