package com.example.miniolx.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.miniolx.adapters.AvailableApartmentsAdapter;
import com.example.miniolx.data.ApartmentModel;
import com.example.miniolx.R;
import com.example.miniolx.data.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText searchET;
    private List<ApartmentModel> apartments = new ArrayList<>();
    private String searchQuery = "";
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Available Apartments");
        Log.d("trace", "User ID: " + Util.U_ID);

        recyclerView = findViewById(R.id.rv);
        progressBar = findViewById(R.id.pb);
        searchET = findViewById(R.id.et_search_place);
        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_low_to_high:
                        Collections.sort(apartments);
                        Log.d("trace", "low to high: " + apartments);
                        showApartments();
                        break;
                    case R.id.rb_high_to_low:
                        Collections.sort(apartments, Collections.reverseOrder());
                        Log.d("trace", "high to low: " + apartments);
                        showApartments();
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
                searchQuery = s.toString().toLowerCase();
                progressBar.setVisibility(View.VISIBLE);
                apartments.clear();
                Log.d("trace", "Loading data from text change listener");
                loadData();
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

    @Override
    protected void onResume() {
        super.onResume();
        searchET.getText().clear();

        int selectedRb = rg.getCheckedRadioButtonId();
        if (selectedRb != -1) {
            RadioButton rb = findViewById(selectedRb);
            rb.setChecked(false);
        }
        apartments.clear();
        progressBar.setVisibility(View.VISIBLE);
        Log.d("trace", "Loading data from (on Resume)");
        loadData();
    }

    private void loadData() {
        FirebaseFirestore
                .getInstance()
                .collection("apartments")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        if (searchQuery.isEmpty())
                            apartments = snapshots.toObjects(ApartmentModel.class);
                        else {
                            apartments.clear();
                            List<DocumentSnapshot> docs = snapshots.getDocuments();
                            for (int i = 0; i < docs.size(); ++i) {
                                if (docs.get(i).getString("address").toLowerCase()
                                        .equals(searchQuery)) {
                                    Log.d("trace", "query: " + searchQuery + ", real data: " +
                                            docs.get(i).getString("address"));
                                    apartments.add(docs.get(i).toObject(ApartmentModel.class));
                                }
                            }
                        }
                        showApartments();
                    }
                });
    }

    private void showApartments() {
        progressBar.setVisibility(View.GONE);
        AvailableApartmentsAdapter adapter = new AvailableApartmentsAdapter(apartments, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AvailableApartmentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(HomeActivity.this, ApartmentDetailsActivity.class);
                i.putExtra("apartment", apartments.get(position));
                startActivity(i);
            }
        });
    }

    public void openAddProductActivity(View view) {
        Intent i = new Intent(this, AddApartmentActivity.class);
        startActivity(i);
    }
}