package com.yc.ac.gachigachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.UserViewHolder> {

    private ArrayList<User> arrayList;
    private Context context;

    public userAdapter(ArrayList<User> arrayList, Context context) {

        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main,parent,false);
        UserViewHolder holder = new UserViewHolder(view);


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //Glide.with(holder.itemView).load(arrayList.get(position).getProfile()).into(holder.profile);
        holder.name.setText(arrayList.get(position).getName());
        holder.phoneNumber.setText(arrayList.get(position).getPhoneNumber());
        holder.address.setText(arrayList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {

        return (arrayList != null ? arrayList.size() : 0);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView phoneNumber;
        TextView address;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // this.profile = itemView.findViewById(R.id.profile);
            this.name    = itemView.findViewById(R.id.name);
            this.phoneNumber = itemView.findViewById(R.id.phone_nuber);
            this.address = itemView.findViewById(R.id.address);
        }
    }
}
