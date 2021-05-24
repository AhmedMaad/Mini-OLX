package com.example.miniolx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText searchET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Available Apartments");

        recyclerView = findViewById(R.id.rv);
        progressBar = findViewById(R.id.pb);
        searchET = findViewById(R.id.et_search_place);
        RadioGroup rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_low_to_high:
                        //call filter from low to high method
                        break;
                    case R.id.rb_high_to_low:
                        //call filter from high to low method
                        break;
                }
            }
        });
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //call filter by name
            }
        });

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