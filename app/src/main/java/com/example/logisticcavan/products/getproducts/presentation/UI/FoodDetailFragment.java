package com.example.logisticcavan.products.getproducts.presentation.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.orders.addorder.presentation.ui.AddOrderBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class FoodDetailFragment extends Fragment {

    FoodDetailFragmentArgs args;

    public FoodDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Log.d("TAG", "onViewCreated: "+args.getProductWithRestaurant().getProduct().getFoodDesc());
        foodDesc.setText(args.getProductWithRestaurant().getProduct().getFoodDesc());
        MaterialButton visitRestaurantBtn = view.findViewById(R.id.visit_restaurant_btn_id);
        visitRestaurantBtn.setText("Visit " + args.getProductWithRestaurant().getRestaurant().getRestaurantName());
        MaterialButton orderFoodBtn = view.findViewById(R.id.order_food_btn_id);
        orderFoodBtn.setOnClickListener(v -> {
            AddOrderBottomSheet bottomSheetDialogFragment = new AddOrderBottomSheet();
            bottomSheetDialogFragment.setArguments(sendArgs());
            bottomSheetDialogFragment.show(getParentFragmentManager(), bottomSheetDialogFragment.getTag());
            bottomSheetDialogFragment.setCancelable(true);
        });

    }
    private Bundle sendArgs() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("productWithRestaurant", args.getProductWithRestaurant());
        return bundle;
    }
}