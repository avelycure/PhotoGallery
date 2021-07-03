package com.avelycure.photogallery.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.albums.AlbumsActivity;
import com.avelycure.photogallery.feedback.FeedbackActivity;
import com.avelycure.photogallery.more.MoreActivity;
import com.avelycure.photogallery.ofiice.OfficeActivity;
import com.avelycure.photogallery.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;

import com.avelycure.photogallery.utils.ImageAdapter;
import com.avelycure.photogallery.utils.NetworkUtils;
import com.avelycure.photogallery.utils.PhotoGalleryDatabaseHelper;

public class PhotoGallery extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Activity components
    private RecyclerView imageList;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;

    //Variables
    Set<Long> likedPhotos;
    private Context context;
    NavigationView navigationView;
    private boolean loading = true;
    private NetworkUtils networkUtils;
    private ImageAdapter imageAdapter;
    private PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_gallery);

        findActivityComponents();

        setParameters();

        setToolbar();

        setRecyclerview();
        }

    //This function should find all components, which I used in PhotoGalleryActivity
    private void findActivityComponents() {
        imageList = findViewById(R.id.rv_images);
        searchView = findViewById(R.id.searchView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }


    //We need this function to set some parameters
    private void setParameters() {
        context = PhotoGallery.this;
        photoGalleryDatabaseHelper = new PhotoGalleryDatabaseHelper(context);
        networkUtils = NetworkUtils.getNetworkUtils(photoGalleryDatabaseHelper);
        navigationView.setNavigationItemSelectedListener(this);
        likedPhotos = new HashSet<Long>();
    }

    private void setToolbar() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                photoGalleryDatabaseHelper.clearDatabase();
                try {
                    networkUtils.updateJSONArray(searchView.getQuery().toString(), 1);
                    imageAdapter = new ImageAdapter(context, photoGalleryDatabaseHelper, likedPhotos, imageAdapter);
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

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    //This function should set layout manager to RecylerView and control that it displays necessary information
    private void setRecyclerview() {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id){
            case R.id.nav_albums:
                intent = new Intent(this, AlbumsActivity.class);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this, FeedbackActivity.class);
                break;
            case R.id.nav_office:
                intent = new Intent(this, OfficeActivity.class);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.nav_about:
                intent = new Intent(this, MoreActivity.class);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
