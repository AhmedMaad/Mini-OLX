package com.example.miniolx.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniolx.R;
import com.example.miniolx.adapters.AppointmentAdapter;
import com.example.miniolx.data.AppointmentModel;
import com.example.miniolx.data.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private ArrayList<AppointmentModel> appointments = new ArrayList<>();
    private ArrayList<String> documentIDs = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        setTitle("My Appointments");

        db = FirebaseFirestore.getInstance();

        db
                .collection("appointments")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        List<DocumentSnapshot> documents = snapshots.getDocuments();
                        for (DocumentSnapshot snapshot : documents)
                            if (snapshot.getString("userID").equals(Util.U_ID)) {
                                appointments.add(snapshot.toObject(AppointmentModel.class));
                                documentIDs.add(snapshot.getId());
                            }
                        showList();

                    }
                });
    }

    private void showList() {
        RecyclerView rv = findViewById(R.id.rv);
        AppointmentAdapter adapter = new AppointmentAdapter(this, appointments);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                db
                        .collection("appointments")
                        .document(documentIDs.get(position))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AppointmentsActivity.this, "Appointment Cancelled", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }

}