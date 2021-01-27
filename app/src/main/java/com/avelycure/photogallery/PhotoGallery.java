package com.avelycure.photogallery;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;

import utils.ImageAdapter;
import utils.NetworkUtils;
import utils.PhotoGalleryDatabaseHelper;

public class PhotoGallery extends AppCompatActivity {

    private RecyclerView imageList;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private LinearLayoutManager linearLayoutManager;

    private Context context;
    private NetworkUtils networkUtils;
    private ImageAdapter imageAdapter;

    private boolean loading = true;
    private PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_gallery);

        context = PhotoGallery.this;
        photoGalleryDatabaseHelper = new PhotoGalleryDatabaseHelper(context);
        networkUtils = new NetworkUtils(this, photoGalleryDatabaseHelper);

        imageList = findViewById(R.id.rv_images);

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                photoGalleryDatabaseHelper.clearDatabase();
                try {
                    networkUtils.updateJSONArray(searchView.getQuery().toString(), 1);
                    imageAdapter = new ImageAdapter(context, photoGalleryDatabaseHelper);
                    imageList.setAdapter(imageAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        imageList.setLayoutManager(linearLayoutManager);

        imageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount && pastVisibleItems != 0) {
                        loading = false;
                        try {
                            ImageAdapter.addPage();
                            networkUtils.updateJSONArray(searchView.getQuery().toString(), ImageAdapter.getCurrentPage());
                            ImageAdapter.addRecyclerViewSize();
                            imageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loading = true;
                }
            }
        });
    }
}
