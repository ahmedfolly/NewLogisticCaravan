package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import static com.example.logisticcavan.common.utils.Constant.DELIVERED;
import static com.example.logisticcavan.common.utils.Constant.PENDING;
import static com.example.logisticcavan.common.utils.Constant.SHIPPED;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentCourierHomeBinding;
import com.example.logisticcavan.orders.getOrders.OnOrderItemClicked;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CourierHomeFragment extends BaseFragment  implements OnOrderItemClicked {

    private CourierOrdersAdapter courierOrdersAdapter;
    private FragmentCourierHomeBinding binding;
    private NavController navController;
    private List<Order> currentOrders;

    @Inject
    GetCourierOrdersViewModel getCourierOrdersViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCourierHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getCourierOrdersViewModel.getOrdersBasedStatus(PENDING);
        observeViewModel();
        setUpSpinner();
        setUpClickListener();
    }



    private void setUpSpinner() {
     binding.filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             String selectedItem = parent.getItemAtPosition(position).toString();
             updateUiBasedSelection(selectedItem);

         }

         private void updateUiBasedSelection(String selectedItem) {

          switch (selectedItem){
              
              case "Filter by beach":
                  break;
              case "All beaches": {
                  isThereOrders(getCourierOrdersViewModel.listOrders.getValue());
                  break;
              }

              case "Seasehell island": {
                  filterDataByBeach("Seasehell island");
                  break;
              }

              case "Breeze island": {
                  filterDataByBeach("Breeze island");
                  break;
              }

              case "Palm island": {
                  filterDataByBeach("Palm island");
                  break;
              }
              case "Masts island": {
                  filterDataByBeach("Masts island");
                  break;
              }
              case "Wave island": {
                  filterDataByBeach("Wave island");
                  break;
              }

              case "Pearl island": {
                  filterDataByBeach("Pearl island");
                  break;
              }

          }

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
         }
     });
    }





    private void setUpClickListener() {
        binding.activeOrders.setOnClickListener(this::activeClicked);
        binding.shippedOrders.setOnClickListener(this::shippedClicked);
        binding.deliveredOrders.setOnClickListener(this::deliveredClicked);
        binding.expiredProducts.setOnClickListener(this::expiredClicked);
    }

    private void activeClicked(View view) {
        changeTexts(view);
        getCourierOrdersViewModel.getOrdersBasedStatus(PENDING);
    }
    private void shippedClicked(View view) {
        changeTexts(view);
        getCourierOrdersViewModel.getOrdersBasedStatus(SHIPPED);
    }

    private void changeTexts(View view) {
        if (view.getId() == R.id.active_orders) {
            binding.textView18.setText(getString(R.string.scheduled_active_orders));
            binding.textView18.setTextColor(Color.parseColor("#ECD211"));

        } else if (view.getId() == R.id.shipped_orders) {
            binding.textView18.setText(getString(R.string.shipped_orders));
            binding.textView18.setTextColor(Color.parseColor("#E55763"));
        } else if (view.getId() == R.id.delivered_orders) {
            binding.textView18.setText(getString(R.string.delivered_orders));
            binding.textView18.setTextColor(Color.parseColor("#5AD058"));
        }else {

        }
    }



    private void deliveredClicked(View view) {
        changeTexts(view);
        getCourierOrdersViewModel.getOrdersBasedStatus(DELIVERED);
    }



    private void expiredClicked(View view) {
        navController.navigate(CourierHomeFragmentDirections.actionCourierHomeFragmentToExpiredProductsFragment());

    }


    private void observeViewModel() {

        getCourierOrdersViewModel.listOrders.observe(getViewLifecycleOwner(), orders -> {
            currentOrders = orders;
            isThereOrders(orders);
            binding.progressBar.setVisibility(View.GONE);

        });

        getCourierOrdersViewModel.showSuccessMessage.observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null) {
                getCourierOrdersViewModel.resetShowSuccessMessage();
            }
        });

        getCourierOrdersViewModel.showErrorMessage.observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                getCourierOrdersViewModel.resetShowErrorMessage();
            }
        });

    }

    private void isThereOrders(List<Order> orders) {

        if (!orders.isEmpty()) {
            Log.e("TAG", "observeViewModel: " + orders.size());
            updateRecyclerView(orders);
            binding.data.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        }else {
            binding.data.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    private void updateRecyclerView(List<Order> orders) {
        courierOrdersAdapter = new CourierOrdersAdapter(orders,this);
        binding.ordersRecycler.setAdapter(courierOrdersAdapter);
    }


    private void filterDataByBeach(String beach) {

        List<Order> filteredList = currentOrders.stream()
                .filter(order -> isThereLocation(order, beach))
                .collect(Collectors.toList());
        isThereOrders(filteredList);

    }

    private boolean isThereLocation(Order order, String beach) {
        Map<String, String> location = order.getLocation();
        String beach1 = location.get("beach");
        return Objects.equals(beach1, beach);
    }


    @Override
    public void onOrderClicked(Order order) {
        navController.navigate
                (CourierHomeFragmentDirections.actionCourierHomeFragmentToOrderDetailFragment(order));
    }
}