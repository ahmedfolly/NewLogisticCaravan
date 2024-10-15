package com.example.logisticcavan.users.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthActivity;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.common.utils.CategoriesListLocal;
import com.example.logisticcavan.offers.presentation.OffersAdapter;
import com.example.logisticcavan.offers.presentation.OffersViewModel;
import com.example.logisticcavan.products.categories.CategoriesAdapter;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetCategoryProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.ProductsAdapter;
import com.example.logisticcavan.restaurants.domain.ProductWithRestaurant;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment implements CategoriesAdapter.OnItemSelected {
    private OffersViewModel offersViewModel;
    private GetProductsViewModel productsViewModel;
    private GetCategoryProductsViewModel categoryProductsViewModel;
    private GetRestaurantViewModel restaurantViewModel;
    private RecyclerView categoriesContainer;
    private CategoriesAdapter categoriesAdapter;
    private OffersAdapter offersAdapter;
    private ProductsAdapter productsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesAdapter = new CategoriesAdapter(CategoriesListLocal.categories, this);
        offersViewModel = new ViewModelProvider(this).get(OffersViewModel.class);
        productsViewModel = new ViewModelProvider(this).get(GetProductsViewModel.class);
        restaurantViewModel = new ViewModelProvider(this).get(GetRestaurantViewModel.class);
        categoryProductsViewModel = new ViewModelProvider(this).get(GetCategoryProductsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment






        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCategories(view);
        getOffers(view);
        getProducts();
        setupProductsContainer(view, productsAdapter);

        view.findViewById(R.id.notification_id).setOnClickListener(view1 -> {

            signOut();

        });
    }



    private void getCategories(View view) {
        categoriesContainer = view.findViewById(R.id.categories_container_id);
        categoriesContainer.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false);
        categoriesContainer.setLayoutManager(llm);
        categoriesContainer.setAdapter(categoriesAdapter);
    }

    private void getOffers(View view) {
        offersViewModel.getOffersLiveData().observe(getViewLifecycleOwner(), result -> result.handle(data -> {
            offersAdapter = new OffersAdapter(data);
            setupOffersContainer(view, offersAdapter);
        }, error -> {
            Toast.makeText(getContext(), error.getMessage() + "occurred while loading offers", Toast.LENGTH_SHORT).show();
        }, () -> {
        }));
        offersViewModel.getAllOffers();
    }

    private void setupOffersContainer(View view, OffersAdapter adapter) {
        RecyclerView offersContainer = view.findViewById(R.id.offers_container);
        offersContainer.setHasFixedSize(true);
        offersContainer.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        offersContainer.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void getProducts() {
        productsViewModel.fetchProducts();
        productsViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), result -> result.handle(productsResult -> {
            getProductsWithRestaurant(result);
        }, errorOnLoadingProducts -> {
        }, () -> {
        }));
    }

    private void setupProductsContainer(View view, ProductsAdapter adapter) {
        RecyclerView productsContainer = view.findViewById(R.id.food_container);
        productsContainer.setHasFixedSize(true);
        productsContainer.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        productsContainer.setAdapter(adapter);
    }

    @Override
    public void scrollToPosition(int position) {
        categoriesContainer.smoothScrollToPosition(position);
    }

    @Override
    public void getCategoryName(String categoryName) {
        if (!categoryName.equals("All")) {
            categoryProductsViewModel.fetchCategoryProducts(categoryName);
            categoryProductsViewModel.getCategoryProductsLiveData().observe(getViewLifecycleOwner(), this::getProductsWithRestaurant);
        } else {
            getProducts();
        }
    }

    void getProductsWithRestaurant(MyResult<List<Product>> result) {
        result.handle(productsResult -> {
            List<String> restaurantsIds = restaurantIds(productsResult);
            restaurantViewModel.fetchRestaurantsIds(restaurantsIds);
            restaurantViewModel.getRestaurant().observe(getViewLifecycleOwner(), restaurantResult -> restaurantResult.handle(restaurants -> {
                List<ProductWithRestaurant> productWithRestaurants = productsResult.stream().map(product -> {
                    Restaurant restaurant = restaurants.stream().filter(r -> r.getRestaurantId().equals(product.getResId())).findFirst().orElse(null);
                    return new ProductWithRestaurant(product, restaurant);
                }).collect(Collectors.toList());
                productsAdapter = new ProductsAdapter(productWithRestaurants);
                setupProductsContainer(this.requireView(), productsAdapter);
            }, errorOnLoadingRestaurants -> {
            }, () -> {
            }));
        }, error -> {
        }, () -> {
        });
    }

    List<String> restaurantIds(List<Product> products) {
        return products.stream().map(Product::getResId).collect(Collectors.toList());
    }
}
