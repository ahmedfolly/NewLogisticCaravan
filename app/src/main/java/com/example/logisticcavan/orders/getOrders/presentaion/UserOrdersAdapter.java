package com.example.logisticcavan.orders.getOrders.presentaion;

import static androidx.navigation.Navigation.findNavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.example.logisticcavan.orders.getOrders.presentaion.ui.UserOrdersFragmentDirections;

import java.util.List;
import java.util.Map;

public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.UserOrderViewHolder> {
    private final List<Order> orders;
    private NavController navController;

    public UserOrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public UserOrdersAdapter.UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_orders_item,parent,false);
        return new UserOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrdersAdapter.UserOrderViewHolder holder, int position) {
        Order order = orders.get(position);
        List<Map<String, Object>> cartItemsMap = order.getCartItems();
        holder.itemNumber.setText(""+cartItemsMap.size());
        //restaurant details
        Map<String, String> restaurant = order.getRestaurant();
        String restaurantName = restaurant.get("name");
        holder.restaurantName.setText(restaurantName);
        //general detaills
        Map<String,Object> gDetails = order.getGeneralDetails();
        String status = (String) gDetails.get("status");
        holder.status.setText(status);
        holder.itemView.setOnClickListener(v->{
            navController = findNavController(holder.itemView);
            NavDirections action = UserOrdersFragmentDirections.actionUserOrdersFragmentToTrakOrderFragment(status);
            navController.navigate(action);
        });
        if (status != null) {
            switch (status){
                case Constant.PENDING:
                    holder.status.setBackgroundResource(R.drawable.status_text_pending_background);
                    break;
                case Constant.DELIVERED:

                    holder.status.setBackgroundResource(R.drawable.status_completed_text_background);
                    holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.white,null));
                    break;
                case Constant.SHIPPED:
                    holder.status.setBackgroundResource(R.drawable.status_text_shipped_background);
                    holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.white,null));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class UserOrderViewHolder extends RecyclerView.ViewHolder{
        TextView restaurantName;
        TextView itemNumber;
        TextView status;
        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name_user_orders);
            itemNumber = itemView.findViewById(R.id.items_count_user_orders);
            status = itemView.findViewById(R.id.user_order_status);
        }
    }
}