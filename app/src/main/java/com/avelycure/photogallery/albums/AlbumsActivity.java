package com.avelycure.photogallery.albums;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.utils.ImageAdapterParameterImpl;

import java.util.List;

public class AlbumsActivity extends AppCompatActivity {

    private static int PORTRAIT_COLUMNS_NUM = 3;
    private static int LANDSCAPE_COLUMNS_NUM = 4;

    private RecyclerView rv;
    private AlbumsViewModel albumsViewModel;
    private AlbumAdapter albumAdapter;
    private Toolbar toolbar;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album__activity);

        albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);
        albumsViewModel.init();
        albumsViewModel.getListMutableLiveData().observe(this, new Observer<List<AlbumListModel>>() {
            @Override
            public void onChanged(List<AlbumListModel> albumListModels) {
                albumAdapter.notifyDataSetChanged();
            }
        });

        rv = findViewById(R.id.albums_rv);
        toolbar = findViewById(R.id.toolbar);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            rv.setLayoutManager(new GridLayoutManager(this, PORTRAIT_COLUMNS_NUM));
        else
            rv.setLayoutManager(new GridLayoutManager(this, LANDSCAPE_COLUMNS_NUM));

        albumAdapter = new AlbumAdapter(albumsViewModel.getListMutableLiveData().getValue(), new ImageAdapterParameterImpl(this));

        rv.setAdapter(albumAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("tag","" + albumAdapter.isChbIsVisible());
        switch (item.getItemId()) {
            case android.R.id.home:
                if (albumAdapter.isChbIsVisible()) {
                    albumAdapter.switchSelection(false);
                switchActionDeleteVisibility(false);
                }else
                    finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (albumAdapter.isChbIsVisible()) {
            albumAdapter.switchSelection(false);
            switchActionDeleteVisibility(false);
        }else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public void switchActionDeleteVisibility(boolean visibility) {
        menu.findItem(R.id.home_action_delete).setVisible(visibility);
    }
}