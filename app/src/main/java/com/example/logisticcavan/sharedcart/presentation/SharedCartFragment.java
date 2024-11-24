package com.example.logisticcavan.sharedcart.presentation;

import static androidx.navigation.fragment.FragmentKt.findNavController;
import static com.example.logisticcavan.common.utils.Constant.EMAIL_REGEX;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedCartItem;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SharedCartFragment extends Fragment implements SharedCartItemsAdapter.DeleteBtnListener {

    private SharedCartItemsAdapter sharedCartItemsAdapter;
    private GetSharedProductsViewModel getSharedProductsViewModel;
    private AddNewUserEmailToSharedCartViewModel addUserEmailViewModel;
    private GetSharedCartViewModel getSharedCartViewModel;
    private AuthViewModel authViewModel;
    private NavController navController;

    private DeleteSharedProductViewModel deleteSharedCartViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedProductsViewModel = new ViewModelProvider(this).get(GetSharedProductsViewModel.class);
        addUserEmailViewModel = new ViewModelProvider(this).get(AddNewUserEmailToSharedCartViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        getSharedCartViewModel = new ViewModelProvider(this).get(GetSharedCartViewModel.class);
        deleteSharedCartViewModel = new ViewModelProvider(this).get(DeleteSharedProductViewModel.class);
        sharedCartItemsAdapter = new SharedCartItemsAdapter(authViewModel, getSharedCartViewModel,this);
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
        TextInputEditText userEmailPicker = view.findViewById(R.id.user_email_picker);
        MaterialButton moveToNext = requireView().findViewById(R.id.checkout_shared_order);
        TextInputLayout userEmailLayout = requireView().findViewById(R.id.user_email_layout);
        getSharedItems();
        MaterialButton addUserEmailButton = view.findViewById(R.id.add_user_to_shared_cart);
        addUserEmailButton.setOnClickListener(v -> addUserEmail(userEmailPicker));
        doOnNotAdminUser(userEmailPicker, addUserEmailButton,moveToNext,userEmailLayout);
        navController = findNavController(this);
        goForward(moveToNext);
        //on back pressed
        view.findViewById(R.id.back_from_shared_cart).setOnClickListener(v -> navController.popBackStack());
    }
    private void goForward(MaterialButton moveToNext){
        moveToNext.setOnClickListener(v -> {
            SharedCartItem[] sharedCartItems = sharedCartItemsAdapter.getCurrentList().toArray(new SharedCartItem[0]);
            getSharedCartViewModel.getSharedCart(new GetSharedCartViewModel.SharedCartCallback() {
                @Override
                public void onSuccess(SharedCart sharedCart) {
                    String restaurantId = sharedCart.getRestaurantId();
                    String restaurantName = sharedCart.getRestaurantName();
                    String[] customers = sharedCart.getUserIds().toArray(new String[0]);
                    NavDirections action = SharedCartFragmentDirections
                            .actionSharedCartFragmentToProceedToSharedOrderFragment(sharedCartItems,
                                    restaurantName,
                                    restaurantId,
                                    customers,
                                    sharedCart);
                    navController.navigate(action);
                }

                @Override
                public void onError(String errorMessage) {

                }
            });

        });
    }
    private void doOnNotAdminUser(TextInputEditText userEmailPicker, MaterialButton addUserEmailButton,MaterialButton checkout,
                                  TextInputLayout textInputLayout) {
        getSharedCartViewModel.getSharedCart(new GetSharedCartViewModel.SharedCartCallback() {
            @Override
            public void onSuccess(SharedCart sharedCart) {
                String adminId = sharedCart.getAdminId();
                if (!adminId.equals(getUserEmail())) {
                    userEmailPicker.setVisibility(View.GONE);
                    addUserEmailButton.setVisibility(View.GONE);
                    checkout.setVisibility(View.GONE);
                    textInputLayout.setVisibility(View.GONE);
                }else {
                    userEmailPicker.setVisibility(View.VISIBLE);
                    addUserEmailButton.setVisibility(View.VISIBLE);
                    checkout.setVisibility(View.VISIBLE);
                    textInputLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    private void getSharedItems() {
        getSharedProductsViewModel.fetchSharedProducts();
        getSharedProductsViewModel.getSharedProductsLiveData().observe(getViewLifecycleOwner(), myResult -> {
            List<SharedCartItem> sharedCartItems = new ArrayList<>();
            myResult.handle(
                    sharedProductsWithProducts -> {
                        for (SharedProductWithSharedCart sharedProductWithSharedCart : sharedProductsWithProducts) {
                            List<Product> products = sharedProductWithSharedCart.getProducts();
                            List<SharedProduct> sharedProducts = sharedProductWithSharedCart.getSharedProducts();
                            for (int i = 0; i < products.size(); i++) {
                                Product product = products.get(i);
                                SharedProduct sharedProduct = findSharedProductByProductId(sharedProducts, product.getProductID());

                                if (sharedProduct != null) {
                                    sharedCartItems.add(new SharedCartItem(product, sharedProduct));
                                }
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
    private SharedProduct findSharedProductByProductId(List<SharedProduct> sharedProducts, String productId) {
        for (SharedProduct sharedProduct : sharedProducts) {
            if (sharedProduct.getProductId().equals(productId)) {
                return sharedProduct;
            }
        }
        return null;  // Return null if no match is found
    }
    private void setupRecyclerView() {
        RecyclerView sharedItemsContainer = requireView().findViewById(R.id.shared_items_container);
        sharedItemsContainer.setHasFixedSize(true);
        sharedItemsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        sharedItemsContainer.setAdapter(sharedCartItemsAdapter);
    }
    private void addUserEmail(TextInputEditText userEmailPicker) {
        String userEmail = Objects.requireNonNull(userEmailPicker.getText()).toString();
        if (!TextUtils.isEmpty(userEmail)) {
            if (isValidEmail(userEmail)) {
                //here add user email to shared cart
                addUserEmailViewModel.addUserToSharedCart(userEmail, result -> Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show());
                userEmailPicker.setText("");
            } else {
                userEmailPicker.setError("Please enter a valid email");
            }
        } else {
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
    private String getUserEmail() {
        return authViewModel.getUserInfoLocally().getEmail();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.disappearBottomNav();
        }
    }
    @Override
    public void onDeleteBtnClicked(String productId) {
        deleteSharedCartViewModel.deleteSharedCartProduct(productId, new DeleteSharedProductViewModel.DeleteSharedProductCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

}