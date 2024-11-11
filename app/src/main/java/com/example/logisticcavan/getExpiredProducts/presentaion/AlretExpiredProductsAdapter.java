package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.offers.domain.Offer;

import java.util.List;

public class AlretExpiredProductsAdapter extends RecyclerView.Adapter<AlretExpiredProductsAdapter.OffersVH> {
    List<Offer> offers;

    public AlretExpiredProductsAdapter(List<Offer> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public AlretExpiredProductsAdapter.OffersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View offerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new OffersVH(offerView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlretExpiredProductsAdapter.OffersVH holder, int position) {
        Offer offer = offers.get(position);
        Glide.with(holder.itemView)
                .load(offer.getOfferImageLink())
                .override(1000,1000)
                .into(holder.offerImage);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class OffersVH extends RecyclerView.ViewHolder {
        ImageView offerImage;

        public OffersVH(@NonNull View itemView) {
            super(itemView);
            offerImage = itemView.findViewById(R.id.offer_image_id);
        }
    }
}
