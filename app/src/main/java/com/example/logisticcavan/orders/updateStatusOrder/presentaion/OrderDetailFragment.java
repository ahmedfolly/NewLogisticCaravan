package com.example.logisticcavan.orders.updateStatusOrder.presentaion;

import static com.example.logisticcavan.common.utils.Constant.DELIVERED;
import static com.example.logisticcavan.common.utils.Constant.PENDING;
import static com.example.logisticcavan.common.utils.Constant.SHIPPED;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentOrderDetailBinding;
import com.example.logisticcavan.orders.getOrders.courier.presentaion.ItemListener;
import com.example.logisticcavan.orders.getOrders.courier.presentaion.StringListAdapter;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderDetailFragment extends BaseFragment implements ItemListener {

   @Inject
   UpdateOrderStatusViewModel updateOrderStatusViewModel;

    private FragmentOrderDetailBinding binding;
    String orderId,customerName,orderStatus;
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
        showProgressDialog();
        List<String> stringList = order.getCustomers();
        getCustomers(stringList);
        setUpUiData(order);
        setUpClickListeners();
    }

    private void setUpClickListeners() {


     binding.iconMessage.setOnClickListener(view -> {
          navController.navigate(OrderDetailFragmentDirections.actionOrderDetailFragmentToChattingFragment(customerName,orderId));
     });

     binding.startDelivery.setOnClickListener(view -> {
         navigateToBottomSheet();
     });

    }

    private void setUpUiData(Order order) {
        Map<String,String> customerMap = order.getCustomer();

        List<Map<String, Object>> cartItemsMap = order.getCartItems();
        Map<String,Object> generaDetails = order.getGeneralDetails();
        Map<String, String> location = order.getLocation();
        Map<String, String> deliveryTime = order.getDeliveryTime();

        orderStatus = generaDetails.get("status").toString();
        orderId = generaDetails.get("orderId").toString();
        customerName = customerMap.get("name");
        binding.address.setText(location.get("beach")+", Villa"+location.get("villaNum"));
        binding.date.setText(deliveryTime.get("date"));
        binding.time.setText(deliveryTime.get("time"));
        binding.deliveryId.setText("#"+orderId.substring(0,5));
        setTextButton(orderStatus);
        setUpCartItems(cartItemsMap);


}

    private void setUpCartItems(List<Map<String, Object>> cartItemsMap) {
        List<String> cartItems = new ArrayList<>();
        cartItemsMap.forEach(item->{
            cartItems.add("x"+item.get("quantity")+" "+item.get("productName"));
        });
       binding.recyclerViewItems.setAdapter(new StringListAdapter(cartItems));
    }

    private void navigateToBottomSheet() {
        UpdateOrderStatusFragment fragment = new UpdateOrderStatusFragment();
        fragment.init(orderStatus,this);
        fragment.show(getParentFragmentManager(), UpdateOrderStatusFragment.class.getSimpleName());
    }

    private void setTextButton(String orderStatus) {
     String text = "";
      switch (orderStatus){
          case PENDING:
              text = getString(R.string.start_delivery1);
      break;
          case DELIVERED: {
              text = getString(R.string.update_status);
          break;
      }
          case SHIPPED: {
              text = getString(R.string.shipped);
              binding.startDelivery.setEnabled(false);
              break;
          }

          default:
              text = getString(R.string.start_delivery1);
      }
    binding.startDelivery.setText(text);
    }

    @Override
    public void onItemClick(String status) {
      updateOrderStatusViewModel.updateOrderStatus(orderId,status);
    }

    private void getCustomers(List<String> userIds) {
        List<UserInfo> users = new ArrayList<>();
        AtomicInteger remainingTasks = new AtomicInteger(userIds.size());
        for (String email : userIds) {
            updateOrderStatusViewModel.getUserRemotely(email)
                    .thenAccept(userInfo -> {
                        users.add(userInfo);
                        if (remainingTasks.decrementAndGet() == 0) {
                            requireActivity().runOnUiThread(() -> {
                                dismissProgressDialog();
                                setUpRecycler(users);
                                for (UserInfo userInfo1 : users) {
                                    Log.d("TAG", "onViewCreated: " + userInfo1.getName());
                                }
                            });
                        }
                    })
                    .exceptionally(ex -> {
                        dismissProgressDialog();
                        Log.e("User Fetch Error", "Error fetching user data", ex);
                        return null;
                    });
        }
    }

    private void setUpRecycler(List<UserInfo> users) {
        UsersInfoAdapter adapter = new UsersInfoAdapter(users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}