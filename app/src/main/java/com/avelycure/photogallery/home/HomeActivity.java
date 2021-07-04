package com.avelycure.photogallery.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.albums.AlbumsActivity;
import com.avelycure.photogallery.feedback.FeedbackActivity;
import com.avelycure.photogallery.more.MoreActivity;
import com.avelycure.photogallery.ofiice.OfficeActivity;
import com.avelycure.photogallery.settings.SettingsActivity;
import com.avelycure.photogallery.utils.CardModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.avelycure.photogallery.utils.ImageAdapter;
import com.avelycure.photogallery.utils.NetworkUtils;
import com.avelycure.photogallery.utils.PhotoGalleryDatabaseHelper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Activity components
    private RecyclerView imageList;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    private NavigationView navigationView;

    //Variables
    private int pageNum = 1;
    private boolean loading = true;
    private NetworkUtils networkUtils;
    private ImageAdapter imageAdapter;
    private PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_gallery);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.init();
        findActivityComponents();
        setRecyclerview();

        setParameters();

        setToolbar();

        homeViewModel.getCards().observe(this, new Observer<List<CardModel>>() {
            @Override
            public void onChanged(List<CardModel> cardModels) {
                imageAdapter.notifyDataSetChanged();
            }
        });
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
        photoGalleryDatabaseHelper = new PhotoGalleryDatabaseHelper(this);
        networkUtils = new NetworkUtils();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setToolbar() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    List<CardModel> cardModels = homeViewModel.getCards().getValue();
                    cardModels.clear();
                    networkUtils.updateJSONArray(searchView.getQuery().toString(), 1, cardModels);
                    homeViewModel.getCards().setValue(cardModels);
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
        imageAdapter = new ImageAdapter(this, photoGalleryDatabaseHelper, homeViewModel.getCards().getValue());
        imageList.setAdapter(imageAdapter);

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
                            pageNum++;
                            networkUtils.updateJSONArray(searchView.getQuery().toString(), pageNum, homeViewModel.getCards().getValue());
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
