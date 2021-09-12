package com.avelycure.photogallery.album_elements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.albums.AlbumListModel;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;

import java.util.ArrayList;
import java.util.List;

public class AlbumsElementViewModel extends ViewModel {

    private MutableLiveData<List<AlbumElementListModel>> mutableLiveData;
    private AppDatabase db;
    private ImageDao imageDao;
    private MutableLiveData<Boolean> editorModeEnabled;

    public LiveData<List<AlbumElementListModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void init(String album) {
        if (mutableLiveData != null)
            return;

        mutableLiveData = new MutableLiveData<>();
        List<AlbumElementListModel> temp = new ArrayList<>();
        db = App.getInstance().getDatabase();
        imageDao = db.imageDao();
        List<Image> list = imageDao.getByAlbum(album);
        for (int i = 0; i < list.size(); i++)
            temp.add(new AlbumElementListModel(
                    list.get(i).id,
                    list.get(i).url,
                    list.get(i).album)
            );
        mutableLiveData.setValue(temp);
    }

    public void initMode() {
        if (editorModeEnabled != null)
            return;
        editorModeEnabled = new MutableLiveData<>();
        editorModeEnabled.setValue(false);
    }

    public LiveData<Boolean> getEditorModeEnabled() {
        return editorModeEnabled;
    }

    public void deletePicture() {
        List<AlbumElementListModel> listWithoutDeletedImages = mutableLiveData.getValue();
        for (int i = 0; i < listWithoutDeletedImages.size(); i++)
            if (listWithoutDeletedImages.get(i).isChecked()) {
                Image image = new Image(listWithoutDeletedImages.get(i).getAlbum(), listWithoutDeletedImages.get(i).getUrl());
                image.setId(listWithoutDeletedImages.get(i).getId());
                imageDao.delete(image);
                listWithoutDeletedImages.remove(i);
            }
        mutableLiveData.setValue(listWithoutDeletedImages);
        editorModeEnabled.setValue(false);
    }
}
