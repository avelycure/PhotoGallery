package com.avelycure.photogallery.dialog_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

public class ShowImageDetailsActivity extends AppCompatActivity {
    private static String URL = "url";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_details);
        this.setFinishOnTouchOutside(true);

        iv = findViewById(R.id.sid_iv);

        Bundle arguement = getIntent().getExtras();
        String url = null;
        if (arguement != null)
            url = arguement.get(URL).toString();

        Picasso.with(this).load(url).into(iv);
    }
}