package com.example.logisticcavan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartItemsAdapter;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.orders.addorder.presentation.AddOrderViewModel;
import com.example.logisticcavan.orders.addorder.presentation.ui.ConfirmOrderBottomFragment;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment implements ConfirmOrderBottomFragment.ConfirmOrderCallback {
    private CartViewModel cartViewModel;
    private RecyclerView cartItemsContainer;
    private AddOrderViewModel addOrderViewModel;
    private CartItemsAdapter cartsAdapter;
    private AuthViewModel authViewModel;
    MaterialButton checkoutBtn;
    private CartFragmentOpenedCallback cartFragmentOpenedCallback;
    TextView cartFragmentTitle;
    String title="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        addOrderViewModel = new ViewModelProvider(this).get(AddOrderViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        cartsAdapter = new CartItemsAdapter();
        cartFragmentOpenedCallback.onCartFragmentOpened();
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
        setupCartItemsContainer();
        getCartItems();
        checkoutBtn.setOnClickListener(v -> {
            ConfirmOrderBottomFragment confirmOrderBottomFragment = new ConfirmOrderBottomFragment(this);
            confirmOrderBottomFragment.show(requireActivity().getSupportFragmentManager(), "confirmOrder");
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CartFragmentOpenedCallback) {
            cartFragmentOpenedCallback = (CartFragmentOpenedCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CartFragmentOpenedCallback");
        }
    }

    private void getCartItems() {
        cartViewModel.getCartItems();
        cartViewModel.getCartItemsData().observe(getViewLifecycleOwner(), cartItemsResult -> {
            cartItemsResult.handle(cartItems -> {
                cartsAdapter.submitList(cartItems);
                setupCheckoutButtonVisibility(cartItems);
                if (!cartItems.isEmpty()) {
                    title = cartItems.get(0).getRestaurantName();
                    cartFragmentTitle.setText("Cart of "+cartItems.get(0).getRestaurantName());
                }
            }, error -> {
            }, () -> {
            });
        });
    }

    private void setupCartItemsContainer() {
        cartItemsContainer.setHasFixedSize(true);
        cartItemsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        cartItemsContainer.setAdapter(cartsAdapter);
    }

    private void uploadOrder(String location, String phone, String paymentMethod) {
        List<Map<String, Object>> carItemsMap = new ArrayList<>();
        Objects.requireNonNull(cartViewModel.getCartItemsData().getValue()).handle(cartItems -> {
            for (CartItem cartItem : cartItems) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("productName", cartItem.getProductName());
                itemMap.put("quantity", cartItem.getQuantity());
                carItemsMap.add(itemMap);
            }
            Log.d("TAG", "uploadOrder: " + cartItems.get(0).getRestaurantName());
            addOrderViewModel.addOrder(createOrder(carItemsMap, cartItems.get(0).getRestaurantId(), cartItems.get(0).getRestaurantName(), location, phone, paymentMethod), new AddOrderViewModel.UploadOrderCallback() {
                @Override
                public void onSuccess(String message) {
                    Log.d("TAG", "order uploaded ");
                }

                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onLoading() {

                }
            });
        }, error -> {
        }, () -> {
        });
    }

    Order createOrder(List<Map<String, Object>> carItemsMap, String restaurantId, String restaurantName, String location, String phone, String paymentMethod) {
        Order order = new Order();
        order.setCartItems(carItemsMap);
        order.setClientName(getUserName());
        order.setClientLocation(location);
        order.setClientPhone(phone);
        order.setRestaurantName(restaurantName);
        order.setStatus("PENDING");
        order.setTo(restaurantId);
        order.setPayment(paymentMethod);
        return order;
    }

    @Override
    public void onConfirmOrder(String userLocation, String phone, String paymentMethod) {
        Log.d("TAG", "onConfirmOrder: " + userLocation + phone + paymentMethod);
        uploadOrder(userLocation, phone, paymentMethod);
        emptyCart();
    }

    private String getUserName() {
        UserInfo userInfo = authViewModel.getUserInfoLocally();
        if (userInfo != null) {
            return userInfo.getName();
        }
        return "";
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

    private void returnToHomeScreen() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void setupCheckoutButtonVisibility(List<CartItem> cartItems) {
        checkoutBtn.setVisibility(cartItems.isEmpty() ? View.GONE : View.VISIBLE);
    }
    public interface CartFragmentOpenedCallback{
        void onCartFragmentOpened();
    }
}