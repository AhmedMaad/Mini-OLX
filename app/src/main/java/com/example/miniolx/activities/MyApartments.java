package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.miniolx.R;

public class MyApartments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apartments);
        setTitle("My Apartments");
    }
}