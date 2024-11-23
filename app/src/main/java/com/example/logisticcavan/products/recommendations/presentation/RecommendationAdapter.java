package com.example.logisticcavan.products.recommendations.presentation;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.Objects;

public class RecommendationAdapter extends ListAdapter<Product, RecommendationAdapter.RecommendationVH> {
    private RecommendedProductListener recommendedProductListener;
    public RecommendationAdapter(RecommendedProductListener recommendedProductListener) {
        super(new RecommendedProductDiffUtil());
        this.recommendedProductListener = recommendedProductListener;
    }

    @NonNull
    @Override
    public RecommendationAdapter.RecommendationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item_layout, parent, false);
        return new RecommendationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.RecommendationVH holder, int position) {
        Product product = getItem(position);
        holder.productName.setText(product.getProductName());
        Glide.with(holder.itemView.getContext()).load(product.getProductImageLink()).into(holder.productImage);
        holder.itemView.setOnClickListener(v->{
            recommendedProductListener.onProductClicked(product);
        });
    }

    public static class RecommendationVH extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;

        public RecommendationVH(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.recommended_food_image_id);
            productName = itemView.findViewById(R.id.recommended_food_name_id);
        }
    }

    public static class RecommendedProductDiffUtil extends DiffUtil.ItemCallback<Product> {

        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return Objects.equals(oldItem.getProductID(), newItem.getProductID());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }
    }
    public interface RecommendedProductListener {
        void onProductClicked(Product product);
    }
}
