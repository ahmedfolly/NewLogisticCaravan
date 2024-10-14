package com.example.logisticcavan.products.categories;


import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.logisticcavan.R;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesVH> {
    List<Category> categories;

    private int selectedPosition = 0;

    private final OnItemSelected listener;

    public CategoriesAdapter(List<Category> categories, OnItemSelected listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.CategoriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoriesVH(categoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoriesVH holder, int position) {
        Category category = categories.get(position);
        holder.bind(category.getCategoryName(), position == selectedPosition);
        holder.itemView.setOnClickListener(view -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            if (listener != null){
                listener.scrollToPosition(selectedPosition);
                listener.getCategoryName(category.getCategoryName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoriesVH extends RecyclerView.ViewHolder {
        TextView categoryNameText;
        View view;

        public CategoriesVH(@NonNull View itemView) {
            super(itemView);
            categoryNameText = itemView.findViewById(R.id.category_id);
            view = itemView.findViewById(R.id.category_container);
        }

        void bind(String categoryName, boolean isSelected) {
            categoryNameText.setText(categoryName);
            TransitionDrawable transitionDrawable = (TransitionDrawable) ResourcesCompat.getDrawable(
                    itemView.getContext().getResources(),
                    R.drawable.category_transition,
                    itemView.getContext().getTheme()
            );
            assert transitionDrawable != null;
            if (isSelected) {
                transitionDrawable.startTransition(500);
            } else {
                transitionDrawable.reverseTransition(500);
            }
            itemView.setBackgroundResource(isSelected ? R.drawable.category_selected_background : R.drawable.category_unselected_background);
        }
    }

    public interface OnItemSelected {
        void scrollToPosition(int position);
        void getCategoryName(String categoryName);
    }
}
