package com.yc.ac.gachigachi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class SwipeCall extends ItemTouchHelper.Callback {
    private final Context context;
    private final gs_ListAdapter adapter;

    public SwipeCall(Context context, gs_ListAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        String phoneNumber = adapter.getPhoneNumber(position);

        Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        context.startActivity(callIntent);

        adapter.notifyItemChanged(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        Paint paint = new Paint();
        int backgroundColor;
        Drawable icon;
        int iconMargin;
        int iconSize = 100;

        if (dX < 0) {
            // 왼쪽 스와이프
            backgroundColor = ContextCompat.getColor(context, R.color.md_theme_light_tertiary);
            icon = ContextCompat.getDrawable(context, R.drawable.call);
        } else {
            // 오른쪽 스와이프
            backgroundColor = ContextCompat.getColor(context, R.color.md_theme_light_error);
            icon = ContextCompat.getDrawable(context, R.drawable.warning);
        }
        iconMargin = (itemView.getHeight() - iconSize) / 2;

        paint.setColor(backgroundColor);

        float cornerRadius = 35.0f;
        RectF background = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
        c.drawRoundRect(background, cornerRadius, cornerRadius, paint);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        assert icon != null;
        if (dX > 0) {
            icon.setBounds(itemView.getLeft() + iconMargin, itemView.getTop() + iconMargin, itemView.getLeft() + iconMargin + iconSize, itemView.getBottom() - iconMargin);
        } else {
            icon.setBounds(itemView.getRight() - iconMargin - iconSize, itemView.getTop() + iconMargin, itemView.getRight() - iconMargin, itemView.getBottom() - iconMargin);
        }
        icon.draw(c);
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.9f;
    }
}
