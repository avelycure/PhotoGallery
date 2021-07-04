package com.avelycure.photogallery.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.utils.CardModel;
import com.avelycure.photogallery.utils.NetworkUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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

    public void createNewRequest(String query) {
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
    }

    public void findMoreImages(String query){
        try {
            currentVisiblePage++;
            networkUtils.updateJSONArray(query, currentVisiblePage, cards.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
