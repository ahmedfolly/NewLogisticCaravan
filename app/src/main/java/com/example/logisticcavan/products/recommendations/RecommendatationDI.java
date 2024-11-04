package com.example.logisticcavan.products.recommendations;

import com.example.logisticcavan.products.recommendations.data.GetRecommendationsRepoImp;
import com.example.logisticcavan.products.recommendations.domain.GetRecommendationsRepo;
import com.example.logisticcavan.products.recommendations.domain.GetRecommendationsUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class RecommendatationDI {
    @Provides
    public GetRecommendationsUseCase provideGetRecommendationsUseCase(GetRecommendationsRepo getRecommendationsRepo) {
        return new GetRecommendationsUseCase(getRecommendationsRepo);
    }

    @Provides
    public GetRecommendationsRepo provideGetRecommendationsRepo(FirebaseFirestore firebaseFirestore, FirebaseAuth auth) {
        return new GetRecommendationsRepoImp(firebaseFirestore, auth);
    }
}
