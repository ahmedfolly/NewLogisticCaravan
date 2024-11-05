package com.example.logisticcavan.orders.getOrders.presentaion;

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
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.example.logisticcavan.orders.getOrders.presentaion.ui.UserOrdersFragmentDirections;

import java.util.List;
import java.util.Locale;
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

        holder.status.setBackgroundResource(0); // Reset background
        holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.black, null));

        //restaurant details
        Map<String, String> restaurant = order.getRestaurant();
        String restaurantName = restaurant.get("name");
        String restaurantId = restaurant.get("id");
        holder.restaurantName.setText(restaurantName);
        //general detaills
        Map<String,Object> gDetails = order.getGeneralDetails();
        String status = (String) gDetails.get("status");
        String orderId = (String) gDetails.get("orderId");
        holder.status.setText(getStatus(status));
        //courier details
        Map<String,String> courier = order.getCourier();
        String courierName = courier.get("name");
        holder.itemView.setOnClickListener(v->{
            navController = findNavController(holder.itemView);
            NavDirections action = UserOrdersFragmentDirections.actionUserOrdersFragmentToTrakOrderFragment(status,restaurantId,orderId,courierName);
            navController.navigate(action);
        });
        if (status != null) {
            switch (status){
                case Constant.PENDING:
                    holder.status.setBackgroundResource(R.drawable.status_text_pending_background);
                    break;
                case Constant.DELIVERED:
                    holder.status.setBackgroundResource(R.drawable.status_completed_text_background);
                    holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                    break;
                case Constant.SHIPPED:
                    holder.status.setBackgroundResource(R.drawable.status_text_shipped_background);
                    holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                    break;
            }
        }
    }
    private String getStatus(String status){
        if (Locale.getDefault().getLanguage().equals("ar")){
            switch (status) {
                case Constant.PENDING:
                    return "قيد الانتظار";
                case Constant.DELIVERED:
                    return "تم الوصول";
                case Constant.SHIPPED:
                    return "تم الشحن";
                default:
                    return "";
            }
        }
        return status;
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