package com.avelycure.photogallery.album_elements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.Image;
import java.util.List;

public class AlbumsElementViewModel extends ViewModel {

    private MutableLiveData<List<Image>> mutableLiveData;

    public LiveData<List<Image>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void init(String album) {
        if (mutableLiveData != null)
            return;
        mutableLiveData = new MutableLiveData<>();
        AppDatabase db = App.getInstance().getDatabase();
        mutableLiveData.setValue(db.imageDao().getByAlbum(album));
    }
}
