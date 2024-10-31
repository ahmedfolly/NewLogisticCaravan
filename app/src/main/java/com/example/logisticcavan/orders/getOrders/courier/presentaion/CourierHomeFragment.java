package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import static com.example.logisticcavan.common.utils.Constant.SHIPPED;
import static com.example.logisticcavan.common.utils.Constant.PENDING;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentCourierHomeBinding;
import com.example.logisticcavan.orders.getOrders.OnOrderItemClicked;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CourierHomeFragment extends BaseFragment  implements OnOrderItemClicked {

    private CourierOrdersAdapter courierOrdersAdapter;
    private FragmentCourierHomeBinding binding;
    private NavController navController;


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
        getCourierOrdersViewModel.getAllOrders();
        observeViewModel();
        setUpClickListener();
    }

    private void setUpClickListener() {
        binding.cartIcon.setOnClickListener(this::showFilterMenu);
    }

    private void showFilterMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.filter_courier_order_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            updateUiStatus(false);
            if (itemId == R.id.filter_by_all) {
                getCourierOrdersViewModel.getAllOrders();
                return true;
            } else if (itemId == R.id.filter_by_pending) {
                getCourierOrdersViewModel.getOrdersBasedStatus(PENDING);
                return true;
            } else if (itemId == R.id.filter_by_completed) {
                getCourierOrdersViewModel.getOrdersBasedStatus(SHIPPED);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }


    private void observeViewModel() {

        getCourierOrdersViewModel.listOrders.observe(getViewLifecycleOwner(), orders -> {
            if (!orders.isEmpty()) {
                updateRecyclerView(orders);
            }
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

    private void updateRecyclerView(List<Order> orders) {
        courierOrdersAdapter = new CourierOrdersAdapter(orders,this);
        binding.ordersRecycler.setAdapter(courierOrdersAdapter);
        updateUiStatus(true);


    }

    private void updateUiStatus(boolean status) {
    if (status){
        binding.ordersRecycler.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }else {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.ordersRecycler.setVisibility(View.GONE);
    }

    }

    @Override
    public void onOrderClicked(Order order) {

        navController.navigate
                (CourierHomeFragmentDirections.actionCourierHomeFragmentToOrderDetailFragment(order));
    }
}