package com.avelycure.photogallery.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<HomeCardModel>> cards;
    private NetworkUtils networkUtils;
    private int currentVisiblePage;

    public LiveData<List<HomeCardModel>> getCards() {
        return cards;
    }

    public void init() {
        if (cards != null)
            return;

        networkUtils = new NetworkUtils(this);
        cards = new MutableLiveData<>();
        List<HomeCardModel> homeCardModelList = new ArrayList<>();
        cards.setValue(homeCardModelList);
        currentVisiblePage = 1;
    }

    public void findMoreImages(String query) {
        currentVisiblePage++;
        networkUtils.makeRequest(query, currentVisiblePage, cards.getValue());
    }

    public void createNewRequest(String query) {
        currentVisiblePage = 1;
        List<HomeCardModel> homeCardModels = cards.getValue();
        homeCardModels.clear();
        networkUtils.makeRequest(query, currentVisiblePage, homeCardModels);
    }

    public void gotRequest(List<HomeCardModel> homeCardModels){
        cards.setValue(homeCardModels);
    }
}
