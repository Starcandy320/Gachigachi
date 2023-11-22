package com.yc.ac.gachigachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class gs_ListAdapter extends RecyclerView.Adapter<gs_ListAdapter.ViewHolder> {
    //private final List<board_Item> items;
    private ArrayList<User> arrayList;
    private Context context;

    public gs_ListAdapter(Context context, ArrayList<User> items) {

        //this.items = items;
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        board_Item item = items.get(position);
//        holder.titleTextView.setText(item.getTitle());
//        holder.subTextView1.setText(item.getSubText1());
//        holder.PhoneNum.setText(item.getPhoneNum());
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_carNumber.setText(arrayList.get(position).getCarNumber());
        holder.tv_address.setText(arrayList.get(position).getAddress());
        holder.tv_phoneNumber.setText(arrayList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView titleTextView;
//        public TextView subTextView1;
//        public TextView PhoneNum;
        TextView tv_name;
        TextView tv_carNumber;
        TextView tv_address;
        TextView tv_phoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
//            titleTextView = itemView.findViewById(R.id.textViewTitle);
//            subTextView1 = itemView.findViewById(R.id.textViewSubtitle1);
//            PhoneNum = itemView.findViewById(R.id.textViewPhoneNum);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_carNumber = itemView.findViewById(R.id.tv_carNumber);
            this.tv_address = itemView.findViewById(R.id.tv_address);
            this.tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
        }
    }
}
