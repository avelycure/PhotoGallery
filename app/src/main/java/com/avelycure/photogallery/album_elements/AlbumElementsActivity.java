package com.avelycure.photogallery.album_elements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapterParameterImpl;

import java.util.List;

/**
 * This activity represents album elements
 */
public class AlbumElementsActivity extends AppCompatActivity {
    private static final String ALBUM = "Album";
    private static final int PORTRAIT_COLUMNS_NUM = 3;
    private static final int LANDSCAPE_COLUMNS_NUM = 4;

    private RecyclerView rv;
    private AlbumElementsAdapter albumElementsAdapter;
    private AlbumsElementViewModel viewModel;
    private Toolbar toolbar;
    private Menu menu;

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
        viewModel.initMode();

        rv = findViewById(R.id.album_elements_rv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        albumElementsAdapter = new AlbumElementsAdapter(
                viewModel.getMutableLiveData().getValue(),
                new ImageAdapterParameterImpl(this),
                viewModel);
        rv.setAdapter(albumElementsAdapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            rv.setLayoutManager(new StaggeredGridLayoutManager(PORTRAIT_COLUMNS_NUM, StaggeredGridLayoutManager.VERTICAL));
        else
            rv.setLayoutManager(new StaggeredGridLayoutManager(LANDSCAPE_COLUMNS_NUM, StaggeredGridLayoutManager.HORIZONTAL));

        viewModel.getMutableLiveData().observe(this, new Observer<List<AlbumElementListModel>>() {
            @Override
            public void onChanged(List<AlbumElementListModel> imageInAlbums) {
                albumElementsAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getEditorModeEnabled().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                albumElementsAdapter.switchSelection(aBoolean);
                switchActionDeleteVisibility(aBoolean);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (albumElementsAdapter.isChbIsVisible()) {
                    albumElementsAdapter.switchSelection(false);
                    switchActionDeleteVisibility(false);
                } else
                    finish();
                return true;
            case R.id.albums_action_delete:
                viewModel.deletePicture();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * If user is in Editor Mode then first call to backPressed only lead to disable checkboxes
     */
    @Override
    public void onBackPressed() {
        if (albumElementsAdapter.isChbIsVisible()) {
            albumElementsAdapter.switchSelection(false);
            switchActionDeleteVisibility(false);
        } else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public void switchActionDeleteVisibility(boolean visibility) {
        if (menu != null)
            menu.findItem(R.id.albums_action_delete).setVisible(visibility);
    }
}