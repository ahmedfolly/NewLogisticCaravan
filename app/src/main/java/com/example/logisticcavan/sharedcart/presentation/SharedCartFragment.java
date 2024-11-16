package com.example.logisticcavan.sharedcart.presentation;

import static com.example.logisticcavan.common.utils.Constant.EMAIL_REGEX;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedCartItem;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SharedCartFragment extends Fragment {

    private RecyclerView sharedItemsContainer;
    private SharedCartItemsAdapter sharedCartItemsAdapter;
    private GetSharedProductsViewModel getSharedProductsViewModel;
    private AddNewUserEmailToSharedCartViewModel addUserEmailViewModel;
    private AuthViewModel authViewModel;
    private GetSharedCartViewModel getSharedCartViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedProductsViewModel = new ViewModelProvider(this).get(GetSharedProductsViewModel.class);
        addUserEmailViewModel = new ViewModelProvider(this).get(AddNewUserEmailToSharedCartViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        getSharedCartViewModel = new ViewModelProvider(this).get(GetSharedCartViewModel.class);
        sharedCartItemsAdapter = new SharedCartItemsAdapter(authViewModel,getSharedCartViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSharedItems();
        MaterialButton addUserEmailButton = view.findViewById(R.id.add_user_to_shared_cart);
        addUserEmailButton.setOnClickListener(v -> addUserEmail());
    }

    private void getSharedItems() {
        getSharedProductsViewModel.fetchSharedProducts();
        getSharedProductsViewModel.getSharedProductsLiveData().observe(getViewLifecycleOwner(), myResult -> {
            List<SharedCartItem> sharedCartItems = new ArrayList<>();

            myResult.handle(
                    sharedProductsWithProducts -> {
                        Log.d("TAG", "getSharedItems: "+sharedProductsWithProducts.size());
                        for (SharedProductWithSharedCart sharedProductWithSharedCart : sharedProductsWithProducts) {
                            List<Product> products = sharedProductWithSharedCart.getProducts();
                            List<SharedProduct> sharedProducts = sharedProductWithSharedCart.getSharedProducts();

                            // Assuming that each "shared cart" will have the same products and shared products together
                            for (int i = 0; i < Math.max(products.size(), sharedProducts.size()); i++) {
                                // Get the product and shared product by index
                                Product product = i < products.size() ? products.get(i) : null;
                                SharedProduct sharedProduct = i < sharedProducts.size() ? sharedProducts.get(i) : null;

                                // Add the combined item to the list
                                sharedCartItems.add(new SharedCartItem(product, sharedProduct));
                            }
                        }
                        sharedCartItemsAdapter.submitList(sharedCartItems);
                        setupRecyclerView();
                    },
                    error -> {
                    },
                    () -> {
                    }
            );
        });
    }
    private void setupRecyclerView() {
        sharedItemsContainer = requireView().findViewById(R.id.shared_items_container);
        sharedItemsContainer.setHasFixedSize(true);
        sharedItemsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        sharedItemsContainer.setAdapter(sharedCartItemsAdapter);
    }
    private void addUserEmail(){
        TextInputEditText userEmailPicker = requireView().findViewById(R.id.user_email_picker);
        String userEmail = Objects.requireNonNull(userEmailPicker.getText()).toString();
        if (!TextUtils.isEmpty(userEmail)){
            if (isValidEmail(userEmail)){
                //here add user email to shared cart
                addUserEmailViewModel.addUserToSharedCart(userEmail, result -> Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show());
            }else {
                userEmailPicker.setError("Please enter a valid email");
            }
        }else {
            userEmailPicker.setError("Please enter a valid email");
        }
    }
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        // Compile the regex pattern and create a matcher
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        // Return whether the email matches the regex pattern
        return matcher.matches();
    }
}