package com.example.logisticcavan.sharedcart;

import com.example.logisticcavan.sharedcart.data.AddToSharedCartRepoImp;
import com.example.logisticcavan.sharedcart.data.GetSharedProductsRepoImp;
import com.example.logisticcavan.sharedcart.domain.repo.AddSharedCartItemRepo;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedProductsRepo;
import com.example.logisticcavan.sharedcart.domain.usecases.AddToSharedCartUseCase;
import com.example.logisticcavan.sharedcart.domain.usecases.GetSharedProductsUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class SharedCartDI {

    @Provides
    public GetSharedProductsUseCase provideGetSharedProductsUseCase(GetSharedProductsRepo sharedCartRepository) {
        return new GetSharedProductsUseCase(sharedCartRepository);
    }

    @Provides
    public GetSharedProductsRepo provideGetSharedProductsRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        return new GetSharedProductsRepoImp(firebaseFirestore, firebaseAuth);
    }

    @Provides
    public AddToSharedCartUseCase provideAddToSharedCartUseCase(AddSharedCartItemRepo sharedCartRepository) {
        return new AddToSharedCartUseCase(sharedCartRepository);
    }

    @Provides
    public AddSharedCartItemRepo provideSharedCartRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth mAuth) {
        return new AddToSharedCartRepoImp(firebaseFirestore, mAuth);
    }

}
