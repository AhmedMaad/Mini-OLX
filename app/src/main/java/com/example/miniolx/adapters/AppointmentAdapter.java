package com.example.miniolx.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniolx.R;
import com.example.miniolx.data.AppointmentModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MVH> {

    private Activity activity;
    private ArrayList<AppointmentModel> appointments;
    private OnItemClickListener onItemClickListener;

    public AppointmentAdapter(Activity activity, ArrayList<AppointmentModel> appointments) {
        this.activity = activity;
        this.appointments = appointments;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AppointmentAdapter.MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.apartment_list_item, parent, false);
        return new MVH(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.MVH holder, int position) {
        holder.mobileTV.setText(appointments.get(position).getMobile());
        holder.timeTV.setText(appointments.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class MVH extends RecyclerView.ViewHolder {

        private TextView mobileTV;
        private TextView timeTV;
        private TextView cancelTV;

        public MVH(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mobileTV = itemView.findViewById(R.id.tv_mobile);
            timeTV = itemView.findViewById(R.id.tv_time);
            cancelTV = itemView.findViewById(R.id.cancel);
            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}
