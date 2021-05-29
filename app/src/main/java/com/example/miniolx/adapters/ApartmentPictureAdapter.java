package com.example.miniolx.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniolx.R;

import java.util.ArrayList;

public class ApartmentPictureAdapter extends RecyclerView.Adapter<ApartmentPictureAdapter.MVH> {

    private Activity activity;
    private ArrayList<String> pictures;

    public ApartmentPictureAdapter(Activity activity, ArrayList<String> pictures) {
        this.activity = activity;
        this.pictures = pictures;
    }

    @NonNull
    @Override
    public ApartmentPictureAdapter.MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.apartment_picture_list_item, parent, false);
        return new MVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentPictureAdapter.MVH holder, int position) {
        Glide
                .with(activity)
                .load(pictures.get(position))
                .placeholder(R.drawable.ic_download)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class MVH extends RecyclerView.ViewHolder {

        private ImageView image;

        public MVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_apartment);
        }
    }
}
