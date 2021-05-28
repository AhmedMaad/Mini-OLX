package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miniolx.R;
import com.example.miniolx.data.ApartmentModel;
import com.example.miniolx.data.AppointmentModel;
import com.example.miniolx.data.Util;
import com.example.miniolx.dialogs.ApartmentDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApartmentDetailsActivity extends AppCompatActivity {

    private ImageView apartmentIV;
    private TextView addressTV;
    private TextView areaTV;
    private TextView roomsNoTV;
    private TextView bathroomsNoTV;
    private TextView kitchensNoTV;
    private TextView viewDescriptionTV;
    private TextView floorNoTV;
    private TextView priceTV;
    private TextView rentTypeTV;
    private ListView availableTimesLV;
    private ApartmentModel apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);
        setTitle("Apartment Details");

        apartmentIV = findViewById(R.id.iv_apartment);
        addressTV = findViewById(R.id.tv_address);
        areaTV = findViewById(R.id.tv_area);
        roomsNoTV = findViewById(R.id.tv_rooms_number);
        bathroomsNoTV = findViewById(R.id.tv_bathroom_number);
        kitchensNoTV = findViewById(R.id.tv_kitchen_number);
        viewDescriptionTV = findViewById(R.id.tv_description);
        floorNoTV = findViewById(R.id.tv_floor);
        priceTV = findViewById(R.id.tv_price);
        rentTypeTV = findViewById(R.id.tv_rent_type);
        availableTimesLV = findViewById(R.id.lv_available_time);

        apartment = getIntent().getParcelableExtra("apartment");

        Glide.with(this).load(apartment.getPicture()).placeholder(R.drawable.ic_download)
                .into(apartmentIV);
        addressTV.setText(apartment.getAddress());
        areaTV.setText(String.valueOf(apartment.getArea()));
        roomsNoTV.setText(String.valueOf(apartment.getRoomsNo()));
        bathroomsNoTV.setText(String.valueOf(apartment.getBathroomsNo()));
        kitchensNoTV.setText(String.valueOf(apartment.getKitchenNo()));
        viewDescriptionTV.setText(String.valueOf(apartment.getViewDescription()));
        floorNoTV.setText(String.valueOf(apartment.getFloorNo()));
        priceTV.setText(String.valueOf(apartment.getPrice()));
        rentTypeTV.setText(String.valueOf(apartment.getRentType()));

        ArrayList<String> times = apartment.getAvailableTimes();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, times);
        availableTimesLV.setAdapter(adapter);
        availableTimesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //check if this apartment belongs to the same user so, tell the user that this
                //is his apartment, otherwise add the time to the appointments list.
                if (apartment.getUserID().equals(Util.U_ID)) {
                    ApartmentDialog dialog = new ApartmentDialog();
                    dialog.show(getSupportFragmentManager(), null);
                } else
                    makeAppointment(times.get(position));

            }
        });

    }

    private void makeAppointment(String time) {

        AppointmentModel appointment =
                new AppointmentModel(time, apartment.getMobile(), Util.U_ID);

        FirebaseFirestore
                .getInstance()
                .collection("appointments")
                .add(appointment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent i = new Intent(ApartmentDetailsActivity.this
                                , AppointmentsActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }

}