package com.avelycure.photogallery.dialog_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ShowImageDetailsActivity extends AlertDialog {
    private static String URL = "url";
    private ImageView iv;
    private Context context;

    protected ShowImageDetailsActivity(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_details);
        //this.setFinishOnTouchOutside(true);

        iv = findViewById(R.id.sid_iv);

        Bundle arguement = ((Activity)(context)).getIntent().getExtras();
        String url = null;
        if (arguement != null)
            url = arguement.get(URL).toString();

        Picasso.with(context).load(url).into(iv);
    }
}