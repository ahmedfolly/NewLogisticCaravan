package com.example.logisticcavan.sharedcart.presentation;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.example.logisticcavan.orders.addorder.presentation.AddOrderViewModel;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.example.logisticcavan.sharedcart.domain.model.SharedCartItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProceedToSharedOrderFragment extends Fragment {

    private AuthViewModel authViewModel;
    private AddOrderViewModel addOrderViewModel;
    private TextInputEditText villaNumInput;
    private ProceedToSharedOrderFragmentArgs args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        addOrderViewModel = new ViewModelProvider(this).get(AddOrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proceed_to_shared_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        villaNumInput = requireView().findViewById(R.id.villa_number_input_sharedOrder);
        args = ProceedToSharedOrderFragmentArgs.fromBundle(getArguments());
        SharedCartItem[] sharedCartItems = args.getSharedProducts();
        MaterialButton placeOrderButton = view.findViewById(R.id.place_shared_order_btn);
        RadioGroup radioGroup = view.findViewById(R.id.payment_methods_group);
        MaterialRadioButton cashOnDeliveryBtn = view.findViewById(R.id.cash_on_delivery_radio_button);
        MaterialRadioButton cardBtn = view.findViewById(R.id.credit_card_radio_button);

        MaterialRadioButton applePay = view.findViewById(R.id.apple_pay_radio_button);

        placeOrderButton.setOnClickListener(v -> {
            Order order = createOrderToUpload(sharedCartItems, args.getRestaurantId(), args.getRestaurantName(),
                    getPaymentMethod(radioGroup, cashOnDeliveryBtn, cardBtn, applePay));
            addOrderViewModel.addOrder(order, new AddOrderViewModel.UploadOrderCallback() {
                @Override
                public void onSuccess(String orderId) {
                    addOrderViewModel.addOrderIdToUser(orderId, Arrays.asList(args.getUserIds()), new AddOrderViewModel.UploadOrderToUser() {
                        @Override
                        public void onSuccess(String message) {
                            NavDirections directions = ProceedToSharedOrderFragmentDirections.actionProceedToSharedOrderFragmentToTrakOrderFragment(Constant.flagFromPlaceOrderScreen, "", "", "");
                            findNavController(requireView()).navigate(directions);
                        }
                        @Override
                        public void onError(String message) {

                        }
                    });

                }

                @Override
                public void onError(Exception e) {
                    Log.d("TAG", "onError: " + e);
                }

                @Override
                public void onLoading() {

                }
            });
        });

//        List<UserInfo> users = new ArrayList<>();
//        AtomicInteger remainingTasks = new AtomicInteger(args.getUserIds().length);  // Keep track of remaining tasks
//
//// Start async fetching for each user
//        for (String email : args.getUserIds()) {
//            authViewModel.getUserRemotely(email)
//                    .thenAccept(userInfo -> {
//                        // Append the userInfo to the list
//                        users.add(userInfo);
//
//                        // Decrease the remaining tasks count
//                        if (remainingTasks.decrementAndGet() == 0) {
//                            // Once all tasks are completed, update the adapter
//                            requireActivity().runOnUiThread(() -> {
//                                // Assuming 'adapter' is your RecyclerView adapter or similar
//                              for (UserInfo userInfo1:users){
//                                  Log.d("TAG", "onViewCreated: "+userInfo1.getName());
//                              }
//                            });
//                        }
//                    })
//                    .exceptionally(ex -> {
//                        // Handle errors if any
//                        Log.e("User Fetch Error", "Error fetching user data", ex);
//                        return null;
//                    });
//        }
    }

    private String getPaymentMethod(RadioGroup radioGroup, MaterialRadioButton cashOnDeliveryBtn, MaterialRadioButton cardBtn, MaterialRadioButton applePay) {
        String paymentMethod = "";
        int id = radioGroup.getCheckedRadioButtonId();
        if (id != -1) {
            if (id == cashOnDeliveryBtn.getId()) {
                paymentMethod = "Cash on delivery";
            } else if (id == cardBtn.getId()) {
                paymentMethod = "Credit card";
            } else if (id == applePay.getId()) {
                paymentMethod = "Apple pay";
            }
        }
        return paymentMethod;
    }

    private Order createOrderToUpload(SharedCartItem[] sharedCartItems, String restaurantId, String restaurantName, String paymentMethod) {
        Order order = new Order();
        order.setCartItems(createSharedProducts(sharedCartItems));
        order.setCourier(createCourier());
        order.setDeliveryTime(createDeliveryTime("17/10/1997", "12.00-13.0"));
        order.setLocation(createLocationMap(getDurrahPeach(), getVillaNum()));
        order.setCustomer(createUserMap());
        order.setRestaurant(createRestaurant(restaurantId, restaurantName));
        order.setGeneralDetails(createGeneralDetails(paymentMethod, 0.0));
        order.setCustomers(Arrays.asList(args.getUserIds()));
        return order;
    }

    private String getDurrahPeach() {
        Spinner durrahBeachSpinner = requireView().findViewById(R.id.durrah_beach_picker);
        return durrahBeachSpinner.getSelectedItem().toString();
    }

    private String getVillaNum() {
        return Objects.requireNonNull(villaNumInput.getText()).toString();
    }

    private Map<String, String> createLocationMap(String beach, String villaNum) {
        Map<String, String> locationMap = new HashMap<>();
        locationMap.put("beach", beach);
        locationMap.put("villaNum", villaNum);
        return locationMap;
    }

    private Map<String, String> createUserMap() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", getUserId());
        userMap.put("name", getUserName());
        return userMap;
    }

    private List<String> getUserEmails(List<String> emails) {
        Map<String, Object> usersEmailsToUpload = new HashMap<>();
        usersEmailsToUpload.put("customers", emails);
        return new ArrayList<>(emails);
    }

    private Map<String, String> createDeliveryTime(String date, String time) {
        Map<String, String> deliveryTime = new HashMap<>();
        deliveryTime.put("date", date);
        deliveryTime.put("time", time);
        return deliveryTime;
    }

    private Map<String, String> createCourier() {
        Map<String, String> courier = new HashMap<>();
        courier.put("id", "YA09QYmCJFdRK8DwreWxKKaqq612");
        courier.put("name", "Ali");
        courier.put("token", "token");
        return courier;
    }

    private Map<String, String> createRestaurant(String id, String name) {
        Map<String, String> restaurant = new HashMap<>();
        restaurant.put("id", id);
        restaurant.put("name", name);
        return restaurant;
    }

    private List<Map<String, Object>> createSharedProducts(SharedCartItem[] sharedCartItems) {
        List<Map<String, Object>> sharedProducts = new ArrayList<>();
        for (SharedCartItem sharedCartItem : sharedCartItems) {
            Map<String, Object> sharedProduct = new HashMap<>();
            sharedProduct.put("productName", sharedCartItem.getProduct().getProductName());
            sharedProduct.put("quantity", sharedCartItem.getSharedProduct().getQuantity());
            sharedProduct.put("category", sharedCartItem.getProduct().getProductCategory());
            sharedProducts.add(sharedProduct);
        }
        return sharedProducts;
    }

    private Map<String, Object> createGeneralDetails(String paymentMethod, double totalPrice) {
        Map<String, Object> generalDetails = new HashMap<>();
        generalDetails.put("paymentMethod", paymentMethod);
        generalDetails.put("totalPrice", totalPrice);
        generalDetails.put("status", "Pending");
        return generalDetails;
    }

    private String getUserId() {
        return authViewModel.getUserInfoLocally().getId();
    }

    private String getUserName() {
        return authViewModel.getUserInfoLocally().getName();
    }

}