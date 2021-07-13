package com.avelycure.photogallery.albums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import com.avelycure.photogallery.R;

public class AlbumsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private AlbumsViewModel albumsViewModel;
    private AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);
        albumsViewModel.init();

        rv = findViewById(R.id.albums_rv);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        else
            rv.setLayoutManager(new GridLayoutManager(this, 4));

        albumAdapter = new AlbumAdapter(albumsViewModel.getListMutableLiveData().getValue(), new AlbumAdapterParameterImpl(this));

        rv.setAdapter(albumAdapter);
    }
}