package com.avelycure.photogallery.albums;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;

import java.util.ArrayList;
import java.util.List;

public class AlbumsElementViewModel extends ViewModel {

    private MutableLiveData<List<ImageInAlbum>> mutableLiveData;

    public LiveData<List<ImageInAlbum>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void init() {
        if (mutableLiveData != null)
            return;
        mutableLiveData = new MutableLiveData<>();
        AppDatabase db = App.getInstance().getDatabase();
        ImageDao imageDao = db.imageDao();
        Log.d("tag", "" + imageDao.getAll());
        List<Image> list1 = imageDao.getAll();
        List<ImageInAlbum> list2 = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            list2.add(new ImageInAlbum(list1.get(i).url));
        }
        mutableLiveData.setValue(list2);
    }
}
