package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpiredProductsAdapter extends RecyclerView.Adapter<ExpiredProductsAdapter.ProductsVH> {
    List<Product> products;
private  ProductListener productListener;

    public ExpiredProductsAdapter(List<Product> products, ProductListener productListener) {
        this.products = products;
        this.productListener = productListener;
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
        holder.productID.setText(product.getProductID());
        holder.expirationDate.setText(stringDate(product.getExpirationData()));


        updateStatus(holder, product);
        holder.removalDate.setText(stringDate(product.getRemovalDate()));

        holder.itemView.setOnClickListener(view -> {
            productListener.onProductClick(product);
        });
    }

    private static void updateStatus(@NonNull ProductsVH holder, Product product) {
        if (product.getRemovalDate().equals(0)){
            holder.status.setText("Status: Product is still on shelf");

        }else {
            holder.itemView.setEnabled(false);
            holder.status.setText("Status: Product has been removed");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsVH extends RecyclerView.ViewHolder {
        TextView productID;
        TextView expirationDate;
        TextView removalDate;
        TextView status;

        public ProductsVH(@NonNull View itemView) {
            super(itemView);
            productID = itemView.findViewById(R.id.productID);
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
