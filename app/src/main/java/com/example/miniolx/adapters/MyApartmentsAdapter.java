package com.example.miniolx.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniolx.R;
import com.example.miniolx.activities.MyApartments;
import com.example.miniolx.data.ApartmentModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MyApartmentsAdapter
        extends RecyclerView.Adapter<MyApartmentsAdapter.MVH> {

    private List<ApartmentModel> apartments;
    private Activity activity;
    private OnDeleteItemClickListener onDeleteItemClickListener;

    public interface OnDeleteItemClickListener {
        void onItemClick(int position);
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener onItemClickListener) {
        this.onDeleteItemClickListener = onItemClickListener;
    }

    public MyApartmentsAdapter(List<ApartmentModel> apartments, Activity activity) {
        this.apartments = apartments;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyApartmentsAdapter.MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity
                .getLayoutInflater()
                .inflate(R.layout.my_apartments_list_item, parent, false);
        return new MVH(v, onDeleteItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyApartmentsAdapter.MVH holder, int position) {

        String address = apartments.get(position).getAddress();
        if (address.length() > 25)
            holder.productAddressTV.setText(address.substring(0, 25) + "...");
        else
            holder.productAddressTV.setText(address);

        Glide
                .with(activity)
                .load(apartments.get(position).getPicture())
                .placeholder(R.drawable.ic_download)
                .into(holder.productIV);

    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }

    public class MVH extends RecyclerView.ViewHolder {

        private ImageView productIV;
        private TextView productAddressTV;
        private MaterialCardView cardView;

        public MVH(@NonNull View itemView, OnDeleteItemClickListener clickListener) {
            super(itemView);

            productIV = itemView.findViewById(R.id.iv_product);
            productAddressTV = itemView.findViewById(R.id.tv);
            cardView = itemView.findViewById(R.id.parent);

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onItemClick(getAdapterPosition());
                    return true;
                }
            });

        }

    }

}
