package com.example.logisticcavan.products.getproducts.presentation.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.ui.AddOrderBottomSheet;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodDetailFragment extends Fragment implements AddOrderBottomSheet.AddToCartCallback {
    FoodDetailFragmentArgs args;
    private CartViewModel cartViewModel;
    public FoodDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        args = FoodDetailFragmentArgs.fromBundle(getArguments());
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(view)
                .load(args.getProductWithRestaurant().getProduct().getProductImageLink())
                .override(800, 800)
                .into((ImageView) view.findViewById(R.id.food_image_detail_id));

        TextView foodName = view.findViewById(R.id.food_name_detail_id);
        foodName.setText(args.getProductWithRestaurant().getProduct().getProductName());
        TextView restaurantName = view.findViewById(R.id.restaurant_name_detail_id);
        restaurantName.setText(args.getProductWithRestaurant().getRestaurant().getRestaurantName());
        TextView foodDesc = view.findViewById(R.id.food_description_detail_id);
        Log.d("TAG", "onViewCreated: " + args.getProductWithRestaurant().getProduct().getFoodDesc());
        foodDesc.setText(args.getProductWithRestaurant().getProduct().getFoodDesc());
        MaterialButton visitRestaurantBtn = view.findViewById(R.id.visit_restaurant_btn_id);
        visitRestaurantBtn.setText("Visit " + args.getProductWithRestaurant().getRestaurant().getRestaurantName());
        MaterialButton orderFoodBtn = view.findViewById(R.id.order_food_btn_id);
        orderFoodBtn.setOnClickListener(v -> {
//            AddOrderBottomSheet bottomSheetDialogFragment = new AddOrderBottomSheet(this,);
//            bottomSheetDialogFragment.setArguments(sendArgs());
//            bottomSheetDialogFragment.show(getParentFragmentManager(), bottomSheetDialogFragment.getTag());
//            bottomSheetDialogFragment.setCancelable(true);
        });

    }

    private Bundle sendArgs() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("productWithRestaurant", args.getProductWithRestaurant());
        return bundle;
    }

    @Override
    public void addToCart(Product product, int quantity, double price) {
        CartItem cartItem = getCartItem(quantity, price);
        //adding to cart operation.
        cartViewModel.getRestaurantIdOfFirstItem(args.getProductWithRestaurant().getRestaurant().getRestaurantId(), new CartViewModel.GetRestaurantIdCallback() {
            @Override
            public void onSuccess(boolean isExist) {
                Log.d("TAG", "resId  "+isExist);
                if (isExist){
                    Log.d("TAG", "item found ");
                    addCartItemToCart(cartItem);
                }else{
                    cartViewModel.getCartCount(new CartViewModel.CartCountCallback() {
                        @Override
                        public void onSuccess(int count) {
                            Log.d("TAG", "main get cart count " + count);
                            if (count == 0){
                                Log.d("TAG", "main  add item to empty database ");
                                addCartItemToCart(cartItem);
                            }else{
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

    @NonNull
    private CartItem getCartItem(int quantity, double price) {
        CartItem cartItem = new CartItem();
        cartItem.setRestaurantId(args.getProductWithRestaurant().getRestaurant().getRestaurantId());
        cartItem.setProductName(args.getProductWithRestaurant().getProduct().getProductName());
        cartItem.setProductImageLink(args.getProductWithRestaurant().getProduct().getProductImageLink());
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        cartItem.setRestaurantName(args.getProductWithRestaurant().getRestaurant().getRestaurantName());
        cartItem.setProductId(args.getProductWithRestaurant().getProduct().getProductID());
        return cartItem;
    }

    private void setupWarningDialog(CartItem cartItem) {
        String restaurantName = args.getProductWithRestaurant().getRestaurant().getRestaurantName();
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
    private void addCartItemToCart(CartItem cartItem) {

            cartViewModel.addToCart(cartItem, new CartViewModel.AddToCartResultCallback() {
                @Override
                public void onSuccess(boolean isAdded) {
                    Log.d("TAG", "tracking is added: from dialog ");
                }
                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getContext(), "Error adding to cart", Toast.LENGTH_SHORT).show();
                }
            });
    }
}