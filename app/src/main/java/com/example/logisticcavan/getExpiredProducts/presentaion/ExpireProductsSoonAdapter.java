package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ExpireProductsSoonAdapter extends RecyclerView.Adapter<ExpireProductsSoonAdapter.ProductsVH> {
    List<Product> products;
    Context context;
    public ExpireProductsSoonAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ExpireProductsSoonAdapter.ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View expiredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_alert_expiration, parent, false);
        return new ProductsVH(expiredView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpireProductsSoonAdapter.ProductsVH holder, int position) {
        Product product = products.get(position);
        holder.idProduct.setText("Product with ID:#"+product.getProductID()+" will expire soon");
        holder.date.setText("Expiry date: "+stringDate(product.getExpirationData()));

    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsVH extends RecyclerView.ViewHolder {
        TextView idProduct;
        TextView date;

        public ProductsVH(@NonNull View itemView) {
            super(itemView);
            idProduct = itemView.findViewById(R.id.idProduct);
            date = itemView.findViewById(R.id.date);
        }
    }

    private String stringDate(Long timestamp){
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       return dateTime.format(formatter);
    }



}
