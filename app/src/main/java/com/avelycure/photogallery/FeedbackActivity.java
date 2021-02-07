package com.avelycure.photogallery;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {

    EditText edtMessage;
    EditText edtTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        edtMessage = findViewById(R.id.edt_message);
        edtTheme = findViewById(R.id.editTheme);
        edtMessage.setBackgroundColor(Color.WHITE);
        edtTheme.setBackgroundColor(Color.WHITE);
    }
}