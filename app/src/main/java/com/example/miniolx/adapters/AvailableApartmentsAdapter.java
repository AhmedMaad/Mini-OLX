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
import com.example.miniolx.data.ApartmentModel;

import java.util.List;

public class AvailableApartmentsAdapter
        extends RecyclerView.Adapter<AvailableApartmentsAdapter.MVH> {

    private List<ApartmentModel> apartments;
    private Activity activity;

    public AvailableApartmentsAdapter(List<ApartmentModel> apartments, Activity activity) {
        this.apartments = apartments;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AvailableApartmentsAdapter.MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity
                .getLayoutInflater()
                .inflate(R.layout.available_apartments_list_item, parent, false);
        return new MVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableApartmentsAdapter.MVH holder, int position) {

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

        holder.productPriceTV.setText(String.valueOf(apartments.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }

    public class MVH extends RecyclerView.ViewHolder {

        private ImageView productIV;
        private TextView productAddressTV;
        private TextView productPriceTV;

        public MVH(@NonNull View itemView) {
            super(itemView);
            productAddressTV = itemView.findViewById(R.id.tv_address);
            productIV = itemView.findViewById(R.id.iv_product);
            productPriceTV = itemView.findViewById(R.id.tv_price);
        }

    }

}
