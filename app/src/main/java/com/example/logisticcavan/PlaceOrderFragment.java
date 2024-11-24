package com.example.logisticcavan;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.orders.addorder.presentation.AddOrderViewModel;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlaceOrderFragment extends Fragment {

    private CartViewModel cartViewModel;
    private AddOrderViewModel addOrderViewModel;
    private PlaceOrderFragmentArgs data;
    private ProgressBar progressBar;
    private AuthViewModel authViewModel;

    private static OrderPlaceCallback orderPlaceCallback;
    NavController navController;
    public PlaceOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        addOrderViewModel = new ViewModelProvider(this).get(AddOrderViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_order, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.upload_order_progress);
        navController = findNavController(this);
        data = PlaceOrderFragmentArgs.fromBundle(getArguments());
        String locationBeach = data.getBeach();
        String locationVilla = data.getVillaNum();
        double orderprice = data.getPrice();
        Order order = data.getOrder();
        Log.d("TAG", "onViewCreated: " + order.getCartItems().size());
        TextView locationText = view.findViewById(R.id.picked_location);
        TextView orderPrice = view.findViewById(R.id.order_total_place_order);
        TextView totalPriceText = view.findViewById(R.id.totale_price_place_order);
        locationText.setText(locationBeach + " villa " + locationVilla);
        orderPrice.setText(orderprice + " SR");
        double totalPrice = orderprice + 10.0;
        totalPriceText.setText(totalPrice + " SR");
        MaterialButton placeOrderBtn = view.findViewById(R.id.place_order_btn);
        RadioGroup radioGroup = view.findViewById(R.id.payment_methods_group);
        MaterialRadioButton cashOnDeliveryBtn = view.findViewById(R.id.cash_on_delivery_radio_button);
        MaterialRadioButton cardBtn = view.findViewById(R.id.credit_card_radio_button);
        MaterialRadioButton applePay = view.findViewById(R.id.apple_pay_radio_button);
        cartViewModel.getCartItems();
        String date = data.getDate();
        String time = data.getTime();
        placeOrderBtn.setOnClickListener(v -> {
            String paymentMethod = "";
            int id = radioGroup.getCheckedRadioButtonId();
            if (id !=-1){
                if (id == cashOnDeliveryBtn.getId()) {
                    paymentMethod = "Cash on delivery";
                } else if (id == cardBtn.getId()) {
                    paymentMethod = "Credit card";
                } else if (id == applePay.getId()) {
                    paymentMethod = "Apple pay";
                }
                order.setGeneralDetails(createGeneralDetails(paymentMethod, totalPrice));
                List<String> customers = new ArrayList<>();
                customers.add(getUserEmail());
                order.setCustomers(customers);
                if (date != null && time != null){
                    Map<String, String> deliveryTime = new HashMap<>();
                    deliveryTime.put("date", date);
                    deliveryTime.put("time", time);
                    order.setDeliveryTime(deliveryTime);
                }else{
                    Map<String, String> deliveryTime = new HashMap<>();
                    deliveryTime.put("date", "now");
                    deliveryTime.put("time", "now");
                    order.setDeliveryTime(deliveryTime);
                }
                uploadOrder(order);
            }else {
                applePay.setError("Please select a payment method");
            }
        });

    }

    private void uploadOrder(Order order) {
        Objects.requireNonNull(cartViewModel.getCartItemsData().getValue())
                .handle(cartItems -> addOrderViewModel.addOrder(order,
                        new AddOrderViewModel.UploadOrderCallback() {
            @Override
            public void onSuccess(String orderId) {
                Log.d("TAG", "uploadOrder: uploaded");
                progressBar.setVisibility(View.GONE);
                cartViewModel.emptyCart(new CartViewModel.EmptyCartResultCallback() {
                    @Override
                    public void onSuccess(boolean isDeleted) {
                        NavDirections directions = PlaceOrderFragmentDirections.actionPlaceOrderFragmentToTrakOrderFragment(Constant.flagFromPlaceOrderScreen,"","","");
                        navController.navigate(directions);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onLoading() {
                progressBar.setVisibility(View.VISIBLE);
            }
        }), error -> {
        }, () -> {
        });
    }
    private Map<String, Object> createGeneralDetails(String paymentMethod, double totalPrice) {
        Map<String, Object> generalDetails = new HashMap<>();
        generalDetails.put("paymentMethod", paymentMethod);
        generalDetails.put("totalPrice", totalPrice);
        generalDetails.put("status", "Pending");
        return generalDetails;
    }
    public static void setOrderPlaceCallback(OrderPlaceCallback orderPlaceCallback) {
        PlaceOrderFragment.orderPlaceCallback = orderPlaceCallback;
    }

    public interface OrderPlaceCallback {
        void onSuccess();
        void onError();
        void onLoading();
    }
    private String getUserEmail(){
        return authViewModel.getUserInfoLocally().getEmail();
    }
}