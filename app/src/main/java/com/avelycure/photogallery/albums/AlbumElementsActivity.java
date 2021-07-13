package com.avelycure.photogallery.albums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.avelycure.photogallery.R;

import java.util.List;

public class AlbumElementsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private AlbumElementsAdapter albumElementsAdapter;
    private AlbumsElementViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_elements);

        viewModel = ViewModelProviders.of(this).get(AlbumsElementViewModel.class);
        viewModel.init();

        rv = findViewById(R.id.album_elements_rv);

        albumElementsAdapter = new AlbumElementsAdapter(viewModel.getMutableLiveData().getValue(),
                new AlbumAdapterParameterImpl(this));

        rv.setAdapter(albumElementsAdapter);
        rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        viewModel.getMutableLiveData().observe(this, new Observer<List<ImageInAlbum>>() {
            @Override
            public void onChanged(List<ImageInAlbum> imageInAlbums) {
                albumElementsAdapter.notifyDataSetChanged();
            }
        });
    }
}