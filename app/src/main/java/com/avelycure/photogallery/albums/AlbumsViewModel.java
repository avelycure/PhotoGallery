package com.avelycure.photogallery.albums;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.ImageDao;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for AlbumActivity
 */
public class AlbumsViewModel extends ViewModel {
    private MutableLiveData<List<AlbumListModel>> listMutableLiveData;
    private ImageDao imageDao;
    private AppDatabase db;
    private MutableLiveData<Boolean> editorModeEnabled;

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
            arrayList.add(new AlbumListModel(albumList.get(i), imageDao.getFirstImage(albumList.get(i))));

        listMutableLiveData.setValue(arrayList);
    }

    public LiveData<Boolean> getEditorModeEnabled() {
        return editorModeEnabled;
    }

    public void initMode() {
        if (editorModeEnabled != null)
            return;
        editorModeEnabled = new MutableLiveData<>();
        editorModeEnabled.setValue(false);
    }

    /**
     * This function is called when user wants to delete album
     */
    public void deleteAlbum() {
        List<AlbumListModel> listWithoutDeletedAlbums = listMutableLiveData.getValue();
        for (int i = 0; i < listWithoutDeletedAlbums.size(); i++) {
            if (listWithoutDeletedAlbums.get(i).isChecked()) {
                imageDao.deleteAlbum(listWithoutDeletedAlbums.get(i).getName());
                listWithoutDeletedAlbums.get(i).setToDelete(true);
            }
        }

        for(AlbumListModel album: listWithoutDeletedAlbums){
            if (album.isToDelete())
                listWithoutDeletedAlbums.remove(album);
        }

        listMutableLiveData.setValue(listWithoutDeletedAlbums);
        editorModeEnabled.setValue(false);
    }
}
