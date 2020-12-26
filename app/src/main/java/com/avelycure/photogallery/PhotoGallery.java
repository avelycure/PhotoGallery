package com.avelycure.photogallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import utils.ImageAdapter;
import utils.NetworkUtils;

public class PhotoGallery extends AppCompatActivity {

    private Button btnSendRequest;
    private ImageView imageView;
    private EditText editTextTag;
    private RecyclerView imageList;

    private NetworkUtils networkUtils;
    private ImageAdapter imageAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Context context;
    private final int COLOMNS_NUM_IN_RECYCLER_VIEW = 2;
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_gallery);

        context = PhotoGallery.this;
        networkUtils = new NetworkUtils();

        btnSendRequest = findViewById(R.id.btn_request);
        editTextTag = findViewById(R.id.edt_tag);
        imageList = findViewById(R.id.rv_images);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(COLOMNS_NUM_IN_RECYCLER_VIEW, StaggeredGridLayoutManager.VERTICAL);
        imageList.setLayoutManager(staggeredGridLayoutManager);

        imageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = staggeredGridLayoutManager.getChildCount();
                totalItemCount = staggeredGridLayoutManager.getItemCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }
                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount && pastVisibleItems!= 0) {
                        loading = false;
                        try {
                            ImageAdapter.addPage();
                            networkUtils.updateJSONArray(editTextTag.getText().toString(), ImageAdapter.getCurrentPage());
                            imageAdapter.notifyDataSetChanged();
                            ImageAdapter.addRecyclerViewSize();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loading = true;
                }
            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View view){
                try {
                    networkUtils.updateJSONArray(editTextTag.getText().toString(), 1);
                    imageAdapter = new ImageAdapter(networkUtils, context);
                    imageList.setAdapter(imageAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            });
        }


    }