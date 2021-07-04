package com.avelycure.photogallery.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.utils.CardModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<CardModel>> cards;

    public MutableLiveData<List<CardModel>> getCards() {
        return cards;
    }

    public void init() {
        if (cards != null)
            return;

        cards = new MutableLiveData<>();
        List<CardModel> cardModelList = new ArrayList<>();
        cards.setValue(cardModelList);
    }
}
