package com.yc.ac.gachigachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class gs_ListAdapter extends RecyclerView.Adapter<gs_ListAdapter.ViewHolder> {
    private final ArrayList<board_Item> items;

    public gs_ListAdapter(ArrayList<board_Item> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView subTextView1;
        public TextView PhoneNum;

        public TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            subTextView1 = itemView.findViewById(R.id.textViewSubtitle1);
            PhoneNum = itemView.findViewById(R.id.textViewPhoneNum);
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
        holder.titleTextView.setText(item.getName());
        holder.subTextView1.setText(item.getCarNumber());
        holder.PhoneNum.setText(item.getPhoneNumber());
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
