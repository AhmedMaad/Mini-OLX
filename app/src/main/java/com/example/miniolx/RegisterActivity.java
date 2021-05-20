package com.example.miniolx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void openSignIn(View view) {
        Intent i = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
    }

}