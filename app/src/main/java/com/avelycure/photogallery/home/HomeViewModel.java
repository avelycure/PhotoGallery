package com.avelycure.photogallery.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.data.FlickrResponse;
import com.avelycure.photogallery.utils.CardModel;
import com.avelycure.photogallery.utils.NetworkUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<CardModel>> cards;
    private NetworkUtils networkUtils;
    private int currentVisiblePage;

    public LiveData<List<CardModel>> getCards() {
        return cards;
    }

    public void init() {
        if (cards != null)
            return;

        networkUtils = new NetworkUtils();
        cards = new MutableLiveData<>();
        List<CardModel> cardModelList = new ArrayList<>();
        cards.setValue(cardModelList);
        currentVisiblePage = 1;
    }

    public void findMoreImages(String query) {
        try {
            currentVisiblePage++;
            networkUtils.updateJSONArray(query, currentVisiblePage, cards.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createNewRequest(String query) {
        Log.d("mytag", "create new request");
        currentVisiblePage = 1;
        List<CardModel> cardModels = cards.getValue();
        cardModels.clear();
        try {
            networkUtils.updateJSONArray(query, 1, cardModels);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        cards.setValue(cardModels);


        Log.d("mytag", "started");
        networkUtils.makeRequestUsingRetrofit();

        networkUtils
                .getFlickrApi()
                .getImagesUrls("dog", 1)
                .enqueue(new Callback<FlickrResponse>() {
                    @Override
                    public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                        Log.d("mytag", "good");

                        FlickrResponse flickrResponse = response.body();
                        Log.d("mytag", flickrResponse.getStatus());
                        Log.d("mytag", "total" + flickrResponse.getPhotos().getTotalImages());
                    }

                    @Override
                    public void onFailure(Call<FlickrResponse> call, Throwable t) {
                        Log.d("mytag", "bad");
                        Log.d("mytag", t.getMessage());
                    }
                });
    }
}
