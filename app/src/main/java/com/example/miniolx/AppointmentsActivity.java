package com.example.miniolx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        setTitle("My Appointments");
    }
}