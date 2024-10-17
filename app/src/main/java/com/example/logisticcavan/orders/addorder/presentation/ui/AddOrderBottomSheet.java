package com.example.logisticcavan.orders.addorder.presentation.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.logisticcavan.R;
import com.example.logisticcavan.restaurants.domain.ProductWithRestaurant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class AddOrderBottomSheet extends BottomSheetDialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_food_bottomsheet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        ProductWithRestaurant productWithRestaurant = args.getParcelable("productWithRestaurant");
        assert productWithRestaurant != null;
        ImageView foodImage = view.findViewById(R.id.food_image_bottom_sheet);
        Glide.with(view)
                .load(productWithRestaurant.getProduct().getProductImageLink())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))
                .into(foodImage);
        TextView foodName = view.findViewById(R.id.food_name_bottom_sheet);
        TextView foodDesc = view.findViewById(R.id.food_description_bottom_sheet);
        foodName.setText(productWithRestaurant.getProduct().getProductName());
        foodDesc.setText(productWithRestaurant.getProduct().getFoodDesc());
        TextView orderQuantity = view.findViewById(R.id.order_quantity);
        double price = productWithRestaurant.getProduct().getProductPrice();
        calculateTotalPrice(view,1,price);
        increaseOrderItemQuantity(view, orderQuantity,price);
        decreaseOrderItemQuantity(view, orderQuantity,price);
    }

    private void increaseOrderItemQuantity(View view, TextView orderQuantity,double price) {
        ImageButton increaseOrderQuantityBtn = view.findViewById(R.id.increase_order_quantity_btn_id);
        increaseOrderQuantityBtn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(orderQuantity.getText().toString());
            currentQuantity += 1;
            orderQuantity.setText(String.valueOf(currentQuantity));
            calculateTotalPrice(view,currentQuantity,price);
        });
    }

    private void decreaseOrderItemQuantity(View view, TextView orderQuantity,double price) {
        ImageButton decreaseOrderQuantityBtn = view.findViewById(R.id.decrease_order_quantity_btn_id);
        decreaseOrderQuantityBtn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(orderQuantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity -= 1;
                orderQuantity.setText(String.valueOf(currentQuantity));
                calculateTotalPrice(view,currentQuantity,price);
            }
        });
    }
    private void calculateTotalPrice(View view,int quantity,double price) {
        double totalPrice = quantity * price;
        TextView totalPriceTxt = view.findViewById(R.id.order_total_price);
        totalPriceTxt.setText(String.valueOf(totalPrice));
    }
}