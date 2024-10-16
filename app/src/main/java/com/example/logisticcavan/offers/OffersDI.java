package com.example.logisticcavan.offers;

import com.example.logisticcavan.offers.data.GetOffersRepoImp;
import com.example.logisticcavan.offers.domain.repos.GetOffersRepo;
import com.example.logisticcavan.offers.domain.usecases.GetOffersUseCase;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class OffersDI {
    @Provides
    GetOffersUseCase provideGetOffersUseCase(GetOffersRepo repo){
        return new GetOffersUseCase(repo);
    }
    @Provides
    GetOffersRepo provideGetOffersRepo(FirebaseFirestore firestore){
        return new GetOffersRepoImp(firestore);
    }
}
