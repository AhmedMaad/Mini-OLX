package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miniolx.R;
import com.example.miniolx.data.ApartmentModel;

import java.util.ArrayList;

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

        ApartmentModel apartment = getIntent().getParcelableExtra("apartment");

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

    }
}