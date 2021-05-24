package com.example.miniolx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class AddProductActivity extends AppCompatActivity {

    private TextInputEditText addressET;
    private TextInputEditText areaET;
    private TextInputEditText roomsNoET;
    private TextInputEditText bathroomsNoET;
    private TextInputEditText kitchenNoET;
    private TextInputEditText viewDescriptionET;
    private TextInputEditText floorNoET;
    private ProgressBar progressBar;
    private String chosenRentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        addressET = findViewById(R.id.et_address);
        areaET = findViewById(R.id.et_area);
        roomsNoET = findViewById(R.id.et_rooms_number);
        bathroomsNoET = findViewById(R.id.et_bathroom_number);
        kitchenNoET = findViewById(R.id.et_kitchen_number);
        viewDescriptionET = findViewById(R.id.et_view_description);
        floorNoET = findViewById(R.id.et_floor_number);
        progressBar = findViewById(R.id.pb);

        String[] rentTypes = {"Weekly", "Monthly"};
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rentTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenRentType = rentTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void uploadApartment(View view) {

    }

}