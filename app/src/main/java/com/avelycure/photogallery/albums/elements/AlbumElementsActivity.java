package com.avelycure.photogallery.albums.elements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapter;
import com.avelycure.photogallery.utils.ImageAdapterImpl;

import java.util.List;

public class AlbumElementsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private AlbumElementsAdapter albumElementsAdapter;
    private AlbumsElementViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_elements);

        String album = null;
        Bundle argument = getIntent().getExtras();
        if (argument != null) {
            album = argument.get("Album").toString();
        }

        viewModel = ViewModelProviders.of(this).get(AlbumsElementViewModel.class);


        viewModel.init(album);

        rv = findViewById(R.id.album_elements_rv);

        albumElementsAdapter = new AlbumElementsAdapter(viewModel.getMutableLiveData().getValue(),
                new ImageAdapterImpl(this));

        rv.setAdapter(albumElementsAdapter);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        viewModel.getMutableLiveData().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> imageInAlbums) {
                albumElementsAdapter.notifyDataSetChanged();
            }
        });
    }
}