package com.example.logisticcavan.offers.data;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.logisticcavan.common.MyResult;
import com.example.logisticcavan.offers.domain.GetOffersRepo;
import com.example.logisticcavan.offers.domain.Offer;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetOffersRepoImp implements GetOffersRepo {
    private final FirebaseFirestore firestore;

    public GetOffersRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Observable<MyResult<List<Offer>>> getAllOffers() {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Offers").addSnapshotListener((value, error) -> {
                if (error != null) {
                    emitter.onNext(MyResult.error(error));
                }else{
                    assert value != null;
                    List<Offer> offersList = value.toObjects(Offer.class);
                    Log.d("Offer", "getAllOffers: "+offersList.get(0));
                    emitter.onNext(MyResult.success(offersList));
                }
            });
        });
    }
}
