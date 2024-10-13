package com.example.logisticcavan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.logisticcavan.common.MyResult;
import com.example.logisticcavan.common.utils.CategoriesListLocal;
import com.example.logisticcavan.offers.domain.Offer;
import com.example.logisticcavan.offers.presentation.OffersAdapter;
import com.example.logisticcavan.offers.presentation.OffersViewModel;
import com.example.logisticcavan.products.categories.CategoriesAdapter;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetCategoryProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.ProductsAdapter;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements CategoriesAdapter.OnItemSelected {
    private OffersViewModel offersViewModel;
    private GetProductsViewModel productsViewModel;
    private GetCategoryProductsViewModel categoryProductsViewModel;

    private GetRestaurantViewModel restaurantViewModel;
    RecyclerView categoriesContainer;
    RecyclerView offersContainer;
    RecyclerView productsContainer;
    CategoriesAdapter categoriesAdapter;
    OffersAdapter offersAdapter;
    ProductsAdapter productsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesAdapter = new CategoriesAdapter(CategoriesListLocal.categories,this);
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
        getProducts(view);
        setupProductsContainer(view,productsAdapter);
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
        offersContainer = view.findViewById(R.id.offers_container);
        offersContainer.setHasFixedSize(true);
        offersContainer.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        offersContainer.setAdapter(adapter);
    }
    private void getProducts(View view) {
        productsViewModel.fetchProducts();
        productsViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), result ->
                result.handle(data -> {
                    productsAdapter = new ProductsAdapter(data);
                    setupProductsContainer(view,productsAdapter);
                    for (Product product :data){
                        String restaurantId = product.getResId();
                        restaurantViewModel.fetchRestaurantData(restaurantId);
                    }
                    restaurantViewModel.getRestaurant().observe(getViewLifecycleOwner(), restaurantResult -> {
                        restaurantResult.handle(
                                restaurant->{
                                    Log.d("res", "getProducts: "+restaurant.getRestaurantName() + restaurant.getRestaurantId());
                                    productsAdapter.updateRestaurant(restaurant.getRestaurantId(),restaurant);
                                    Toast.makeText(getContext(),restaurant.getRestaurantId(),Toast.LENGTH_SHORT).show();
                                },
                                error->{
                                    Log.d("res", "getProducts: "+error.getMessage());
                                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                                },
                                ()->{
                                }
                        );
                    });

                }, error -> {
                    Log.d("TAG", "onError: " + error.getMessage());
                }, () -> {
                    Log.d("TAG", "onLoad: Loading ");
                }));
    }

    private void setupProductsContainer(View view,ProductsAdapter adapter){
        productsContainer = view.findViewById(R.id.food_container);
        productsContainer.setHasFixedSize(true);
        productsContainer.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        productsContainer.setAdapter(adapter);
    }

    private void getRestaurant(String resId){
        restaurantViewModel.fetchRestaurantData(resId);
    }

    @Override
    public void scrollToPosition(int position) {
        categoriesContainer.smoothScrollToPosition(position);
    }

    @Override
    public void getCategoryName(String categoryName) {
        if (!categoryName.equals("All")){
            categoryProductsViewModel.fetchCategoryProducts(categoryName);
            categoryProductsViewModel.getCategoryProductsLiveData().observe(getViewLifecycleOwner(),result->{
                result.handle(
                        data->{
                            productsAdapter = new ProductsAdapter(data);
                            setupProductsContainer(this.requireView(),productsAdapter);
                            for (Product product :data)
                            {
                                Log.d("products", "getCategoryName: "+product.getProductName());
                            }
                        },
                        error->{},
                        ()->{}
                );
            });
        }else{
            getProducts(this.requireView());
        }
    }
}