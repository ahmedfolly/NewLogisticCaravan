package com.example.logisticcavan.navigations.commonui;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.logisticcavan.R;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
//        private int id;
//        private String restaurantId;
//        private String productName;
//        private String productImageLink;
//        private double price;
//        private int quantity;
//        private String productId;
//        cartViewModel.addToCart(new CartItem(0, "resId",
//                "Any name",
//                "any link",
//                100.0,
//                10,
//                "any id"),new CartViewModel.AddToCartResultCallback(){
//
//            @Override
//            public void onSuccess(boolean result) {
//                Log.d("TAG", "inserted Success Room: "+result);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("TAG", "inserted erroe Room: "+e.getMessage());
//            }
//        });

//        cartViewModel.getRestaurantIdOfFirstItem("resId", new CartViewModel.GetResaurantIdCallback() {
//            @Override
//            public void onSuccess(boolean isExist) {
//                Log.d("TAG", "onSuccess Room: "+isExist);
//            }
//            @Override
//            public void onError(Throwable e) {
//                Log.d("TAG", "onError Room: "+e.getMessage());
//            }
//        });
//        cartViewModel.getCartCount(new CartViewModel.CartCountCallback() {
//            @Override
//            public void onSuccess(int count) {
//                Log.d("TAG", "onSuccess: from main"+count);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("TAG", "onError: from main"+e.getMessage());
//
//            }
//        });
//        cartViewModel.getRestaurantIdOfFirstItem("7gRzrEYIpARz3Wbep38l", new CartViewModel.GetRestaurantIdCallback() {
//            @Override
//            public void onSuccess(boolean isExist) {
//                Log.d("TAG", "main id exist "+isExist);
//            }
//            @Override
//            public void onError(Throwable e) {
//
//            }
//        });
        CartItem cartItem = new CartItem(0, "resId3",
                "Any name",
                "any link",
                100.0,
                10,
                "any id");

//        cartViewModel.emptyCart(new CartViewModel.EmptyCartResultCallback() {
//            @Override
//            public void onSuccess(boolean isDeleted) {
//                Log.d("TAG", " main deleted data: ");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//        });

        cartViewModel.getRestaurantIdOfFirstItem("resId3", new CartViewModel.GetRestaurantIdCallback() {
            @Override
            public void onSuccess(boolean isExist) {
                Log.d("TAG", "resId  "+isExist);
                if (isExist){
                    addItemToCart(cartItem);
                }else{
                    cartViewModel.getCartCount(new CartViewModel.CartCountCallback() {
                        @Override
                        public void onSuccess(int count) {
                            Log.d("TAG", "main get cart count " + count);
                            if (count == 0){
                                Log.d("TAG", "main  add item to empty database ");
                                addItemToCart(cartItem);
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
    void addItemToCart(CartItem cartItem){
        cartViewModel.addToCart(cartItem, new CartViewModel.AddToCartResultCallback() {
            @Override
            public void onSuccess(boolean isAdded) {
                Log.d("TAG", "main added item: ");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
     void setupWarningDialog(CartItem cartItem) {
        Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.curved_borders);
        dialog.setContentView(R.layout.warn_add_to_cart_dialog);
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
                    addItemToCart(cartItem);
                    dialog.dismiss();
                }

                @Override
                public void onError(Throwable e) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error deleting cart " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
    }


}