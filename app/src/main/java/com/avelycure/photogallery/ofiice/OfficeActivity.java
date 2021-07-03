package com.avelycure.photogallery.ofiice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avelycure.photogallery.R;

public class OfficeActivity extends AppCompatActivity {

    ImageView personalPhoto;
    Button btnDateOfBirth;
    Button btnSex;
    Button btnEmail;
    Button btnTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        personalPhoto = findViewById(R.id.iv_personal_photo);
        btnEmail = findViewById(R.id.button_email);
        btnTelephone = findViewById(R.id.button_telephone);
        btnSex = findViewById(R.id.button_sex);
        btnDateOfBirth = findViewById(R.id.button_date_of_birth);

        personalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You clicked image, later you will be able to chande this image", Toast.LENGTH_SHORT).show();
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Make activity for result", Toast.LENGTH_SHORT).show();
            }
        });

        btnDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Make activity for result", Toast.LENGTH_SHORT).show();
            }
        });

        btnSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Make activity for result", Toast.LENGTH_SHORT).show();
            }
        });

        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Make activity for result", Toast.LENGTH_SHORT).show();
            }
        });

    }
}