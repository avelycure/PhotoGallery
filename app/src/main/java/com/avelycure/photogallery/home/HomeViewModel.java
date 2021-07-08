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

        networkUtils = new NetworkUtils(this);
        cards = new MutableLiveData<>();
        List<CardModel> cardModelList = new ArrayList<>();
        cards.setValue(cardModelList);
        currentVisiblePage = 1;
    }

    public void findMoreImages(String query) {
        currentVisiblePage++;
        networkUtils.makeRequest(query, currentVisiblePage, cards.getValue());
    }

    public void createNewRequest(String query) {
        currentVisiblePage = 1;
        List<CardModel> cardModels = cards.getValue();
        cardModels.clear();
        networkUtils.makeRequest(query, currentVisiblePage, cardModels);
    }

    public void gotRequest(List<CardModel> cardModels){
        cards.setValue(cardModels);
    }
}
