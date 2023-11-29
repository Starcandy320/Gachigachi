package com.yc.ac.gachigachi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class gs_ListAdapter extends RecyclerView.Adapter<gs_ListAdapter.ViewHolder> {
    private final List<board_Item> items;

    public gs_ListAdapter(List<board_Item> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView carNumber;
        public TextView phoneNumber;
        public TextView address;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            carNumber = itemView.findViewById(R.id.textViewCarNumber);
            phoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            address = itemView.findViewById(R.id.textViewAddress);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        board_Item item = items.get(position);
        holder.name.setText(item.getName());
        holder.carNumber.setText(item.getCarNumber());
        holder.phoneNumber.setText(item.getPhoneNumber());
        holder.address.setText(item.getAddress());
    }
    public String getPhoneNumber(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position).getPhoneNumber();
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
