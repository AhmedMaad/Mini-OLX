package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.miniolx.R;

public class AppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        setTitle("My Appointments");
    }
}