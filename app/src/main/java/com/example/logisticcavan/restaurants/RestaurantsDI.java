package com.example.logisticcavan.restaurants;

import com.example.logisticcavan.restaurants.data.GetRestaurantRepoImp;
import com.example.logisticcavan.restaurants.domain.GetRestaurantDataRepo;
import com.example.logisticcavan.restaurants.domain.GetRestaurantUseCase;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RestaurantsDI {

    @Provides
    GetRestaurantUseCase provideGetRestaurantUseCase(GetRestaurantDataRepo repo){
        return new GetRestaurantUseCase(repo);
    }
    @Provides
    GetRestaurantDataRepo provideGetRestaurantRepo(FirebaseFirestore firestore){
        return new GetRestaurantRepoImp(firestore);
    }
}
