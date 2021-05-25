package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.miniolx.R;
import com.example.miniolx.adapters.MyApartmentsAdapter;
import com.example.miniolx.data.ApartmentModel;
import com.example.miniolx.data.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyApartments extends AppCompatActivity {

    private List<ApartmentModel> apartments;
    private ProgressBar progressBar;
    private List<ApartmentModel> apartmentsToShow = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apartments);
        setTitle("My Apartments");
        Log.d("trace", "User ID: " + Util.U_ID);

        FirebaseFirestore
                .getInstance()
                .collection("apartments")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        apartments = snapshots.toObjects(ApartmentModel.class);
                        for (int i = 0; i < apartments.size(); ++i)
                            if (apartments.get(i).getUserID().equals(Util.U_ID))
                                apartmentsToShow.add(apartments.get(i));
                        showApartments();
                    }
                });

    }

    private void showApartments() {
        progressBar = findViewById(R.id.pb);
        progressBar.setVisibility(View.GONE);
        RecyclerView rv = findViewById(R.id.rv);
        MyApartmentsAdapter adapter = new MyApartmentsAdapter(apartmentsToShow, this);
        rv.setAdapter(adapter);
        adapter.setOnDeleteItemClickListener(new MyApartmentsAdapter.OnDeleteItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

}