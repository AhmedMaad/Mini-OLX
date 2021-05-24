package com.example.miniolx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Available Apartments");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, SignInActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.item_my_apartments:
                startActivity(new Intent(this, MyApartments.class));
                return true;

            case R.id.item_appointments:
                startActivity(new Intent(this, AppointmentsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openAddProductActivity(View view) {
        Intent i = new Intent(this, AddApartmentActivity.class);
        startActivity(i);
    }
}