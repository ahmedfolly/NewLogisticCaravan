package com.example.logisticcavan.rating.addandgetrating.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;

import per.wsj.library.AndRatingBar;

public class RatingsAdapter extends ListAdapter<Rating, RatingsAdapter.RatingVH> {


    public RatingsAdapter() {
        super(new RatingDiffUtil());
    }

    @NonNull
    @Override
    public RatingsAdapter.RatingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item,parent,false);
        return new RatingVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingsAdapter.RatingVH holder, int position) {
        Rating rating = getItem(position);
        String userName = rating.getCustomerName();
        String ratingDate = rating.getDate();
        String reviewText = rating.getReview();
        float ratingValue = rating.getStars();
        holder.userName.setText(userName);
        holder.ratingDate.setText(ratingDate);
        holder.reviewText.setText(reviewText);
        holder.ratingBar.setRating(ratingValue);
    }

    public class RatingVH extends RecyclerView.ViewHolder {

        TextView userName, ratingDate, reviewText;
        AndRatingBar ratingBar;
        public RatingVH(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_rating);
            ratingDate = itemView.findViewById(R.id.rating_date);
            reviewText = itemView.findViewById(R.id.review_text_ratings);
            ratingBar = itemView.findViewById(R.id.rating_bar_ratings);
        }
    }
    static class RatingDiffUtil extends DiffUtil.ItemCallback<Rating>{

        @Override
        public boolean areItemsTheSame(@NonNull Rating oldItem, @NonNull Rating newItem) {
            return oldItem.getOrderId().equals(newItem.getOrderId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rating oldItem, @NonNull Rating newItem) {
            return oldItem == newItem;
        }
    }
}
