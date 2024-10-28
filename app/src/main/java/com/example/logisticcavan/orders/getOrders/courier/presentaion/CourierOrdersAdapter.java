package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.io.ObjectStreamClass;
import java.util.List;
import java.util.Map;

public class CourierOrdersAdapter extends RecyclerView.Adapter<CourierOrdersAdapter.OffersVH> {
    List<Order> orders;

    public CourierOrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public CourierOrdersAdapter.OffersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View offerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_order_courier, parent, false);
        return new OffersVH(offerView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourierOrdersAdapter.OffersVH holder, int position) {
        Order order = orders.get(position);
        holder.idOrder.setText(order.getOrderId());
        Map<String,String> customerMap = order.getCustomer();
        String customerName = customerMap.get("name");
        holder.clientName.setText(customerName);
//        holder.restaurantName.setText(order.getRestaurantName());
        //order items details
        List<Map<String, Object>> cartItemsMap = order.getCartItems();
        holder.itemNumber.setText(""+cartItemsMap.size());
        //restaurant details
        Map<String, String> restaurant = order.getRestaurant();
        String restaurantName = restaurant.get("name");
        holder.restaurantName.setText(restaurantName);
        //customerdetails
        //general details
//        holder.itemNumber.setText(""+order.getCartItems().size());
        holder.itemView.setOnClickListener(view1 -> {
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OffersVH extends RecyclerView.ViewHolder {
        TextView idOrder;
        TextView clientName;
        TextView restaurantName;
        TextView itemNumber;

        public OffersVH(@NonNull View itemView) {
            super(itemView);
            idOrder = itemView.findViewById(R.id.idOrder);
            clientName = itemView.findViewById(R.id.customerName);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            itemNumber = itemView.findViewById(R.id.itemNumber);
        }
    }
}
