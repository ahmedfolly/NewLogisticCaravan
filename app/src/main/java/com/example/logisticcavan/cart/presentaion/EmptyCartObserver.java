package com.example.logisticcavan.cart.presentaion;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class EmptyCartObserver extends RecyclerView.AdapterDataObserver {
    private final View emptyView;
    private final RecyclerView recyclerView;
    private final MaterialButton checkoutBtn;

    public EmptyCartObserver(View emptyView, RecyclerView recyclerView, MaterialButton checkoutBtn) {
        this.emptyView = emptyView;
        this.recyclerView = recyclerView;
        this.checkoutBtn = checkoutBtn;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            boolean emptyViewVisible = itemCount == 0;

            if (emptyViewVisible) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                checkoutBtn.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                checkoutBtn.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onChanged() {
        super.onChanged();
        Log.d("TAG", "onChanged: ");
        checkIfEmpty();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        checkIfEmpty();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        checkIfEmpty();
    }
}
