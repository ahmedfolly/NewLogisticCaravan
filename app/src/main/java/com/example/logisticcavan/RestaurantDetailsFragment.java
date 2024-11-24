package com.example.logisticcavan;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.ui.AddOrderBottomSheet;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.products.getproducts.presentation.RestaurantProductsAdapter;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantProductsViewModel;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.presentation.AddToSharedCartViewModel;
import com.example.logisticcavan.sharedcart.presentation.GetSharedCartViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RestaurantDetailsFragment extends Fragment
        implements RestaurantProductsAdapter.FoodItemClickListener,
        AddOrderBottomSheet.AddToCartCallback,
AddOrderBottomSheet.AddToSharedCartCallback{

    private RestaurantDetailsFragmentArgs args;
    GetProductsViewModel getProductsViewModel;
    private GetRestaurantProductsViewModel getRestaurantProductsViewModel;
    private RestaurantProductsAdapter restaurantProductsAdapter;
    private RecyclerView productsContainer;
    private CartViewModel cartViewModel;
    private ProgressBar loadRestaurantProductsProgress;

    private NavController navController;
    private AddToSharedCartViewModel addToSharedCartViewModel;
    private AuthViewModel authViewModel;
    private GetSharedCartViewModel getSharedCartViewModel;

    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRestaurantProductsViewModel = new ViewModelProvider(this).get(GetRestaurantProductsViewModel.class);
        restaurantProductsAdapter = new RestaurantProductsAdapter(getParentFragmentManager(), this);
        getProductsViewModel = new ViewModelProvider(this).get(GetProductsViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        addToSharedCartViewModel = new ViewModelProvider(this).get(AddToSharedCartViewModel.class);
        getSharedCartViewModel = new ViewModelProvider(this).get(GetSharedCartViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = RestaurantDetailsFragmentArgs.fromBundle(getArguments());
        Restaurant restaurant = args.getRestaurant();
        productsContainer = view.findViewById(R.id.restaurant_food_container);
        ImageView restaurantImage = view.findViewById(R.id.restaurant_image_details);
        loadRestaurantProductsProgress = view.findViewById(R.id.load_restaurant_products_progress);
        //restaurant image
        Glide.with(this)
                .load(restaurant.getRestaurantImageLink())
                .into(restaurantImage);
        //restaurant logo
        ImageView restaurantLogo = view.findViewById(R.id.restaurant_logo);
        Glide.with(this)
                .load(restaurant.getRestaurantLogoLink())
                .circleCrop()
                .into(restaurantLogo);
        //restaurant name
        TextView restaurantName = view.findViewById(R.id.restaurant_name_detail);
        restaurantName.setText(restaurant.getRestaurantName());
        //restaurant available time
        TextView restaurantAvailableTime = view.findViewById(R.id.restaurant_available_time_detail);
        restaurantAvailableTime.setText(restaurant.getAvailableTime());
        getRestaurantProducts(restaurant.getRestaurantId());

        TextView ratingText = view.findViewById(R.id.restaurant_rating_text_detail);
        navController = findNavController(this);
        ratingText.setOnClickListener(v->{
            NavDirections action = RestaurantDetailsFragmentDirections.actionRestaurantDetailsFragmentToRatingsFragment(restaurant);
            navController.navigate(action);
        });
    }
    void getRestaurantProducts(String restaurantId) {
        getRestaurantProductsViewModel.getRestaurantProducts(restaurantId);
        getRestaurantProductsViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), productsIdsResult -> {
            productsIdsResult.handle(
                    productsIds -> {
                        for (String id : productsIds) {
                            Log.d("TAG", "getRestaurantProducts: " + id);
                        }
                        getProductsFromIds(productsIds);
                    },
                    error -> {
                    },
                    () -> {
                    }
            );
        });
    }
    void setupProductsContainer() {
        productsContainer.setHasFixedSize(true);
        productsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        productsContainer.setAdapter(restaurantProductsAdapter);
    }
    void getProductsFromIds(List<String> productsIds) {
        getProductsViewModel.fetchProducts(productsIds);
        getProductsViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), productsResult -> {
            productsResult.handle(
                    products -> {
                        //here submit this list with adapter
                        restaurantProductsAdapter.submitList(products);
                        setupProductsContainer();
                        loadRestaurantProductsProgress.setVisibility(View.GONE);
                    }, error -> loadRestaurantProductsProgress.setVisibility(View.VISIBLE),
                    () -> loadRestaurantProductsProgress.setVisibility(View.VISIBLE)
            );
        });
    }
    private Bundle sendArgs(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurant", args.getRestaurant());
        bundle.putParcelable("product", product);
        return bundle;
    }
    @Override
    public void onFoodItemClick(Product product) {
        AddOrderBottomSheet bottomSheetDialogFragment = new AddOrderBottomSheet(this,this);
        bottomSheetDialogFragment.setArguments(sendArgs(product));
        bottomSheetDialogFragment.show(getParentFragmentManager(), bottomSheetDialogFragment.getTag());
        bottomSheetDialogFragment.setCancelable(true);
    }
    @Override
    public void addToCart(Product product, int quantity, double price) {
        CartItem cartItem = getCartItem(product, quantity, price);
        //adding to cart operation.
        cartViewModel.getRestaurantIdOfFirstItem(args.getRestaurant().getRestaurantId(), new CartViewModel.GetRestaurantIdCallback() {
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
        String restaurantName = args.getRestaurant().getRestaurantName();
        Dialog dialog = new Dialog(requireContext());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.curved_borders);
        dialog.setContentView(R.layout.warn_add_to_cart_dialog);
        TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
        dialogMessage.setText("A new order will clear your cart with " + "\"" + restaurantName + "\"" + ".");
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
        cartItem.setRestaurantId(args.getRestaurant().getRestaurantId());
        cartItem.setProductName(product.getProductName());
        cartItem.setProductImageLink(product.getProductImageLink());
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        cartItem.setRestaurantName(args.getRestaurant().getRestaurantName());
        cartItem.setProductId(product.getProductID());
        cartItem.setCategoryName(product.getProductCategory());
        return cartItem;
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
                            args.getRestaurant().getRestaurantId(),
                            args.getRestaurant().getRestaurantName(),
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
                    if (restaurantId.equals(args.getRestaurant().getRestaurantId())){
                        addToSharedCartViewModel.addToSharedCart(sharedProduct,
                                args.getRestaurant().getRestaurantId(),
                                args.getRestaurant().getRestaurantName(),
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
}