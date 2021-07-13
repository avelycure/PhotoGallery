package com.avelycure.photogallery.albums.elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapter;
import com.avelycure.photogallery.utils.ImageAdapterImpl;

import java.util.List;

public class AlbumElementsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private AlbumElementsAdapter albumElementsAdapter;
    private AlbumsElementViewModel viewModel;
    private static String ALBUM = "Album";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_elements);

        String album = null;
        Bundle argument = getIntent().getExtras();
        if (argument != null) {
            album = argument.get(ALBUM).toString();
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

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}