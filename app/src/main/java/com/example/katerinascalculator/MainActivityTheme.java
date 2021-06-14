package com.example.katerinascalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.radiobutton.MaterialRadioButton;

import static android.os.Build.VERSION_CODES.M;

public class MainActivityTheme extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dark);


        Button lightTheme = findViewById(R.id.btnLightTheme);
        lightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityTheme.this, MainActivity.class);
                intent.putExtra(MainActivity.KEY_DISPLAY,"fhgjy");
                startActivity(intent);


            }
        });

        Button darkTheme = findViewById(R.id.btnDarkTheme);
        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}