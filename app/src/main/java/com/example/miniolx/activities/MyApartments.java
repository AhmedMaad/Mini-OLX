package com.example.miniolx.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.miniolx.R;
import com.example.miniolx.adapters.MyApartmentsAdapter;
import com.example.miniolx.data.ApartmentModel;
import com.example.miniolx.data.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class MyApartments extends AppCompatActivity {

    private List<ApartmentModel> apartments;
    private ProgressBar progressBar;
    private List<ApartmentModel> apartmentsToShow = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apartments);
        setTitle("My Apartments");
        Log.d("trace", "User ID: " + Util.U_ID);

        db = FirebaseFirestore.getInstance();

        db
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
                db
                        .collection("apartments")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot snapshots) {
                                List<DocumentSnapshot> s = snapshots.getDocuments();
                                for (int i = 0; i < s.size(); ++i) {
                                    Log.d("trace", "apartment id: " + apartmentsToShow.get(position).getApartmentID());
                                    Log.d("trace", "apartment id: " + s.get(i).getId());
                                    if (apartmentsToShow.get(position).getApartmentID()
                                            .equals(s.get(i).getId())) {
                                        deleteApartment(s.get(i).getId());
                                        apartmentsToShow.remove(position);
                                        adapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void deleteApartment(String id) {
        db
                .collection("apartments")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MyApartments.this, "Apartment Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}