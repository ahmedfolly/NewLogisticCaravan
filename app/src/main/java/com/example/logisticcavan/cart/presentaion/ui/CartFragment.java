package com.example.logisticcavan.cart.presentaion.ui;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.logisticcavan.PlaceOrderFragment;
import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartItemsAdapter;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.DetectQuantityUtil;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.example.logisticcavan.orders.addorder.presentation.AddOrderViewModel;
import com.example.logisticcavan.orders.addorder.presentation.ui.ConfirmOrderBottomFragment;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment implements ConfirmOrderBottomFragment.ConfirmOrderCallback,
        DetectQuantityUtil.UpdateCartItemCallback,
        CartItemsAdapter.OnDeleteButtonClicked{
    private CartViewModel cartViewModel;
    private RecyclerView cartItemsContainer;
    private AddOrderViewModel addOrderViewModel;
    private CartItemsAdapter cartsAdapter;
    private AuthViewModel authViewModel;
    MaterialButton checkoutBtn;
    TextView cartFragmentTitle, deliveryAddressText, itemsText;
    CardView locationCard;
    String title = "";
    NavController navController;

    private Map<String,Object> deliveryTimeMap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        addOrderViewModel = new ViewModelProvider(this).get(AddOrderViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        cartsAdapter = new CartItemsAdapter(this);
        DetectQuantityUtil.setUpdateCartItemCallback(this);
        deliveryTimeMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView backArrowBtn = view.findViewById(R.id.cart_fragment_back_arrow_btn);
        cartFragmentTitle = view.findViewById(R.id.cart_fragment_title);
        backArrowBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        cartItemsContainer = view.findViewById(R.id.cart_items_container);
        checkoutBtn = view.findViewById(R.id.order_cart_btn);
        deliveryAddressText = view.findViewById(R.id.delivery_address_text);
        locationCard = view.findViewById(R.id.location_card);
        itemsText = view.findViewById(R.id.items_text);
        Spinner beachSpinner = view.findViewById(R.id.durrah_beach_picker);
        Spinner deliveryWindowSpinner = view.findViewById(R.id.delievery_window_spinner);
        TextInputEditText villaNumberEd = view.findViewById(R.id.villa_number_input);
        getCartItems();
        setupCartItemsContainer();
        navController = findNavController(this);
        checkoutBtn.setOnClickListener(v -> {
            String villaNum = Objects.requireNonNull(villaNumberEd.getText()).toString();
            if (!deliveryTimeMap.isEmpty()){
                Log.d("TAG", "onViewCreated: "+deliveryTimeMap.get("time"));
            }
            if (!TextUtils.isEmpty(villaNum)) {
                cartViewModel.fetchTotalPrice();
                cartViewModel.getTotalPriceData().observe(getViewLifecycleOwner(), price -> {
//                    uploadOrder(beachSpinner.getSelectedItem().toString(), Objects.requireNonNull(villaNumberEd.getText()).toString(), "Cash on delivery",price);
                    cartViewModel.getCartItemsData().observe(getViewLifecycleOwner(), cartItemsResult -> {
                        cartItemsResult.handle(cartItems -> {
                                    Order order = createOrder(createCartItems(cartItems),
                                            createCourier(),
                                            createDeliveryTime("17/10/1997", "12.00-13.0"),
                                            createLocationMap(beachSpinner.getSelectedItem().toString(), villaNum),
                                            createUserMap(getUserId(), getUserName()),
                                            createRestaurant(cartItems.get(0).getRestaurantId(), cartItems.get(0).getRestaurantName()));
                                    NavDirections action ;
                                    if (!deliveryTimeMap.isEmpty()){
                                         action = CartFragmentDirections.actionCartFragmentToPlaceOrderFragment(order,
                                                beachSpinner.getSelectedItem().toString(),
                                                villaNum, price.floatValue(),
                                                Objects.requireNonNull(deliveryTimeMap.get("time")).toString(),
                                                Objects.requireNonNull(deliveryTimeMap.get("date")).toString());
                                    }else{
                                        action = CartFragmentDirections.actionCartFragmentToPlaceOrderFragment(order,
                                                beachSpinner.getSelectedItem().toString(),
                                                villaNum, price.floatValue(),
                                                null,
                                                null);
                                    }
                                    navController.navigate(action);
                                },
                                error -> {
                                },
                                () -> {
                                });
                    });

                });
            } else {
                villaNumberEd.setError("Villa number is required");
            }
        });
        detectTime(deliveryWindowSpinner);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.disappearBottomNav();
        }
    }

    private void getCartItems() {
        cartViewModel.getCartItems();
        cartViewModel.getCartItemsData().observe(getViewLifecycleOwner(), cartItemsResult -> {
            cartItemsResult.handle(cartItems -> {
                cartsAdapter.submitList(cartItems);
                Log.d("TAG", "onViewCreated: " + cartsAdapter.getItemCount());
                if (!cartItems.isEmpty()) {
                    title = cartItems.get(0).getRestaurantName();
                    cartFragmentTitle.setText("Cart of " + cartItems.get(0).getRestaurantName());
                }
                if (cartItems.isEmpty()) {
                    returnToHomeFragment();
                }
            }, error -> {
            }, () -> {
            });
        });
    }

    private void setupCartItemsContainer() {
        cartItemsContainer.setLayoutManager(new LinearLayoutManager(requireContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        cartItemsContainer.setNestedScrollingEnabled(false);
        cartItemsContainer.setAdapter(cartsAdapter);
    }



    //    Order createOrder(List<Map<String, Object>> carItemsMap, String restaurantId, String restaurantName, String location, String phone, String paymentMethod) {
//        Order order = new Order();
//        order.setCartItems(carItemsMap);
//        order.setClientName(getUserName());
//        order.setClientLocation(location);
//        order.setClientPhone(phone);
//        order.setRestaurantName(restaurantName);
//        order.setStatus("PENDING");
//        order.setTo(restaurantId);
//        order.setPayment(paymentMethod);
//        return order;
//    }
    Order createOrder(List<Map<String, Object>> carItemsMap,
                      Map<String, String> courier,
                      Map<String, String> deliveryTime,
                      Map<String, String> location,
                      Map<String, String> customer,
                      Map<String, String> restaurant) {
        Order order = new Order();
        order.setCartItems(carItemsMap);
        order.setLocation(location);
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setCourier(courier);
        order.setDeliveryTime(deliveryTime);
        return order;
    }

    @Override
    public void onConfirmOrder(String userLocation, String phone, String paymentMethod) {
//        uploadOrder(userLocation, phone, paymentMethod);
        emptyCart();
    }

    private String getUserName() {
        UserInfo userInfo = authViewModel.getUserInfoLocally();
        if (userInfo != null) {
            return userInfo.getName();
        }
        return "";
    }

    private String getUserId() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }

    private void emptyCart() {
        cartViewModel.emptyCart(new CartViewModel.EmptyCartResultCallback() {
            @Override
            public void onSuccess(boolean isDeleted) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void returnToHomeFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void updateCartItem(int id, int quantity, double price) {
        Log.d("TAG", "updateCartItem: " + price);
        cartViewModel.updateQuantity(id, quantity, price);
    }

    @Override
    public void onDeleteButtonClicked(int id) {
        cartViewModel.deleteItemById(id);
    }

    private void detectTime(Spinner arriveTimeSpinner){
        String[] scheduleList = getResources().getStringArray(R.array.schedule_list);
        TextView timeTxt = requireView().findViewById(R.id.selected_time);
        arriveTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = scheduleList[i];
                if (selectedItem.equals("Scheduled time")){
                    timeTxt.setVisibility(View.VISIBLE);
                    pickDateTime(timeTxt);
                }else{
                    timeTxt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void pickDateTime(TextView textViewDateTime) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Show DatePickerDialog first
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // After date is picked, show TimePickerDialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                            (timeView, selectedHour, selectedMinute) -> {
                                // Display the selected date and time
                                String dateTime = "Selected Date: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear +
                                        "\nSelected Time: " + String.format("%02d:%02d", selectedHour, selectedMinute);
                                String dateTime1 = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                                deliveryTimeMap.put("date", dateTime1);
                                deliveryTimeMap.put("time", time);
                                textViewDateTime.setText(dateTime);
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);

        datePickerDialog.show();
    }
    public interface CartFragmentOpenedCallback {
        void onCartFragmentOpened();
    }

    private Map<String, String> createLocationMap(String beach, String villaNum) {
        Map<String, String> locationMap = new HashMap<>();
        locationMap.put("beach", beach);
        locationMap.put("villaNum", villaNum);
        return locationMap;
    }

    private Map<String, String> createUserMap(String id, String name) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", id);
        userMap.put("name", name);
        return userMap;
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
    private List<Map<String, Object>> createCartItems(List<CartItem> cartItems) {
        List<Map<String, Object>> carItemsList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Map<String, Object> cartItemsMap = new HashMap<>();
            cartItemsMap.put("productName", cartItem.getProductName());
            cartItemsMap.put("quantity", cartItem.getQuantity());
            cartItemsMap.put("category",cartItem.getCategoryName());
            carItemsList.add(cartItemsMap);
        }
        return carItemsList;
    }

}