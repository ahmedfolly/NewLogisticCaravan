package com.example.logisticcavan.notifications.presentaion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.notifications.domain.entity.Notification;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsVH> {
    List<Notification> notifications;

    public NotificationsAdapter( List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationsAdapter.NotificationsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View offerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_notifications, parent, false);
        return new NotificationsVH(offerView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.NotificationsVH holder, int position) {
        Notification notification = notifications.get(position);
        holder.text.setText(notification.getMessage());
        holder.date.setText(notification.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationsVH extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;

        public NotificationsVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
        }
    }
}
