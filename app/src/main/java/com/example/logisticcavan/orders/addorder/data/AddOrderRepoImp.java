package com.example.logisticcavan.orders.addorder.data;

import static com.example.logisticcavan.common.utils.Constant.ORDERS;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.addorder.domain.AddOrderRepo;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private final BehaviorSubject<Boolean> setOrderIdToCurrUserSubject = BehaviorSubject.create();

    public AddOrderRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }
    @Override
    public Observable<MyResult<String>> addOrder(Order order) {
        subject.onNext(MyResult.loading());
        firestore.collection(ORDERS)
                .add(getOrderDataToUpload(order))
                .addOnSuccessListener(documentReference -> {
                    String orderId = documentReference.getId();
//                    uploadOrderId(orderId);
//                    Log.d("TAG", "addOrder: "+orderId);
//                    addOrderToCurrentUser(orderId);
//                    subject.onNext(MyResult.success("Uploaded successfully"));
                    uploadOrderId(orderId).addOnSuccessListener(aVoid -> {
                        Log.d("TAG", "addOrder: " + orderId);
                        addOrderToCurrentUser(orderId);
                        subject.onNext(MyResult.success("Uploaded successfully"));
                    }).addOnFailureListener(e -> {
                        subject.onNext(MyResult.error(e));
                    });
                })
                .addOnFailureListener(e -> {
                    subject.onNext(MyResult.error(e));
                });

        return subject.hide();
    }

    private Task<Void> uploadOrderId(String id) {
        DocumentReference orderRef = firestore.collection(ORDERS).document(id);
        return orderRef.get().continueWithTask(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    Map<String, Object> gDetailsMap = (Map<String, Object>) documentSnapshot.get("generalDetails");
                    if (gDetailsMap != null) {
                        gDetailsMap.put("orderId", id);
                        // Now update the document with the modified map
                        return orderRef.update("generalDetails", gDetailsMap);
                    }
                }
            }
            return Tasks.forException(new Exception("Failed to update order ID"));
        });
    }    public void addOrderToCurrentUser(String orderId) {
        Map<String,String> orderIdMap = new HashMap<>();
        orderIdMap.put("orderId",orderId);
        firestore.collection("users")
                .document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()))
                .collection("Orders")
                .add(orderIdMap)
                .addOnSuccessListener(v->{
                    Log.d("TAG", "addOrderToCurrentUser: order id added to curr user");
                })
                .addOnFailureListener(e->{
                    Log.d("TAG", "addOrderToCurrentUser: "+e.getMessage());
                });
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
