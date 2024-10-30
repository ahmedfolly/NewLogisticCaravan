package com.example.logisticcavan.rating;

import com.example.logisticcavan.rating.addandgetrating.data.GetRestaurantRatingsRepoImp;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.GetRestaurantRatingRepo;
import com.example.logisticcavan.rating.addandgetrating.domain.usecase.GetRestaurantRatingsUseCase;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class GetRatingsDI {
    @Provides
    public GetRestaurantRatingsUseCase provideGetRatingsUseCase(GetRestaurantRatingRepo getRatingsRepo) {
        return new GetRestaurantRatingsUseCase(getRatingsRepo);
    }
    @Provides
    public GetRestaurantRatingRepo provideGetRatingsRepo(FirebaseFirestore firebaseFirestore) {
        return new GetRestaurantRatingsRepoImp(firebaseFirestore);
    }

}
