package com.avelycure.photogallery.albums;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.albums.data.AlbumListModel;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.ImageDao;

import java.util.ArrayList;
import java.util.List;

public class AlbumsViewModel extends ViewModel {
    private MutableLiveData<List<AlbumListModel>> listMutableLiveData;
    private ImageDao imageDao;
    private AppDatabase db;

    public LiveData<List<AlbumListModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void init() {
        if (listMutableLiveData != null)
            return;
        listMutableLiveData = new MutableLiveData<>();
        List<AlbumListModel> arrayList = new ArrayList<>();

        db = App.getInstance().getDatabase();
        imageDao = db.imageDao();

        List<String> albumList = imageDao.getAlbumsInDB();

        for (int i = 0; i < albumList.size(); i++)
            arrayList.add(new AlbumListModel(albumList.get(i), "some image"));

        listMutableLiveData.setValue(arrayList);
    }

    public void addAlbum(String name, String image) {
        List<AlbumListModel> list = listMutableLiveData.getValue();
        list.add(new AlbumListModel(name, image));
        listMutableLiveData.setValue(list);
    }
}
