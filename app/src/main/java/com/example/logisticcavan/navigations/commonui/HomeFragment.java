package com.example.logisticcavan.navigations.commonui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.logisticcavan.R;
import com.example.logisticcavan.restaurants.domain.ProductWithRestaurant;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.example.logisticcavan.restaurants.presentation.CombinedProductsWithRestaurantsViewModel;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.common.utils.CategoriesListLocal;
import com.example.logisticcavan.offers.presentation.customer.OffersAdapter;
import com.example.logisticcavan.offers.presentation.OffersViewModel;
import com.example.logisticcavan.products.categories.CategoriesAdapter;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetCategoryProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.ProductsAdapter;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantViewModel;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantsViewModel;
import com.example.logisticcavan.restaurants.presentation.RestaurantsAdapter;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment implements CategoriesAdapter.OnItemSelected {
    private OffersViewModel offersViewModel;
    private GetProductsViewModel productsViewModel;
    private GetCategoryProductsViewModel categoryProductsViewModel;
    private GetRestaurantViewModel restaurantViewModel;
    private RecyclerView categoriesContainer, offersContainer;
    private RecyclerView productsContainer;
    private CategoriesAdapter categoriesAdapter;
    private OffersAdapter offersAdapter;
    private ProductsAdapter productsAdapter;
    private RestaurantsAdapter restaurantsAdapter;
    private NavController navController;
    private ProgressBar foodProgressBar, offerLoaderProgress;
    private CombinedProductsWithRestaurantsViewModel combinedProductsWithRestaurantsViewModel;
    private GetRestaurantsViewModel getRestaurantsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesAdapter = new CategoriesAdapter(CategoriesListLocal.categories, this);
        productsAdapter = new ProductsAdapter();
        restaurantsAdapter = new RestaurantsAdapter();
        offersViewModel = new ViewModelProvider(this).get(OffersViewModel.class);
        productsViewModel = new ViewModelProvider(this).get(GetProductsViewModel.class);
        restaurantViewModel = new ViewModelProvider(this).get(GetRestaurantViewModel.class);
        categoryProductsViewModel = new ViewModelProvider(this).get(GetCategoryProductsViewModel.class);
        getRestaurantsViewModel = new ViewModelProvider(this).get(GetRestaurantsViewModel.class);
        combinedProductsWithRestaurantsViewModel = new ViewModelProvider(this).get(CombinedProductsWithRestaurantsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productsContainer = view.findViewById(R.id.food_container);
        offersContainer = view.findViewById(R.id.offers_container);
        foodProgressBar = view.findViewById(R.id.food_loader_progress_bar);
        offerLoaderProgress = view.findViewById(R.id.offers_loader_progress_bar);
        ImageView filterList = view.findViewById(R.id.filter_list_id);
        filterList.setOnClickListener(v -> {
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_homeFragment_to_cartFragment);
        });
        getCategories(view);
        getOffers(view);
//        getProducts();
        setupProductsContainer(view,restaurantsAdapter);
        getRestaurants();
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
            offerLoaderProgress.setVisibility(View.GONE);
        }, error -> {
            Toast.makeText(getContext(), error.getMessage() + "occurred while loading offers", Toast.LENGTH_SHORT).show();
        }, () -> {
            offerLoaderProgress.setVisibility(View.VISIBLE);
        }));
        offersViewModel.getAllOffers();
    }

    private void setupOffersContainer(View view, OffersAdapter adapter) {
        offersContainer = view.findViewById(R.id.offers_container);
        offersContainer.setHasFixedSize(true);
        offersContainer.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        offersContainer.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
//    private void getProducts() {
//        productsViewModel.fetchProducts();
//        productsViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), result -> result.handle(productsResult -> {
//            List<String> restaurantIds = restaurantIds(productsResult);
//            restaurantViewModel.fetchRestaurantsIds(restaurantIds);
//            restaurantViewModel.getRestaurant().observe(getViewLifecycleOwner(), restaurantResult -> restaurantResult.handle(restaurants -> {
//                List<ProductWithRestaurant> productWithRestaurants = productsResult.stream().map(product -> {
//                    Restaurant restaurant = restaurants.stream().filter(r -> r.getRestaurantId().equals(product.getResId())).findFirst().orElse(null);
//                    return new ProductWithRestaurant(product, restaurant);
//                }).collect(Collectors.toList());
//                productsAdapter.submitList(productWithRestaurants);
//                foodProgressBar.setVisibility(View.GONE);
//            }, errorOnLoadingRestaurants -> {
//            }, () -> {
//            }));
//        }, error -> {
//        }, () -> foodProgressBar.setVisibility(View.VISIBLE)));
//
//
////        combinedProductsWithRestaurantsViewModel.getCombinedLiveData().removeObservers(getViewLifecycleOwner());
////        combinedProductsWithRestaurantsViewModel.combineSources(productsViewModel.getProductsLiveData(), restaurantViewModel.getRestaurant());
////        combinedProductsWithRestaurantsViewModel.getCombinedLiveData().observe(getViewLifecycleOwner(), result -> result.handle(productWithRestaurants -> {
////            productsAdapter.submitList(productWithRestaurants);
////            setupProductsContainer(this.requireView(), productsAdapter);
////            foodProgressBar.setVisibility(View.GONE);
////        }, error -> {
////        }, () -> foodProgressBar.setVisibility(View.VISIBLE)));
//    }

    private void getRestaurants() {
        getRestaurantsViewModel.fetchRestaurants();
        getRestaurantsViewModel.getRestaurants().observe(getViewLifecycleOwner(), myResult -> {
            myResult.handle(restaurants -> {
                        restaurantsAdapter.submitList(restaurants);
                        setupProductsContainer(this.requireView(), restaurantsAdapter);
                    }, error -> {
                    },
                    () -> {});
                    });
        }

        private void setupProductsContainer (View view, RestaurantsAdapter restaurantsAdapter){
            productsContainer = view.findViewById(R.id.food_container);
            productsContainer.setHasFixedSize(true);
            productsContainer.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false));
            productsContainer.setAdapter(restaurantsAdapter);
        }

        @Override
        public void scrollToPosition ( int position){
            categoriesContainer.smoothScrollToPosition(position);
        }

        @Override
        public void getCategoryName (String categoryName){
            if (!categoryName.equals("All")) {

                categoryProductsViewModel.fetchCategoryProducts(categoryName);
                categoryProductsViewModel.getCategoryProductsLiveData().observe(getViewLifecycleOwner(), result -> {
                    result.handle(productsResult -> {
                        List<String> restaurantIds = restaurantIds(productsResult);
                        restaurantViewModel.fetchRestaurantsIds(restaurantIds);
//                    List<String> restaurantIds = restaurantIds(productsResult);
//                    List<String> restaurantsIdsSet = new ArrayList<>(new HashSet<>(restaurantIds));
//                    restaurantViewModel.fetchRestaurantsIds(restaurantsIdsSet);
//                    for (String id : restaurantsIdsSet) {
//                        Log.d("TAG", "getCategoryName: " + id);
//                    }
//                    restaurantViewModel.getRestaurant().observe(getViewLifecycleOwner(), restaurantResult -> restaurantResult.handle(restaurants -> {
//                        List<ProductWithRestaurant> productWithRestaurants = productsResult.stream().map(product -> {
//                            Restaurant restaurant = restaurants.stream().filter(r -> r.getRestaurantId().equals(product.getResId())).findFirst().orElse(null);
//                            return new ProductWithRestaurant(product, restaurant);
//                        }).collect(Collectors.toList());
//                        productsAdapter.submitList(productWithRestaurants);
//                        setupProductsContainer(this.requireView(), productsAdapter);
//                    }, errorOnLoadingRestaurants -> {
//                    }, () -> {
//                    }));
                    }, error -> {
                    }, () -> {
                    });
                    combinedProductsWithRestaurantsViewModel.combineSources(categoryProductsViewModel.getCategoryProductsLiveData(), restaurantViewModel.getRestaurant());
                    combinedProductsWithRestaurantsViewModel.getCombinedLiveData().observe(getViewLifecycleOwner(), productWithRestaurantsResult -> productWithRestaurantsResult.handle(productWithRestaurants -> {
                        productsAdapter.submitList(productWithRestaurants);
//                        setupProductsContainer(this.requireView(), productsAdapter);
                    }, error -> {
                    }, () -> {
                    }));
                });
            } else {
//                getProducts();
            }
        }

        List<String> restaurantIds (List < Product > products) {
            return products.stream().map(Product::getResId).collect(Collectors.toList());
        }
    }
