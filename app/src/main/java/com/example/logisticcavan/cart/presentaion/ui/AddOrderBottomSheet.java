package com.example.logisticcavan.cart.presentaion.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.logisticcavan.R;
import com.example.logisticcavan.cart.presentaion.DetectQuantityUtil;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.ProductWithRestaurant;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddOrderBottomSheet extends BottomSheetDialogFragment {
    private final AddToCartCallback addToCartCallback;
    private final AddToSharedCartCallback addToSharedCartCallback;

    public AddOrderBottomSheet(AddToCartCallback addToCartCallback,AddToSharedCartCallback addToSharedCartCallback) {
        this.addToCartCallback = addToCartCallback;
        this.addToSharedCartCallback = addToSharedCartCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_food_bottomsheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
//        Restaurant restaurant = args.getParcelable("restaurant");
        Product product = args.getParcelable("product");

        ImageView foodImage = view.findViewById(R.id.food_image_bottom_sheet);
        assert product != null;
        Glide.with(view)
                .load(product.getProductImageLink())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))
                .into(foodImage);
        TextView foodName = view.findViewById(R.id.food_name_bottom_sheet);
        foodName.setText(product.getProductName());
        TextView foodDesc = view.findViewById(R.id.food_description_bottom_sheet);
        foodDesc.setText(product.getFoodDesc());
        TextView orderQuantity = view.findViewById(R.id.order_quantity);
        double price = product.getProductPrice();
        TextView totalPriceTxt = view.findViewById(R.id.order_total_price);
        DetectQuantityUtil.calculateTotalPrice(totalPriceTxt, 1, price);
        ImageButton decreaseOrderQuantityBtn = view.findViewById(R.id.decrease_order_quantity_btn_id);
        ImageButton increaseOrderQuantityBtn = view.findViewById(R.id.increase_order_quantity_btn_id);
        DetectQuantityUtil.increaseOrderItemQuantity("order", null, increaseOrderQuantityBtn, totalPriceTxt, orderQuantity, price);
        DetectQuantityUtil.decreaseOrderItemQuantity("order", null, decreaseOrderQuantityBtn, totalPriceTxt, orderQuantity, price);
        addToCart(view, product, orderQuantity, totalPriceTxt);

        addProductToSharedCart(product,product.getProductID(), orderQuantity);
    }

    private void addToCart(View view, Product product, TextView quantityText, TextView totalPriceTxt) {
        CardView addToCartBtn = view.findViewById(R.id.add_product_to_cart);
        addToCartBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());
            double price = Double.parseDouble(totalPriceTxt.getText().toString());
            addToCartCallback.addToCart(product, quantity, price);
            dismiss();
        });
    }
    private void addProductToSharedCart( Product product,String productId, TextView quantityText) {
        ImageView addToSharedCartBtn = requireView().findViewById(R.id.add_to_group_order);
        addToSharedCartBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());

            addToSharedCartCallback.addToSharedCart(product,productId, quantity);
            dismiss();
        });
    }
    public interface AddToCartCallback {
        void addToCart(Product product, int quantity, double price);
    }
   public interface AddToSharedCartCallback{
        void addToSharedCart(Product product,String productId, int quantity);
   }
}
