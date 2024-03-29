package com.avelycure.photogallery.home;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.about.MoreActivity;
import com.avelycure.photogallery.albums.AlbumsActivity;
import com.avelycure.photogallery.office.OfficeActivity;
import com.avelycure.photogallery.settings.SettingsActivity;
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.avelycure.photogallery.utils.ImageAdapterParameterImpl;
import com.avelycure.photogallery.utils.MySuggestionProvider;
import com.avelycure.photogallery.utils.NetworkUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Activity components
    private RecyclerView imageList;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    private NavigationView navigationView;
    private SearchRecentSuggestions suggestions;
    private ShimmerFrameLayout mFrameLayout;

    //Variables
    private boolean loading = true;
    private ImageAdapter imageAdapter;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    //ViewModel
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__activity);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.init();

        imageList = findViewById(R.id.rv_images);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRecyclerview();
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        homeViewModel.getCards().observe(this, new Observer<List<HomeCardModel>>() {
            @Override
            public void onChanged(List<HomeCardModel> cardModels) {
                imageAdapter.notifyDataSetChanged();
            }
        });

        homeViewModel.getFirstResponse().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mFrameLayout.setVisibility(View.GONE);
                imageList.setVisibility(View.VISIBLE);
            }
        });

        suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);

        mFrameLayout = findViewById(R.id.home_shimmer_layout);
    }

    /**
     * Setting SearchView
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mFrameLayout.startShimmer();
                mFrameLayout.setVisibility(View.VISIBLE);
                imageList.setVisibility(View.GONE);

                manageQuery(searchView.getQuery().toString());
                searchView.setFocusable(false);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    /**
     * This function should set layout manager to RecyclerView and control that it displays
     * actual information(infinite recyclerView)
     */
    private void setRecyclerview() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        imageList.setLayoutManager(linearLayoutManager);
        imageAdapter = new ImageAdapter(new ImageAdapterParameterImpl(this),
                homeViewModel.getCards().getValue(),
                new NetworkUtils(homeViewModel));
        imageList.setAdapter(imageAdapter);

        imageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 3 && pastVisibleItems != 0) {
                        loading = false;
                        homeViewModel.findMoreImages(searchView.getQuery().toString());
                    }
                    loading = true;
                }
            }
        });
    }

    /**
     * This function is called when user clicks on recent query suggestions. A
     *
     * @param intent activity gets it and calls manageQuery to get images
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String query = intent.getStringExtra(SearchManager.QUERY);
        searchView.setQuery(query, false);
        searchView.setFocusable(false);
        searchView.clearFocus();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mFrameLayout.startShimmer();
            mFrameLayout.setVisibility(View.VISIBLE);
            imageList.setVisibility(View.GONE);
            manageQuery(query);
        }
    }

    /**
     * This function is needed to delegate request to homeViewModel
     *
     * @param query is a tag by which FlickrApi will look for images
     */
    private void manageQuery(String query) {
        homeViewModel.createNewRequest(query);
        suggestions.saveRecentQuery(query, null);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
    }

    /**
     * This function is called to navigate between activities in drawerLayout
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.nav_albums:
                intent = new Intent(this, AlbumsActivity.class);
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
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
