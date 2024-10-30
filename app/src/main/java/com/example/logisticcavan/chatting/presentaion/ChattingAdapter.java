package com.example.logisticcavan.chatting.presentaion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.chatting.domain.Message;

import java.util.List;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ChattingVH> {
    List<Message> messages;
     String userId;
    public ChattingAdapter( List<Message> messages,String userId ) {
        this.messages = messages;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ChattingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View offerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_message, parent, false);
        return new ChattingVH(offerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingVH holder, int position) {
        Message message = messages.get(position);
        if (message.getSenderId().equals(userId)) {
            holder.partner.setVisibility(View.GONE);
            holder.sender.setVisibility(View.VISIBLE);
            holder.messageSender.setText(message.getText());

        }else {
            holder.partner.setVisibility(View.VISIBLE);
            holder.sender.setVisibility(View.GONE);
            holder.messagePartner.setText(message.getText());

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ChattingVH extends RecyclerView.ViewHolder {
        TextView messageSender;
        TextView messagePartner;
        ConstraintLayout sender;
        ConstraintLayout partner;

        public ChattingVH(@NonNull View itemView) {
            super(itemView);
            messageSender = itemView.findViewById(R.id.messageSender);
            messagePartner = itemView.findViewById(R.id.messagePartner);
            partner = itemView.findViewById(R.id.partner);
            sender = itemView.findViewById(R.id.sender);
        }
    }
}
