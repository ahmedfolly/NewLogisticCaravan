package com.example.logisticcavan.orders.updateStatusOrder.presentaion;

import static com.example.logisticcavan.common.utils.Constant.DELIVERED;
import static com.example.logisticcavan.common.utils.Constant.PENDING;
import static com.example.logisticcavan.common.utils.Constant.SHIPPED;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.databinding.FragmentUpdateOrderStatusBinding;
import com.example.logisticcavan.orders.getOrders.courier.presentaion.ItemListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class UpdateOrderStatusFragment extends BottomSheetDialogFragment {

    String orderStatus;
    ItemListener itemListener;
    private FragmentUpdateOrderStatusBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUpdateOrderStatusBinding.inflate(inflater, container, false);
        setUpRadioButtons(orderStatus);
        binding.updateStatus.setOnClickListener(view -> {
            itemListener.onItemClick(orderStatus);
            dismiss();
        });

        return binding.getRoot();
    }


    private void setUpRadioButtons(String orderStatus) {
        Log.d("TAG", "onCreateView: " + orderStatus);
        switch (orderStatus) {

            case PENDING:
                binding.pending.setChecked(true);
                break;

            case SHIPPED:
                binding.shipped.setChecked(true);
                break;

            case DELIVERED:
                binding.arrived.setChecked(true);
                break;
        }
     setListenerRadioButtons();
    }

    private void setListenerRadioButtons() {
        Log.e("TAG", "setListenerRadioButtons:00 " );

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Log.e("TAG", "setOnCheckedChangeListener: " );

           if(checkedId == R.id.arrived){
               orderStatus = DELIVERED;

           }else if(checkedId == R.id.shipped){
               orderStatus = SHIPPED;

           }
           else if(checkedId == R.id.pending){
               orderStatus = PENDING;
           }else{
               orderStatus = "00000000";
           }
        });
       Log.e("TAG", "setListenerRadioButtons: "+orderStatus );
    }

    public void init(String orderStatus, ItemListener itemListener) {

        this.orderStatus = orderStatus;
        this.itemListener = itemListener;

    }
}