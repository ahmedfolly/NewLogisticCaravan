package com.example.logisticcavan.caravan.presentation;

import static com.example.logisticcavan.common.utils.Constant.BAKERY;
import static com.example.logisticcavan.common.utils.Constant.CARAVAN;
import static com.example.logisticcavan.common.utils.Constant.DRINKS;
import static com.example.logisticcavan.common.utils.Constant.GROCERY;
import static com.example.logisticcavan.common.utils.Constant.PHARMACY;
import static com.example.logisticcavan.common.utils.Constant.PRODUCTS;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.logisticcavan.R;
import com.example.logisticcavan.caravan.domain.model.Products;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.ui.AddOrderBottomSheet;
import com.example.logisticcavan.databinding.FragmentCaravanBinding;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.RestaurantProductsAdapter;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class CaravanFragment extends Fragment implements RestaurantProductsAdapter.FoodItemClickListener, AddOrderBottomSheet.AddToCartCallback ,
        AddOrderBottomSheet.AddToSharedCartCallback{

    private FragmentCaravanBinding binding;
    private RestaurantProductsAdapter restaurantProductsAdapter;
    private CartViewModel cartViewModel;


    @Inject
    CaravanViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCaravanBinding.inflate(inflater, container, false);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        setUpClickListener();
        viewModel.getCaravanProducts();
        observeViewModel();
        return binding.getRoot();
    }

    private void observeViewModel() {
        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                restaurantProductsAdapter = new RestaurantProductsAdapter(getParentFragmentManager(), this);
                filterByCategory(GROCERY, products);
                binding.progressBar.setVisibility(View.GONE);
            } else {
                binding.noItems.setVisibility(View.VISIBLE);
            }
        });
    }

    private void filterByCategory(String grocery, List<Product> products) {
        if (products.isEmpty()) {
            binding.noItems.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : products) {
                if (product.getProductCategory().equals(grocery)) {
                    filteredList.add(product);
                }
            }
            if (filteredList.isEmpty()) {
                binding.noItems.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                restaurantProductsAdapter.submitList(filteredList);
                binding.recyclerView.setAdapter(restaurantProductsAdapter);
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setUpClickListener() {

        binding.grocery.setOnClickListener(view -> {
            filterByCategory(GROCERY, viewModel.products.getValue());
            setFocus(binding.grocery, binding.pharmacy, binding.bakery, binding.drinks);
        });
        binding.pharmacy.setOnClickListener(view -> {
            filterByCategory(PHARMACY, viewModel.products.getValue());
            setFocus(binding.pharmacy, binding.grocery, binding.bakery, binding.drinks);
        });

        binding.bakery.setOnClickListener(view -> {
            filterByCategory(BAKERY, viewModel.products.getValue());
            setFocus(binding.bakery, binding.grocery, binding.pharmacy, binding.drinks);
        });
        binding.drinks.setOnClickListener(view -> {
            filterByCategory(DRINKS, viewModel.products.getValue());
            setFocus(binding.drinks, binding.grocery, binding.pharmacy, binding.bakery);
        });
    }

    private void setFocus(TextView focusedView, TextView... otherViews) {

        focusedView.setTextColor(Color.parseColor("#9E1E1E"));
        for (TextView textView : otherViews) {
            textView.setTextColor(getResources().getColor(R.color.grey_color));
        }
    }



    @Override
    public void addToCart(Product product, int quantity, double price) {
        CartItem cartItem = getCartItem(product, quantity, price);
        cartViewModel.getCartCount(new CartViewModel.CartCountCallback() {
            @Override
            public void onSuccess(int count) {
                Log.d("TAG", "main get cart count " + count);
                if (count == 0) {
                    Log.d("TAG", "main  add item to empty database ");
                    addCartItemToCart(cartItem);
                } else {
                    setupWarningDialog(cartItem);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void addCartItemToCart(CartItem cartItem) {

        cartViewModel.addToCart(cartItem, new CartViewModel.AddToCartResultCallback() {
            @Override
            public void onSuccess(boolean isAdded) {
                Log.d("TAG", "tracking is added: from dialog ");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "Error adding to cart" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupWarningDialog(CartItem cartItem) {
        Dialog dialog = new Dialog(requireContext());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.curved_borders);
        dialog.setContentView(R.layout.warn_add_to_cart_dialog);
        TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
        dialogMessage.setText("A new order will clear your cart with " + "\"" + "Caravan" + "\"" + ".");
        Button continueBtn = dialog.findViewById(R.id.continue_btn);
        Button cancelBtn = dialog.findViewById(R.id.cancel_btn);
        dialog.show();
        cancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        continueBtn.setOnClickListener(v -> {
            cartViewModel.emptyCart(new CartViewModel.EmptyCartResultCallback() {
                @Override
                public void onSuccess(boolean isDeleted) {
                    Log.d("TAG", "tracking is deleted: from dialog ");
                    addCartItemToCart(cartItem);
                    dialog.dismiss();
                }

                @Override
                public void onError(Throwable e) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Error deleting cart " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
    }

    private CartItem getCartItem(Product product, int quantity, double price) {
        CartItem cartItem = new CartItem();
        cartItem.setRestaurantId("caravanId");
        cartItem.setProductName(product.getProductName());
        cartItem.setProductImageLink(product.getProductImageLink());
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        cartItem.setRestaurantName("caravan");
        cartItem.setProductId(product.getProductID());
        cartItem.setCategoryName(product.getProductCategory());
        return cartItem;
    }

    @Override
    public void onFoodItemClick(Product product) {
        AddOrderBottomSheet bottomSheetDialogFragment = new AddOrderBottomSheet(this,null);
        bottomSheetDialogFragment.setArguments(sendArgs(product));
        bottomSheetDialogFragment.show(getParentFragmentManager(), bottomSheetDialogFragment.getTag());
        bottomSheetDialogFragment.setCancelable(true);
    }

    private Bundle sendArgs(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurant", getRestaurant());
        bundle.putParcelable("product", product);
        return bundle;
    }

    private Restaurant getRestaurant(){
        return new Restaurant();
    }



    private void addItems() {
        Products products = new Products(list());
        FirebaseFirestore.getInstance()
                .collection(CARAVAN)
                .document(PRODUCTS)
                .set(products).addOnCanceledListener(() -> {
                });
    }



    private List<Product> list() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductName("Classic white lusine toast");
        product1.setProductID("caravan");

        product1.setProductCategory("bakery");
        product1.setProductImageLink("https://s3-alpha-sig.figma.com/img/03aa/9953/e6e84541eabc835f88ccba2e07f482f6?Expires=1731888000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=RAKOEZQ3QsZRXoOPIVJZlSVgvwckYHeNdeD3VrclB2GW7Q5AqzCJgtWeJfU1djUkreHmc-yOiRs49kFimINz0KLHUZcFsgcu-oK5BzkNOevuBuFu6ggIobqo181k3Oan5tJ7qFTheXq9ywbbSMf7oZ8qbqbVaZckxWPLJEpUO1N9SUBGTrhkneomkBBrvTuwTkMO-kf2ErVVZM6mo8g7BAu3qJ41jkCLsorxTF34pdYRoLyJI2Q7tPpXtCfCmUaNtvOFrMQFnWV3Fz14n4TTP8Smm2fkBqnDf7Qa~dkcORpDkKn4iL5o0JkWNR0DSgDxrZ6jI0zCRS~7fMkJOIx5Bg__");
        product1.setProductPrice(5.0);
        product1.setResId("caravan");
        product1.setFoodDesc("caravan caravan");
        product1.setExpirationData(1730176810L);
        product1.setRemovalDate(1730176810L);
        product1.setStatusExpiration("caravan");

        Product product2 = new Product();
        product2.setProductName("samoly bread 6 pieces");
        product2.setProductID("caravan");

        product2.setProductCategory("bakery");
        product2.setProductImageLink("https://s3-alpha-sig.figma.com/img/0fa0/9efa/ae65bdd9308302b105e8ecbcaee03d21?Expires=1731888000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=fVSyZRyULBeNwtsX3ujnPMuGVhrgnk~hOxkaG898CkkdOEV3ZR4t-VdDAHssPsMHQq3rqi6D3-Eu1p1Bx6jHp7UMtGreUZrvyDGwUExu69zPQZ-w6y8YdCl7IZhy0JaFXkIm34BmbZC~dQrJQMM~elWPKjcu83tO9J3jPSNw0qchIVeJUTMeriGOXSdP-rYwz5q~nSQsyfXiCnBZCfH-o3i2ETUMaCHwU3oN5IgjEjLlobP4xKu82~1nMQdMetGI~ULQTem6S0DQokzOR4L9PiFwYHyRmLvm5ZdNDsK4AJuQr8iQkbzWtjCMcMWp7UNAn10Z2oEaWACghqTdChOD7A__");
        product2.setProductPrice(12.0);
        product2.setResId("caravan");
        product2.setFoodDesc("caravan caravan");
        product2.setExpirationData(1730176810L);
        product2.setRemovalDate(1730176810L);
        product2.setStatusExpiration("caravan");


        Product product3 = new Product();
        product3.setProductName("solpadeine soluable");
        product3.setProductID("caravan");

        product3.setProductCategory("pharmacy");
        product3.setProductImageLink("https://s3-alpha-sig.figma.com/img/2869/4bd2/3c487670bf5189d91711e24dbb42bed1?Expires=1731888000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jQaTdEPeU17ea5WMNfmhXO0SogEhfVN8XXyf4eRWSwHPCBo5YCLO7I9xJV82M~YR~v2xEvqMlKVxk94Vc9HMJ~~CTJyqO5d4CXl2ZXh~p~wzQ9DM0TgztexdbreTAZyW5UlwQwUbIYC-kv5XDsbr3l6ATuaySCxi7xVWY4yOQAErLlWyWB2WxZWxt9vAHi4kq-EW1GZRDXKdq7dTYcOxApFU5W1knOyIfvXnH6xs~lyKaHb9JWB4kLrkqsokGOdp37HyyVFUz91tS9rm7CPDNPoa2ScWNNO9EE6GJPHEjgOyscRpXp3W4bEgkqYI~T71CZdBgtKAF1aAzUYT6W3oLA__");
        product3.setProductPrice(12.0);
        product3.setResId("caravan");
        product3.setFoodDesc("caravan caravan");
        product3.setExpirationData(1730176810L);
        product3.setRemovalDate(1730176810L);
        product3.setStatusExpiration("caravan");


        Product product4 = new Product();
        product4.setProductName("panadol extra");
        product4.setProductID("caravan");

        product4.setProductCategory("pharmacy");
        product4.setProductImageLink("https://s3-alpha-sig.figma.com/img/798e/765b/e59b1f89aa68f8f3727b76fd97a02e26?Expires=1731888000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=VImAJulh0CW93vq-Pujvww4MU1F7TDX~MHKygNgYc3oU5v2nqRiFahEg3zFedSS1RMCqa-yBepIIHvLe9uEPX-vtjrkQYAaPkFfq3eBeywrYZkr9cmWWWLqzctaLiLqmLaIwrGifr4eDEGwqVnv-H8BpZ7lhmw3ndwXp94298VvRH44XS9Q8aabzO7p7gFLuNYVG3~Cvje9ns~uDAbhINR-fWx5F-MhszZeRFQuUQ6SiVpOCC9Oc7UneClmPuVqS3Zo6HKOvY4i5EoqD7~~7nVwwj0RUWWtsfd2ui7wbF4dGESmWXISnhwYyyemEEkI9M5JDym2SUBfwOam-E1IEIg__");
        product4.setProductPrice(12.0);
        product4.setResId("caravan");
        product4.setFoodDesc("caravan caravan");
        product4.setExpirationData(1730176810L);
        product4.setRemovalDate(1730176810L);
        product4.setStatusExpiration("caravan");

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        return productList;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (requireActivity() instanceof MainActivity){
            ((MainActivity) requireActivity()).disappearBottomNav();
        }
    }

    @Override
    public void addToSharedCart(Product product,String productId, int quantity) {

    }


}