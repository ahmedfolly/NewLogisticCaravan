package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentOrderDetailBinding;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;
import java.util.Map;


public class OrderDetailFragment extends BaseFragment {

    private FragmentOrderDetailBinding binding;
    String orderId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
        Order order =   OrderDetailFragmentArgs.fromBundle(requireArguments()).getOrder();
        setUpUiData(order);
        return binding.getRoot();
    }

    private void setUpUiData(Order order) {
        Map<String,String> customerMap = order.getCustomer();
        String customerName = customerMap.get("name");

        List<Map<String, Object>> cartItemsMap = order.getCartItems();
        Map<String,Object> generaDetails = order.getGeneralDetails();

        Map<String, String> location = order.getLocation();
        Map<String, String> deliveryTime = order.getDeliveryTime();
        Map<String, String> restaurant = order.getRestaurant();
        String restaurantName = restaurant.get("name");
        String orderId = generaDetails.get("orderId").toString();
        String orderStatus = generaDetails.get("status").toString();

        binding.address.setText(location.get("beach")+", Villa"+location.get("villaNum"));
        binding.date.setText(deliveryTime.get("date"));
        binding.time.setText(deliveryTime.get("time"));
        binding.deliveryId.setText("#"+orderId.substring(0,5));


        setTextButton(orderStatus);
        if(orderStatus.equals("pending")){


    }
}

    private void setTextButton(String orderStatus) {
     String text = "";
      switch (orderStatus){
          case "Pending":
             text = "Update Status";
      break;
      case "Delivered":{
          text = "Delivered";
          binding.startDelivery.setEnabled(false);
          break;
      }

          default:
           text ="Start delivery";
      }
    binding.startDelivery.setText(text);
    }
    }