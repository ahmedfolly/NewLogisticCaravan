package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpiredProductsAdapter extends RecyclerView.Adapter<ExpiredProductsAdapter.ProductsVH> {
    List<Product> products;
    Context context;
private  ProductListener productListener;

    public ExpiredProductsAdapter(List<Product> products, ProductListener productListener , Context context) {
        this.products = products;
        this.productListener = productListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpiredProductsAdapter.ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View expiredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expired_product, parent, false);
        return new ProductsVH(expiredView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpiredProductsAdapter.ProductsVH holder, int position) {
        Product product = products.get(position);
        Glide.with(holder.itemView)
                .load(product.getProductImageLink())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(18)))
                .into(holder.productImage);
        Log.e("TAG", "getProductImageLink: " + product.getProductImageLink());
        holder.expirationDate.setText(stringDate(product.getExpirationData()));
        updateStatus(holder, product);
        holder.itemView.setOnClickListener(view -> {
            productListener.onProductClick(product);
        });
    }

    private void updateStatus(@NonNull ProductsVH holder, Product product) {
        if (product.getRemovalDate() == 0L){
            holder.status.setText(context.getString(R.string.status_product_is_still_on_shelf));
        }else {
            holder.removalDate.setText(stringDate(product.getRemovalDate()));
            holder.itemView.setEnabled(false);
            holder.status.setText(context.getString(R.string.product_has_been_removed));
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsVH extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView expirationDate;
        TextView removalDate;
        TextView status;

        public ProductsVH(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image);
            expirationDate = itemView.findViewById(R.id.expirationDate);
            removalDate = itemView.findViewById(R.id.removalDate);
            status = itemView.findViewById(R.id.status);
        }
    }

    private String stringDate(Long timestamp){
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       return dateTime.format(formatter);
    }


    public interface ProductListener {
        void onProductClick(Product product);
    }

}
