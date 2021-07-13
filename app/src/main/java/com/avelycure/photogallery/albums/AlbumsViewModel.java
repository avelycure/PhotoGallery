package com.avelycure.photogallery.albums;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.List;

public class AlbumsViewModel extends ViewModel {
    private MutableLiveData<List<AlbumListModel>> listMutableLiveData;

    public LiveData<List<AlbumListModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void init(){
        if (listMutableLiveData!=null)
            return;
        listMutableLiveData = new MutableLiveData<>();
        List<AlbumListModel> arrayList = new ArrayList<>();

        arrayList.add(new AlbumListModel("album1", "mountain"));
        arrayList.add(new AlbumListModel("album2", "forest"));
        arrayList.add(new AlbumListModel("album3", "lake"));

        listMutableLiveData.setValue(arrayList);
    }

    public void addAlbum(String name, String image){
        List<AlbumListModel> list = listMutableLiveData.getValue();
        list.add(new AlbumListModel(name, image));
        listMutableLiveData.setValue(list);
    }
}
