package com.example.logisticcavan.orders.updateStatusOrder.presentaion;

import static androidx.navigation.Navigation.findNavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.example.logisticcavan.orders.getOrders.presentaion.ui.UserOrdersFragmentDirections;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UsersInfoAdapter extends RecyclerView.Adapter<UsersInfoAdapter.UserOrderViewHolder> {
    private final List<UserInfo> users;

    public UsersInfoAdapter(List<UserInfo> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UsersInfoAdapter.UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_data_item,parent,false);
        return new UserOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersInfoAdapter.UserOrderViewHolder holder, int position) {
        UserInfo userInfo = users.get(position);
        holder.address.setText(userInfo.getAddress());
        holder.name.setText(userInfo.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserOrderViewHolder extends RecyclerView.ViewHolder{
        TextView address;
        TextView name;
        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            name = itemView.findViewById(R.id.name);
        }
    }
}