package com.avelycure.photogallery.elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapterParameterImpl;

import java.util.List;

public class AlbumElementsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private AlbumElementsAdapter albumElementsAdapter;
    private AlbumsElementViewModel viewModel;
    private static String ALBUM = "Album";
    private static int PORTRAIT_COLUMNS_NUM = 3;
    private static int LANDSCAPE_COLUMNS_NUM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_elements__activity);

        String album = null;
        Bundle argument = getIntent().getExtras();
        if (argument != null)
            album = argument.get(ALBUM).toString();

        viewModel = ViewModelProviders.of(this).get(AlbumsElementViewModel.class);

        viewModel.init(album);

        rv = findViewById(R.id.album_elements_rv);

        albumElementsAdapter = new AlbumElementsAdapter(viewModel.getMutableLiveData().getValue(),
                new ImageAdapterParameterImpl(this));

        rv.setAdapter(albumElementsAdapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            rv.setLayoutManager(new GridLayoutManager(this, PORTRAIT_COLUMNS_NUM));
        else
            rv.setLayoutManager(new GridLayoutManager(this, LANDSCAPE_COLUMNS_NUM));

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