package com.avelycure.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import utils.ImageAdapter;
import utils.NetworkUtils;
import utils.PhotoGalleryDatabaseHelper;

public class PhotoGallery extends AppCompatActivity {

    private Button btnSendRequest;
    private EditText editTextTag;
    private RecyclerView imageList;

    LinearLayoutManager linearLayoutManager;

    Context context;
    private NetworkUtils networkUtils;
    private ImageAdapter imageAdapter;

    private boolean loading = true;
    PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_gallery);

        context = PhotoGallery.this;
        photoGalleryDatabaseHelper = new PhotoGalleryDatabaseHelper(context);
        networkUtils = new NetworkUtils(this, photoGalleryDatabaseHelper);

        btnSendRequest = findViewById(R.id.btn_request);
        editTextTag = findViewById(R.id.edt_tag);
        imageList = findViewById(R.id.rv_images);

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

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoGalleryDatabaseHelper.clearDatabase();
                int id;
                String address;
                try {
                    networkUtils.updateJSONArray(editTextTag.getText().toString(), 1);
                    imageAdapter = new ImageAdapter(networkUtils, context, photoGalleryDatabaseHelper);
                    imageList.setAdapter(imageAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}