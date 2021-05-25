package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.miniolx.R;
import com.example.miniolx.data.ApartmentModel;

public class ApartmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);
        setTitle("Apartment Details");

        ApartmentModel apartment = getIntent().getParcelableExtra("apartment");

    }
}