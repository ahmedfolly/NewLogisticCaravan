package com.example.logisticcavan.orders.addorder.presentation.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.logisticcavan.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class ConfirmOrderBottomFragment extends BottomSheetDialogFragment {
    public ConfirmOrderCallback confirmOrderCallback;

    public ConfirmOrderBottomFragment(ConfirmOrderCallback confirmOrderCallback) {
        this.confirmOrderCallback = confirmOrderCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.confirm_order_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText pickLocation = view.findViewById(R.id.pick_location);
        TextInputEditText pickPhone = view.findViewById(R.id.pick_phone);
        TextView warningPaymentMessage = view.findViewById(R.id.warninig_payment_message);
        RadioGroup paymentMethod = view.findViewById(R.id.payment_group);

        int selectedPaymentMethodId = paymentMethod.getCheckedRadioButtonId();
        MaterialRadioButton cashRadioBtn = view.findViewById(R.id.cash_radio_btn);
        MaterialButton confirmCheckoutBtn = view.findViewById(R.id.confirm_order_btn);
        checkPaymentMethod(selectedPaymentMethodId, warningPaymentMessage, paymentMethod, cashRadioBtn);
        confirmCheckoutBtn.setOnClickListener(v -> {
            String userLocation = Objects.requireNonNull(pickLocation.getText()).toString();
            String phone = Objects.requireNonNull(pickPhone.getText()).toString();
            String paymentSelectedMethod = getPaymentMethod(paymentMethod, cashRadioBtn);
            if (!TextUtils.isEmpty(userLocation) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(paymentSelectedMethod)) {
                //here add add callbakc with data picked
                confirmOrderCallback.onConfirmOrder(userLocation, phone, paymentSelectedMethod);
                dismiss();
            } else {
                if (TextUtils.isEmpty(userLocation)) {
                    pickLocation.setError("Location is required");
                }
                if (TextUtils.isEmpty(phone)) {
                    pickPhone.setError("Phone is required");
                }
                if (TextUtils.isEmpty(paymentSelectedMethod)) {
                    warningPaymentMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    String getPaymentMethod(RadioGroup paymentMethod, MaterialRadioButton radioButton) {
        int selectedRadioButtonId = paymentMethod.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            return "";
        }
        return radioButton.getText().toString();
    }
    void checkPaymentMethod(int selectedPaymentMethodId, TextView warningPaymentMessage, RadioGroup paymentMethod,MaterialRadioButton cashRadioBtn){
        if (selectedPaymentMethodId != -1) {
            cashRadioBtn = paymentMethod.findViewById(selectedPaymentMethodId);
        }
        cashRadioBtn.setOnCheckedChangeListener((buttonView, isChecked) ->
                warningPaymentMessage.setVisibility(View.GONE));
    }

    public interface ConfirmOrderCallback {
        void onConfirmOrder(String userLocation, String phone, String paymentMethod);
    }
}
