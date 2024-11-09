package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;

import java.util.List;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.StringViewHolder> {

    private List<String> stringList;

    public StringListAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_items_cart, parent, false);
        return new StringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        String text = stringList.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class StringViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text1);
        }
    }
}
