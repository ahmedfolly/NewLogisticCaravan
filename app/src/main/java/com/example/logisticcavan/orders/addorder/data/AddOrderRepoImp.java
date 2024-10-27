package com.example.logisticcavan.orders.addorder.data;

import static com.example.logisticcavan.common.utils.Constant.ORDERS;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.addorder.domain.AddOrderRepo;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AddOrderRepoImp implements AddOrderRepo {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final BehaviorSubject<MyResult<String>> subject = BehaviorSubject.create();

    public AddOrderRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }
    @Override
    public Observable<MyResult<String>> addOrder(Order order) {
        subject.onNext(MyResult.loading());
        firestore.collection(ORDERS)
                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .set(getOrderDataToUpload(order))
                .addOnSuccessListener(documentReference -> {
                    subject.onNext(MyResult.success("Uploaded successfully"));
                })
                .addOnFailureListener(e -> {
                    subject.onNext(MyResult.error(e));
                });

        return subject.hide();
    }
    private Map<String, Object> getOrderDataToUpload(Order order) {
        Map<String, Object> orderDataToUpload = new HashMap<>();
//        orderDataToUpload.put("dateCreated", order.getDateCreated());
//        orderDataToUpload.put("from", order.getFrom());
//        orderDataToUpload.put("location", order.getLocation());
//        orderDataToUpload.put("payment", order.getPayment());
//        orderDataToUpload.put("price", order.getPrice());
//        orderDataToUpload.put("productId", order.getOrderId());
//        orderDataToUpload.put("pending", order.getStatus());
//        orderDataToUpload.put("to", order.getTo());
//        orderDataToUpload.put("totalAmount", order.getTotalAmount());
//        orderDataToUpload.put("totalCost", order.getTotalCost());
        orderDataToUpload.put("cartItems", order.getCartItems());
//        orderDataToUpload.put("clientName", order.getClientName());
//        orderDataToUpload.put("clientLocation", order.getClientLocation());
//        orderDataToUpload.put("clientPhone", order.getClientPhone());
//        orderDataToUpload.put("RestaurantName", order.getRestaurantName());
        orderDataToUpload.put("courier", order.getCourier());
        orderDataToUpload.put("deliveryTime", order.getDeliveryTime());
        orderDataToUpload.put("customer", order.getCustomer());
        orderDataToUpload.put("restaurant", order.getRestaurant());
        orderDataToUpload.put("generalDetails", order.getGeneralDetails());
        orderDataToUpload.put("location", order.getLocation());
        return orderDataToUpload;
    }
}
