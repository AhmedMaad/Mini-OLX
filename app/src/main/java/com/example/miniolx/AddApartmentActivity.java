package com.example.miniolx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddApartmentActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private TextInputEditText addressET;
    private TextInputEditText areaET;
    private TextInputEditText roomsNoET;
    private TextInputEditText bathroomsNoET;
    private TextInputEditText kitchenNoET;
    private TextInputEditText viewDescriptionET;
    private TextInputEditText floorNoET;
    private ProgressBar progressBar;
    private String chosenRentType;
    private ArrayList<String> availableTimes = new ArrayList<>();
    private TextInputEditText timeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);

        addressET = findViewById(R.id.et_address);
        areaET = findViewById(R.id.et_area);
        roomsNoET = findViewById(R.id.et_rooms_number);
        bathroomsNoET = findViewById(R.id.et_bathroom_number);
        kitchenNoET = findViewById(R.id.et_kitchen_number);
        viewDescriptionET = findViewById(R.id.et_view_description);
        floorNoET = findViewById(R.id.et_floor_number);
        progressBar = findViewById(R.id.pb);
        timeET = findViewById(R.id.et_available_time);

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

        view.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String address = addressET.getText().toString();
        double area = Double.parseDouble(areaET.getText().toString());
        int rooms = Integer.parseInt(roomsNoET.getText().toString());
        int bathrooms = Integer.parseInt(bathroomsNoET.getText().toString());
        int kitchens = Integer.parseInt(kitchenNoET.getText().toString());
        String viewDescription = viewDescriptionET.getText().toString();
        int floorNo = Integer.parseInt(floorNoET.getText().toString());

        ApartmentModel apartment = new ApartmentModel(address, area, rooms, bathrooms
                , kitchens, viewDescription, floorNo, chosenRentType, Util.U_ID, availableTimes);

        FirebaseFirestore
                .getInstance()
                .collection("apartments")
                .add(apartment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddApartmentActivity.this, "Apartment Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

    public void pickDate(View view) {
        DateChooserDialog datePicker = new DateChooserDialog();
        datePicker.setCancelable(false);
        datePicker.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
        String dayOfWeek;
        try {
            Date d = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            dayOfWeek = new SimpleDateFormat("EE").format(d);

            String chosenDate = "(" + dayOfWeek + ")" + " " + date;
            Log.d("trace", chosenDate);
            timeET.append(chosenDate + "\n");
            availableTimes.add(chosenDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("trace", e.getMessage());
        }

    }
}