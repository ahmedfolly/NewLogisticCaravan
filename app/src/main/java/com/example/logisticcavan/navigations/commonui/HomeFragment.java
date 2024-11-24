package com.example.logisticcavan.navigations.commonui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logisticcavan.R;
import com.example.logisticcavan.SharedViewModel;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.ui.AddOrderBottomSheet;
import com.example.logisticcavan.products.recommendations.presentation.RecommendationAdapter;
import com.example.logisticcavan.products.recommendations.presentation.RecommendationViewModel;
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
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.presentation.AddToSharedCartViewModel;
import com.example.logisticcavan.sharedcart.presentation.GetSharedCartViewModel;
import com.example.logisticcavan.sharedcart.presentation.GetSharedProductsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment implements CategoriesAdapter.OnItemSelected,
        RecommendationAdapter.RecommendedProductListener,
        AddOrderBottomSheet.AddToCartCallback,
        AddOrderBottomSheet.AddToSharedCartCallback{
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
    private FrameLayout parent;
    ImageView openCartScreen;
    private CardView caravanItems;
    private CartViewModel cartViewModel;

    private SharedViewModel sharedViewModel;

    private HomeFragmentOpenedCallback homeFramentOpenedCallback;

    private boolean isRatingSubmitted = false;
    private RecommendationAdapter recommendationAdapter;
    private RecommendationViewModel recommendationViewModel;
    private RecyclerView recommendedContainer;
    private GetSharedProductsViewModel getSharedProductsViewModel;
    private AuthViewModel authViewModel;

    private GetSharedCartViewModel getSharedCartViewModel;
    private AddToSharedCartViewModel addToSharedCartViewModel;
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
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        combinedProductsWithRestaurantsViewModel = new ViewModelProvider(this).get(CombinedProductsWithRestaurantsViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        recommendationViewModel = new ViewModelProvider(this).get(RecommendationViewModel.class);
        getSharedProductsViewModel = new ViewModelProvider(this).get(GetSharedProductsViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        getSharedCartViewModel = new ViewModelProvider(this).get(GetSharedCartViewModel.class);
        addToSharedCartViewModel = new ViewModelProvider(this).get(AddToSharedCartViewModel.class);
        recommendationAdapter = new RecommendationAdapter(this);
//        PlaceOrderFragment.setOrderPlaceCallback(this);
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
        recommendedContainer = view.findViewById(R.id.recommended_container);
        productsContainer = view.findViewById(R.id.food_container);
        offersContainer = view.findViewById(R.id.offers_container);
        foodProgressBar = view.findViewById(R.id.food_loader_progress_bar);
        caravanItems = view.findViewById(R.id.caravan);
        offerLoaderProgress = view.findViewById(R.id.offers_loader_progress_bar);
        openCartScreen = view.findViewById(R.id.cart_icon);
        openCartScreen.setOnClickListener(v -> {
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_homeFragment_to_cartFragment);
        });

        caravanItems.setOnClickListener(v -> {
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_homeFragment_to_caravanFragment);
        });

        getCategories(view);
        getOffers(view);
//        getProducts();
        setupProductsContainer(view, restaurantsAdapter);
        getRestaurants();
        checkForCartItemsCount();

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        if (offersContainer.getOnFlingListener() == null) {
            linearSnapHelper.attachToRecyclerView(offersContainer);
        }

        if (!isRatingSubmitted) {
            sharedViewModel.isRatingSubmitted().observe(getViewLifecycleOwner(), isSummitted -> {
                if (isSummitted) {
                    showTopSnackbar();
                    isRatingSubmitted = true;
                }
            });
        }
        getRecommendedProducts();
        openSharedCartScreen();
        isSharedCartEmpty();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
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

    private void checkForCartItemsCount() {
        cartViewModel.getCartItems();
        cartViewModel.getCartItemsData().observe(getViewLifecycleOwner(), cartItemsResult -> {
            cartItemsResult.handle(cartItems -> {
                        TextView cartCount = requireView().findViewById(R.id.cart_count);
                        if (cartItems.isEmpty()) {
                            openCartScreen.setVisibility(View.GONE);
                            cartCount.setVisibility(View.GONE);
                        } else {
                            openCartScreen.setVisibility(View.VISIBLE);
                            cartCount.setVisibility(View.VISIBLE);
                            cartCount.setText(String.valueOf(cartItems.size()));
                        }
                    },
                    error -> {
                    },
                    () -> {
                    });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.showBottomNav();
        }
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
                    () -> {
                    });
        });
    }

    private void setupProductsContainer(View view, RestaurantsAdapter restaurantsAdapter) {
        productsContainer = view.findViewById(R.id.food_container);
        productsContainer.setHasFixedSize(true);
        productsContainer.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        productsContainer.setAdapter(restaurantsAdapter);
    }

    @Override
    public void scrollToPosition(int position) {
        categoriesContainer.smoothScrollToPosition(position);
    }

    @Override
    public void getCategoryName(String categoryName) {
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

    List<String> restaurantIds(List<Product> products) {
        return products.stream().map(Product::getResId).collect(Collectors.toList());
    }

    public void showTopSnackbar() {
        CoordinatorLayout coordinatorLayout = requireActivity().findViewById(R.id.parent_container);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_LONG);
        View customSnackView = getLayoutInflater().inflate(R.layout.feedback_submitted_snackbar_layout, null);
        View snackbarView = snackbar.getView();

        //setup snackbar view
        setupSnackbarSiew(snackbar, customSnackView, snackbarView);
        //setup snackbar settings
        setupSnackbarSettings(snackbar, snackbarView);
    }

    private void setupSnackbarSiew(Snackbar snackbar, View customSnackView, View snackbarView) {
        @SuppressLint("RestrictedApi")
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(customSnackView, 0);
    }

    private void getRecommendedProducts() {
        ProgressBar progressBar = requireView().findViewById(R.id.on_recommendations_loading);
        recommendationViewModel.getProducts().observe(getViewLifecycleOwner(), result -> {
            result.handle(products -> {
                if (products.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                }
                recommendationAdapter.submitList(products);
                setupRecommendedContainer(products);
                progressBar.setVisibility(View.GONE);
            }, error -> {
                progressBar.setVisibility(View.GONE);
            }, () -> {
                progressBar.setVisibility(View.VISIBLE);
            });
        });

    }

    private void setupRecommendedContainer(List<Product> productList) {
        if (!productList.isEmpty()) {
            TextView recommendedText = requireView().findViewById(R.id.recommened_text);
            recommendedText.setVisibility(View.VISIBLE);
            recommendedContainer.setVisibility(View.VISIBLE);
            recommendedContainer.setHasFixedSize(true);
            recommendedContainer.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
            recommendedContainer.setAdapter(recommendationAdapter);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setupSnackbarSettings(Snackbar snackbar, View snackbarView) {
        snackbar.setBackgroundTint(getResources().getColor(R.color.primary_color, null));
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
        snackbarView.setBackgroundResource(R.drawable.feedback_submitted_snackbar_bg);
        params.gravity = Gravity.TOP;
        params.topMargin = 64;
        snackbarView.setElevation(8);
        snackbarView.setLayoutParams(params);
        snackbar.show();
    }

    private void openSharedCartScreen() {
        ImageView openSharedCart = requireView().findViewById(R.id.open_shared_cart);
        openSharedCart.setOnClickListener(v -> {
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_homeFragment_to_sharedCartFragment);
        });
    }

    private void isSharedCartEmpty() {
        ImageView openSharedCart = requireView().findViewById(R.id.open_shared_cart);
        getSharedProductsViewModel.fetchSharedProducts();
        getSharedProductsViewModel.getSharedProductsLiveData().observe(getViewLifecycleOwner(), result -> {
            result.handle(products -> {
                        if (!products.isEmpty()) {
                            openSharedCart.setVisibility(View.VISIBLE);
                        }else{
                            openSharedCart.setVisibility(View.GONE);
                        }
                    },
                    error -> {
                    },
                    () -> {
                    });
        });
    }

    @Override
    public void onProductClicked(Product product) {
        AddOrderBottomSheet bottomSheetDialogFragment = new AddOrderBottomSheet(this,this);
        bottomSheetDialogFragment.setArguments(sendArgs(product));
        bottomSheetDialogFragment.show(getParentFragmentManager(), bottomSheetDialogFragment.getTag());
        bottomSheetDialogFragment.setCancelable(true);
    }
    private Bundle sendArgs(Product product) {
        Bundle bundle = new Bundle();
//        bundle.putParcelable("restaurant", args.getRestaurant());
        bundle.putParcelable("product", product);
        return bundle;
    }

    @Override
    public void addToCart(Product product, int quantity, double price) {
        CartItem cartItem = getCartItem(product, quantity, price);
        //adding to cart operation.
        cartViewModel.getRestaurantIdOfFirstItem(product.getResId(), new CartViewModel.GetRestaurantIdCallback() {
            @Override
            public void onSuccess(boolean isExist) {
                Log.d("TAG", "resId  " + isExist);
                if (isExist) {
                    Log.d("TAG", "item found ");
                    addCartItemToCart(cartItem);
                } else {
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
    @SuppressLint("SetTextI18n")
    private void setupWarningDialog(CartItem cartItem) {
        Dialog dialog = new Dialog(requireContext());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.curved_borders);
        dialog.setContentView(R.layout.warn_add_to_cart_dialog);
        TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
        dialogMessage.setText("A new order will clear your cart");
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
        cartItem.setRestaurantId(product.getResId());
        cartItem.setProductName(product.getProductName());
        cartItem.setProductImageLink(product.getProductImageLink());
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        cartItem.setRestaurantName(product.getProductOwner());
        cartItem.setProductId(product.getProductID());
        cartItem.setCategoryName(product.getProductCategory());
        return cartItem;
    }

    @Override
    public void addToSharedCart(Product product,String productId, int quantity) {
        SharedProduct sharedProduct = new SharedProduct();
        sharedProduct.setAddedBy(getUserName());
        sharedProduct.setProductId(productId);
        sharedProduct.setQuantity(quantity);
        getSharedCartViewModel.getSharedCart(new GetSharedCartViewModel.SharedCartCallback() {
            @Override
            public void onSuccess(SharedCart sharedCart) {
                if (sharedCart.getRestaurantId() == null){
                    Log.d("TAG", "onSuccess: "+sharedCart);
                    addToSharedCartViewModel.addToSharedCart(sharedProduct,
                            product.getResId(),
                            product.getProductOwner(),
                            new AddToSharedCartViewModel.AddToSharedCartCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(requireContext(),result,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    Log.d("TAG", "there is an error in adding product to shared cart ");
                                }
                            });
                }if (sharedCart.getRestaurantId()!=null){
                    String restaurantId = sharedCart.getRestaurantId();
                    if (restaurantId.equals(product.getResId())){
                        addToSharedCartViewModel.addToSharedCart(sharedProduct,
                                product.getResId(),
                                product.getProductOwner(),
                                new AddToSharedCartViewModel.AddToSharedCartCallback() {
                                    @Override
                                    public void onSuccess(String result) {
                                        Toast.makeText(requireContext(),result,Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.d("TAG", "there is an error in adding product to shared cart ");
                                    }
                                });
                    }else{
                        Toast.makeText(requireContext(),"you try to add product from another restaurant",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }

    private String getUserName(){
        return authViewModel.getUserInfoLocally().getName();
    }
    public interface HomeFragmentOpenedCallback {
        void onHomeFragmentOpened();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        offersContainer.setAdapter(null); // Clear adapter to prevent memory leaks
    }
}
