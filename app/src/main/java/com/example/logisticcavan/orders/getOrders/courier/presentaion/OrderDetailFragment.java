package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import static com.example.logisticcavan.common.utils.Constant.DELIVERED;
import static com.example.logisticcavan.common.utils.Constant.PENDING;
import static com.example.logisticcavan.common.utils.Constant.SHIPPED;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentOrderDetailBinding;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;
import java.util.Map;


public class OrderDetailFragment extends BaseFragment {

    private FragmentOrderDetailBinding binding;
    String orderId,customerName;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Order order =   OrderDetailFragmentArgs.fromBundle(requireArguments()).getOrder();
        setUpUiData(order);
        setUpClickListeners();
    }

    private void setUpClickListeners() {
     binding.iconMessage.setOnClickListener(view -> {
          navController.navigate(OrderDetailFragmentDirections.actionOrderDetailFragmentToChattingFragment(customerName,orderId));
     });

    }

    private void setUpUiData(Order order) {
        Map<String,String> customerMap = order.getCustomer();
         customerName = customerMap.get("name");

        List<Map<String, Object>> cartItemsMap = order.getCartItems();
        Map<String,Object> generaDetails = order.getGeneralDetails();

        Map<String, String> location = order.getLocation();
        Map<String, String> deliveryTime = order.getDeliveryTime();
        Map<String, String> restaurant = order.getRestaurant();
        String restaurantName = restaurant.get("name");
         orderId = generaDetails.get("orderId").toString();
        String orderStatus = generaDetails.get("status").toString();

        binding.address.setText(location.get("beach")+", Villa"+location.get("villaNum"));
        binding.date.setText(deliveryTime.get("date"));
        binding.time.setText(deliveryTime.get("time"));
        binding.deliveryId.setText("#"+orderId.substring(0,5));
        setTextButton(orderStatus);
        if(orderStatus.equals("pending")){

    }
}

    private void navigateToChangeStatusFragment() {

    }

    private void setTextButton(String orderStatus) {
     String text = "";
      switch (orderStatus){
          case PENDING:
              text = "Start delivery";
      break;
          case DELIVERED: {
              text = "Update Status";
          break;
      }
          case SHIPPED: {
              text = "Shipped";
              binding.startDelivery.setEnabled(false);
              break;
          }

          default:
           text ="Start delivery";
      }
    binding.startDelivery.setText(text);
    }
    }