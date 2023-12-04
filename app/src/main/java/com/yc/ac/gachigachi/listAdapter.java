package com.yc.ac.gachigachi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {
    private final List<board_Item> items;
    private final Function<String, String> numberMethod;

    public listAdapter(List<board_Item> items, Function<String, String> numberMethod) {
        this.items = items;
        this.numberMethod = numberMethod;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView carNumber;
        public TextView phoneNumber;
        public TextView address;
        public TextView day;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            carNumber = itemView.findViewById(R.id.textViewCarNumber);
            phoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            address = itemView.findViewById(R.id.textViewAddress);
            day = itemView.findViewById(R.id.textViewDay);
            time = itemView.findViewById(R.id.textViewTime);
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
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
            dayOfWeek = 2;
        }
        String dayString = getDayString(dayOfWeek);

        if (item.getTimetable() != null && item.getTimetable().size() > (dayOfWeek-2)) {
            String timeDay = item.getTimetable().get(dayOfWeek - 2);
            if (!timeDay.equals(",")) {
                timeDay = numberMethod.apply(timeDay);
                timeDay = timeDay + "시";
                holder.name.setText(item.getName());
                holder.carNumber.setText(item.getCarNumber());
                holder.phoneNumber.setText(item.getPhoneNumber());
                holder.address.setText(item.getAddress());
                holder.day.setText(dayString);
                holder.time.setText(timeDay);
            } else {
                holder.itemView.post(() -> {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        items.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                    }
                });
            }
        }
    }

    private String getDayString(int dayOfWeek) {
        String[] week = {"월요일", "화요일", "수요일", "목요일", "금요일"};
        return week[dayOfWeek - 2];
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
