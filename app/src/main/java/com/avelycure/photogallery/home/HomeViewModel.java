package com.avelycure.photogallery.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for HomeActivity
 */
public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<HomeCardModel>> cards;
    private NetworkUtils networkUtils;
    private int currentVisiblePage;
    private MutableLiveData<Boolean> gotFirstSetOfImages;

    public LiveData<List<HomeCardModel>> getCards() {
        return cards;
    }

    public LiveData<Boolean> getFirstResponse() {
        return gotFirstSetOfImages;
    }

    //be careful with gotFirstSetOfImages
    public void init() {
        if (cards != null || gotFirstSetOfImages != null)
            return;

        networkUtils = new NetworkUtils(this);
        cards = new MutableLiveData<>();

        gotFirstSetOfImages = new MutableLiveData<>();
        gotFirstSetOfImages.setValue(false);

        List<HomeCardModel> homeCardModelList = new ArrayList<>();
        cards.setValue(homeCardModelList);
        currentVisiblePage = 1;
    }

    /**
     * When user scrolls recyclerView without changing tag, we just increase pageNum
     *
     * @param query is a tag to choose images
     */
    public void findMoreImages(String query) {
        currentVisiblePage++;
        networkUtils.getImages(query, currentVisiblePage, cards.getValue());
    }

    /**
     * Is calles when user wants to find images in first time
     *
     * @param query is a tag to choose images
     */
    public void createNewRequest(String query) {
        currentVisiblePage = 1;
        List<HomeCardModel> homeCardModels = cards.getValue();
        homeCardModels.clear();
        networkUtils.getImages(query, currentVisiblePage, homeCardModels);
    }

    /**
     * This function is a callback which is called when images are got by retrofit and are ready to
     * be displayed
     *
     * @param homeCardModels
     */
    public void gotRequest(List<HomeCardModel> homeCardModels) {
        gotFirstSetOfImages.postValue(true);
        cards.setValue(homeCardModels);
    }
}
